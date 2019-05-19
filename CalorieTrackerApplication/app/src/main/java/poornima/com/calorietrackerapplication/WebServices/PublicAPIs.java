package poornima.com.calorietrackerapplication.WebServices;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PublicAPIs {

    private static final String API_KEY = "AIzaSyBXX1Bu2AF-FoUvdZ62qePgp1mwdtTP_Go";
    private static final String SEARCH_ID_cx = "003250631447165667167:trbwexffky8";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
                String snippetLastChar = snippet.substring(snippet.length() - 3);
                Log.d("SNIPPET", snippetLastChar);
                if(snippet.endsWith("...")){
                    Log.d("SNIPPET::::::::", snippet);
                    int p=snippet.lastIndexOf(".");
                    String e=snippet.substring(p+1);
                    Log.d("SNIPPET----e", e);

                }

            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    //getting imgURL

    public static String getImgURL(String result){
        String imgSrc = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {

                JSONObject jObj = new JSONObject(jsonArray.getJSONObject(0).getString("pagemap"));

                JSONArray jsonArray1 = jObj.getJSONArray("cse_image");
                imgSrc = jsonArray1.getJSONObject(0).getString("src");

                Log.d("SNIP", imgSrc);


            }
        }catch (Exception e){
            e.printStackTrace();
            imgSrc = "NO INFO FOUND";
        }
        return imgSrc;
    }


    //API TO GET THE FOOD DETAILS
    public static String getFoodAPI(String keyword) throws JSONException {

        String FOODAPIKEY = "n4dsUc8HXnrruOVQr7nynEDr5rxYbFRVaynT2WOY";

        String FOOD_URL = " https://api.nal.usda.gov/ndb/search/?format=json&q="+keyword+"&sort=n&max=1&offset=0&api_key="+FOODAPIKEY;

        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        try {
            url = new URL(FOOD_URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }

        //getting the food number

        String foodNumber = "";
        JSONObject jsonObject = new JSONObject(textResult);

        JSONObject getListOBJ = new JSONObject(String.valueOf(jsonObject.getJSONObject("list")));
        JSONArray jsonArray = getListOBJ.getJSONArray("item");
        if(jsonArray != null && jsonArray.length() > 0) {

            foodNumber =jsonArray.getJSONObject(0).getString("ndbno");
        }


        //need to get the fat and carbs content

        String fat = "", carbs = "", servingUnit = "";

        String FOOD_DETAILS_URL = "https://api.nal.usda.gov/ndb/V2/reports?ndbno="+foodNumber+"&type=b&format=json&api_key="+FOODAPIKEY;

        URL url1 = null;
        HttpURLConnection connection1 = null;
        String foodDetailsTxt = "";

        try {
            url1 = new URL(FOOD_DETAILS_URL);
            connection1 = (HttpURLConnection)url1.openConnection();
            connection1.setReadTimeout(10000);
            connection1.setConnectTimeout(15000);
            connection1.setRequestMethod("GET");
            connection1.setRequestProperty("Content-Type", "application/json");
            connection1.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection1.getInputStream());
            while (scanner.hasNextLine()) {
                foodDetailsTxt += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection1.disconnect();
        }

      //  Log.d("FOOOOD_JSON","::"+ foodDetailsTxt);

        //filtering to get the carb and fat

        JSONObject foodObj1 = new JSONObject(foodDetailsTxt);

      //  JSONObject foodObj2 = new JSONObject(String.valueOf(foodObj1.getJSONObject("foods")));
        JSONArray foodArray1 = foodObj1.getJSONArray("foods");

        if(foodArray1 != null && foodArray1.length() > 0) {
            JSONObject foodObj3  =foodArray1.getJSONObject(0);
            JSONObject foodObj4 = new JSONObject(String.valueOf(foodObj3.getJSONObject("food")));
            JSONArray nutrientArray = foodObj4.getJSONArray("nutrients");
            int lenght = nutrientArray.length();

            for(int i=0; i< lenght; i++){
                int flag = 0;
                JSONObject nutrient = nutrientArray.getJSONObject(i);

                String nutrient_id = nutrient.getString("nutrient_id");
                //id for fat
                if(nutrient_id.equals("204")){

                    JSONArray foodARRAY = nutrient.getJSONArray("measures");
                    JSONObject nutriObj = foodARRAY.getJSONObject(0);

                    servingUnit = nutriObj.getString("label");
                    fat = nutriObj.getString("value");
                   // Log.d("FOOOD-fat", fat);

                }
                //for carbs
                if(nutrient_id.equals("208")){

                    JSONArray foodARRAY = nutrient.getJSONArray("measures");
                    JSONObject nutriObj = foodARRAY.getJSONObject(0);

                    carbs = nutriObj.getString("value");
                   // Log.d("FOOOD--carbs", carbs);

                }

            }
        }
        String finalResult = "";
        finalResult = "{ \"servingUnit\": \""+servingUnit+"\", \"fat\": \""+ fat + "\", \"calories\": \""+carbs + "\"}";

        return finalResult;
    }

    //GEO CODDING API

    public static String getGeoCode(String location) {
      //  location = location.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        try {
            url = new URL("https://www.mapquestapi.com/geocoding/v1/address?key=yjzAjHhPddv6iRKgLaZfR5SbzAzpHihP&inFormat=kvp&outFormat=json&location="+location+"thumbMaps=false");
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    //get PARKS

    public static String getParks(String location) {
        //  location = location.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        try {
            url = new URL("https://www.mapquestapi.com/search/v2/radius?origin=+"+location +"&radius=5&maxMatches=10&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799951&outFormat=json&key=yjzAjHhPddv6iRKgLaZfR5SbzAzpHihP");
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

}
