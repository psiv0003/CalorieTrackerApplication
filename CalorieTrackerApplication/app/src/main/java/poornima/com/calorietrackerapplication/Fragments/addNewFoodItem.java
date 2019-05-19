package poornima.com.calorietrackerapplication.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import poornima.com.calorietrackerapplication.Models.Food;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.PublicAPIs;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

public class addNewFoodItem extends Fragment {
    View view;
    TextView foodNameTxt, foodCategoryTxt, foocDesc, foodFat, foodCarbs;
    String foodNameStr, foodCategoryStr;
    Button diet;
    ImageView foodImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.new_food_item, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        foodCategoryStr = preferences.getString("new_food_category", "");
        foodNameStr =  preferences.getString("new_food_txt", "");

        foodNameTxt = (TextView) view.findViewById(R.id.foodNameTxt);
        foodCategoryTxt = (TextView) view.findViewById(R.id.foodCategory);
        foocDesc = (TextView) view.findViewById(R.id.fooddescTxt);
        foodFat = (TextView) view.findViewById(R.id.fatTxt);
        foodCarbs = (TextView) view.findViewById(R.id.calTxt);

        diet = (Button) view.findViewById(R.id.button4);
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyDietScreen del = new DailyDietScreen();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,
                        del).commit();
            }
        });
        foodNameTxt.setText(foodNameStr);
        foodCategoryTxt.setText(foodCategoryStr);

        SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
        searchAsyncTask.execute(foodNameStr);

        foodImg = (ImageView) view.findViewById(R.id.imageView3);


        return view;
    }

    //gettind desc and img
    private class SearchAsyncTask extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Loading", "Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return PublicAPIs.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            //TextView tv= (TextView) findViewById(R.id.tvResult);
            //tv.setText(GoogleSearchAPI.getSnippet(result));

            String desc = PublicAPIs.getSnippet(result);
            foocDesc.setText(desc);


            String Imgurl = PublicAPIs.getImgURL(result);
            foodDetails foodDetails = new foodDetails();
            foodDetails.execute(foodNameStr);
            if(Imgurl.equals("NO INFO FOUND")){
            }
            else{

                new DisplayImgFromURL(foodImg).execute(Imgurl);
            }

            progressDialog.dismiss();

            Log.d("GOOFLE", result);
        }
    }

    //disp the image
    private class DisplayImgFromURL extends AsyncTask<String, Void, Bitmap> {

        ImageView foodImageFromURL;

        public DisplayImgFromURL(ImageView foodImageFromURL)
        {
            this.foodImageFromURL = foodImageFromURL;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitMapImg = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitMapImg = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitMapImg;
        }


        protected void onPostExecute(Bitmap result)
        {
            foodImageFromURL.setImageBitmap(result);
        }
    }

    //get the food details
    private class foodDetails extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Loading", "Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String foodDetails = null;
            try {
                foodDetails = PublicAPIs.getFoodAPI(params[0]);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return foodDetails;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                dispResults(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            progressDialog.dismiss();

        }
    }

//setting the text in the screen

    public void dispResults(String results) throws JSONException {

        JSONObject jsonObject = new JSONObject(results);
        String fat = jsonObject.getString("fat");
        String servingUnit = jsonObject.getString("servingUnit");
        String cal = jsonObject.getString("calories");

        foodCarbs.setText(cal);
        foodFat.setText(fat);
        addNewFoodToDB addNewFoodToDB = new addNewFoodToDB();
        addNewFoodToDB.execute(cal,foodCategoryStr,fat,foodNameStr,"1",servingUnit);




    }

    //adding new food to DB

    private class addNewFoodToDB extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Loading", "Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String count = RestWS.getFoodId();
            String fooodID =  String.valueOf(Integer.parseInt(count) + 2);

            Food food = new Food(strings[0], strings[1], strings[2], fooodID, strings[3],strings[4],strings[5]);
            RestWS.createFood(food);

            return "Added";
        }

        @Override
        protected void onPostExecute(String result) {


            progressDialog.dismiss();
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            alertDialogBuilder.setTitle("Food Added Succesfully!")
                    .setMessage(" Food item was added successfully to the database. You can use this item the next time you want to add anything!")
                    .setCancelable(false)
                    .setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
            ;
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }



}
