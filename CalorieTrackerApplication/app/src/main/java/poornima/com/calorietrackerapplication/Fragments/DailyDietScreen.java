package poornima.com.calorietrackerapplication.Fragments;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import poornima.com.calorietrackerapplication.Activities.MainActivity;
import poornima.com.calorietrackerapplication.Activities.signUpActivity;
import poornima.com.calorietrackerapplication.Models.Consumption;
import poornima.com.calorietrackerapplication.Models.Food;
import poornima.com.calorietrackerapplication.Models.User;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

import static android.content.Context.MODE_PRIVATE;


public class DailyDietScreen extends Fragment {
    View view;
    Spinner categorySpinner, foodItemSpinner;
    Button addBtn, newFoodItemBtn;
    TextView qtyTxt;
    EditText qtyEditTxt;
    ArrayList<String> foodCategories = new ArrayList<>();
    ArrayList<String> foodItems = new ArrayList<>();
    String userId, foodIdStr, qtyAmt, date;
    SharedPreferences preferences;
    String foodCategory, foodCalorieAmt, foodIdNo, foodName, foodFat, foodServingAmt, foodServingUnit;

    Typeface font;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.daily_diet_fragment, container, false);
        getActivity().setTitle("Daily Diet");


        foodResults foodResults = new foodResults();
        foodResults.execute();


        preferences = this.getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);
        userId =  preferences.getString("user_id", "");



        categorySpinner = (Spinner) view.findViewById(R.id.foodCategorySpinner);
        foodItemSpinner = (Spinner) view.findViewById(R.id.foodItemSpinner);
        qtyTxt = (TextView) view.findViewById(R.id.servingType);
        qtyEditTxt = (EditText) view.findViewById(R.id.editText);

        addBtn = (Button) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyAmt = qtyEditTxt.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.format(new Date());
                date = date + "T00:00:00+00:00";
                addNewItem addNewItem = new addNewItem();
                addNewItem.execute( date, qtyAmt);
              //  addNewItem(;
            }
        });

        newFoodItemBtn = (Button) view.findViewById(R.id.addNewFoodBtn);
        newFoodItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterNewFoodTxt();
            }
        });


        return view;
    }

    private class foodResults extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", "Loading...");
           progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
           results =  RestWS.getFoodDetails();

            return "Success";

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            try {
                displayItem(results);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void displayItem(String results) throws JSONException {
        Log.d("Foood", results);

        final JSONArray jsonObject = new JSONArray(results);
        final int len = jsonObject.length();
        for(int i = 0; i<len; i++){
            JSONObject json_obj = jsonObject.getJSONObject(i);
           Log.d("FOOOd ", String.valueOf(i)+ ":::::::"+ json_obj.toString());
            foodCategories.add(json_obj.getString("category"));

        }
        List<String> uniqueFoodCategories = new ArrayList<String>(new HashSet<String>(foodCategories));
        int length = uniqueFoodCategories.size();
        List<String> foodCategoriesList = new ArrayList<String>();


        for (int i = 0; i < length; i++) {

            foodCategoriesList.add(uniqueFoodCategories.get(i));
        }
       // ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(this.getActivity(),
         //       R.array.activitylevel, android.R.layout.simple_spinner_item);
        final ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this.getActivity() ,android.R.layout.simple_spinner_dropdown_item, foodCategoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoriesAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = adapterView.getItemAtPosition(i).toString();
                foodCategory = category;

               getFoodItemList getFoodItemList = new getFoodItemList();
                getFoodItemList.execute(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });



    }

    private class getFoodItemList extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", "Loading...");
            progressDialog.show();

        }


        @Override
        protected String doInBackground(String... strings) {
             results = RestWS.getFood(strings[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            try {
                foodItemDisplay(results);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void foodItemDisplay(String results) throws JSONException {
        Log.d("FooodItem", results);
        final List<String> foodItem = new ArrayList<String>();
        final List<String> foodServingType = new ArrayList<String>();
        final List<String> foodId = new ArrayList<String>();
        final List<String> calorieamount = new ArrayList<String>();
        final List<String> fat = new ArrayList<String>();
        final List<String> servingAmt = new ArrayList<String>();
        final List<String> foodNameList = new ArrayList<String>();






        final JSONArray jsonObject = new JSONArray(results);
        final int len = jsonObject.length();
        for(int i = 0; i<len; i++){
            JSONObject json_obj = jsonObject.getJSONObject(i);
            Log.d("FOOOd ", String.valueOf(i)+ ":::::::"+ json_obj.toString());
            //foodItems.add(json_obj.getString("name"));
            foodItem.add(json_obj.getString("name"));
            foodServingType.add(json_obj.getString("servingunit"));
            foodId.add(json_obj.getString("foodid"));
            calorieamount.add(json_obj.getString("calorieamount"));
            fat.add(json_obj.getString("fat"));
            servingAmt.add(json_obj.getString("servingamt"));
           // foodNameList.add(json_obj.getString(""))

        }




        // ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(this.getActivity(),
        //       R.array.activitylevel, android.R.layout.simple_spinner_item);
        final ArrayAdapter<String> foodItemAdapter = new ArrayAdapter<String>(this.getActivity() ,android.R.layout.simple_spinner_dropdown_item, foodItem);
        foodItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodItemSpinner.setAdapter(foodItemAdapter);

        foodItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                foodIdStr = foodId.get(i);
                foodIdNo = foodId.get(i);
                foodName =foodItem.get(i);
                foodCalorieAmt = calorieamount.get(i);
                foodServingAmt = servingAmt.get(i);
                foodFat = fat.get(i);
                foodServingUnit = foodServingType.get(i);



                qtyTxt.setText(foodServingType.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

    }

    private class addNewItem extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String count = RestWS.getConsumptionId();
            String consumptionid =  String.valueOf(Integer.parseInt(count) + 1);

            User user = new User(preferences.getString("name", ""),preferences.getString("surname", ""),preferences.getString("email", ""),preferences.getString("height", ""),preferences.getString("weight", ""),preferences.getString("address", ""),preferences.getString("postcode", ""),preferences.getString("dob", ""),preferences.getString("stepspermile", ""),preferences.getString("activitylevel", ""),preferences.getString("gender", ""), preferences.getString("user_id", ""));
            Food food = new Food(foodCalorieAmt,foodCategory,foodFat,foodIdNo,foodName,foodServingAmt,foodServingUnit);
            Consumption consumption = new Consumption( user,food, strings[0], strings[1],consumptionid);
            RestWS.createConsumption(consumption);

            return "Added";
        }

        @Override
        protected void onPostExecute(String result) {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            alertDialogBuilder.setTitle("Food Added Succesfully!")
                    .setMessage("Your Food item was added successfully!")
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

    public void enterNewFoodTxt(){
        LayoutInflater li = (LayoutInflater) LayoutInflater.from(this.getActivity());
        View promptsView = li.inflate(R.layout.new_food_item_txt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this.getActivity());
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.setFoodItem);
        final  EditText category = (EditText) promptsView.findViewById(R.id.setFoodCategory);
        alertDialogBuilder
                .setTitle("Enter Food Item")
                .setCancelable(false)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(category.length() == 0 || userInput.length() == 0){
                                    Toast.makeText(getActivity(), "Please enter all the values!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE).edit();
                                    editor.putString("new_food_txt",  userInput.getText().toString());
                                    editor.putString("new_food_category",  category.getText().toString());
                                    editor.commit();
                                    Fragment addNewItem = null;

                                    addNewItem = new addNewFoodItem();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.content_frame,
                                            addNewItem).commit();
                                    //TODO connect these values to some place
                                }


                            }
                        })
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


}
