package poornima.com.calorietrackerapplication.WebServices;
import android.util.Log;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import poornima.com.calorietrackerapplication.Models.Consumption;
import poornima.com.calorietrackerapplication.Models.Food;
import poornima.com.calorietrackerapplication.Models.Report;
import poornima.com.calorietrackerapplication.Models.User;
import poornima.com.calorietrackerapplication.Models.UserCredential;

public class RestWS {

    final static String BASE_URL = "http://192.168.56.1:23536/MobileAssignment1/webresources/";

    public static String userLogin(String username, String passewordhash) {
        final String methodPath = "caltrackerwebservices.usercredential/userlogin/" + username + "/" + passewordhash;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/7a2ebb6af2aec2df1a6fea562cc4f164";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    //get user id

    public static String getuserId() {
        final String methodPath = "caltrackerwebservices.userdetails/getCountOfUsers";
        // final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.userdetails/getCountOfUsers";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            // conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    //create a user

    public static String createReport(Report report) {
        final String methodPath = "caltrackerwebservices.report";
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
            String stringCourseJson =
                    //"{\"activitylevel\":2,\"address\":\"Caulfield, Melbourne\",\"dob\":\"1965-03-12T00:00:00+10:00\",\"email\":\"jane@gmail.com\",\"gender\":\"female\",\"height\":150.00,\"name\":\"Pleasework\",\"postcode\":3145,\"stepspermile\":3000,\"surname\":\"Doe\",\"userid\":13,\"weight\":70.00}";
                    gson.toJson(report);
            Log.d("JSON", stringCourseJson);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString()+" "+ conn.getResponseMessage());
            textResult = conn.getResponseMessage();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    //update report

    public static String UpdateReport(Report report, String reportId) {
        final String methodPath = "caltrackerwebservices.report/"+reportId;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
            String stringCourseJson =
                    //"{\"activitylevel\":2,\"address\":\"Caulfield, Melbourne\",\"dob\":\"1965-03-12T00:00:00+10:00\",\"email\":\"jane@gmail.com\",\"gender\":\"female\",\"height\":150.00,\"name\":\"Pleasework\",\"postcode\":3145,\"stepspermile\":3000,\"surname\":\"Doe\",\"userid\":13,\"weight\":70.00}";
                    gson.toJson(report);
            Log.d("JSON", stringCourseJson);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            //set the connection method to POST
            conn.setRequestMethod("PUT");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString()+" "+ conn.getResponseMessage());
            textResult = conn.getResponseMessage();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }
    //user credential


    //check to see if email exsisits

    public static String checkEmail(String email) {
        final String methodPath = "caltrackerwebservices.userdetails/findByEmail/"+ email;
        // final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.userdetails/getCountOfUsers";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // check to see if username exsists
    public static String checkUsername(String username) {
        final String methodPath = "caltrackerwebservices.usercredential/findByUsername/"+ username;
        // final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.userdetails/getCountOfUsers";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void userCred(UserCredential user) {
        final String methodPath = "caltrackerwebservices.usercredential";
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
            String stringCourseJson =
                    //"{\"activitylevel\":2,\"address\":\"Caulfield, Melbourne\",\"dob\":\"1965-03-12T00:00:00+10:00\",\"email\":\"jane@gmail.com\",\"gender\":\"female\",\"height\":150.00,\"name\":\"Pleasework\",\"postcode\":3145,\"stepspermile\":3000,\"surname\":\"Doe\",\"userid\":13,\"weight\":70.00}";
                    gson.toJson(user);
            Log.d("JSON", stringCourseJson);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString()+" "+ conn.getResponseMessage());
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    //get report details
    //GET consumption ID
    public static String reportId() {
        final String methodPath = "caltrackerwebservices.report/count";
        // final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.userdetails/getCountOfUsers";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            // conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //create new user
    public static void createUser(User user) {
        final String methodPath = "caltrackerwebservices.userdetails";
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
            String stringCourseJson =
                    //"{\"activitylevel\":2,\"address\":\"Caulfield, Melbourne\",\"dob\":\"1965-03-12T00:00:00+10:00\",\"email\":\"jane@gmail.com\",\"gender\":\"female\",\"height\":150.00,\"name\":\"Pleasework\",\"postcode\":3145,\"stepspermile\":3000,\"surname\":\"Doe\",\"userid\":13,\"weight\":70.00}";
                    gson.toJson(user);
            Log.d("JSON", stringCourseJson);

            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString()+" "+ conn.getResponseMessage());
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
    //get all food details

    public static String getReportDetails(String userId, String date){
        final String methodPath = "caltrackerwebservices.report/findCalorieDetails/"+userId+"/"+date;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    //get all food details

    public static String getFoodDetails(){
        final String methodPath = "caltrackerwebservices.food";
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //get food items

    public static String getFood(String category){
        final String methodPath = "caltrackerwebservices.food/findByCategory/" + category;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //GET consumption ID
    public static String getConsumptionId() {
        final String methodPath = "caltrackerwebservices.consumption/count";
        // final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.userdetails/getCountOfUsers";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            // conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //POST to Consumption table
    public static void createConsumption(Consumption consumption) {
        final String methodPath = "caltrackerwebservices.consumption";
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
           // String userJSON =
                    //"{\"activitylevel\":2,\"address\":\"Caulfield, Melbourne\",\"dob\":\"1965-03-12T00:00:00+10:00\",\"email\":\"jane@gmail.com\",\"gender\":\"female\",\"height\":150.00,\"name\":\"Pleasework\",\"postcode\":3145,\"stepspermile\":3000,\"surname\":\"Doe\",\"userid\":13,\"weight\":70.00}";
                   // gson.toJson(user);
            String stringConsumptionJSON = gson.toJson(consumption);
            Log.d("JSON", stringConsumptionJSON);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringConsumptionJSON.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringConsumptionJSON);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString()+" "+ conn.getResponseMessage());
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    //get food ID

    public static String getFoodId() {
        final String methodPath = "caltrackerwebservices.food/count";
        // final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.userdetails/getCountOfUsers";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            // conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //POST to food table

    public static void createFood(Food food) {
        final String methodPath = "caltrackerwebservices.food";
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
            // String userJSON =
            //"{\"activitylevel\":2,\"address\":\"Caulfield, Melbourne\",\"dob\":\"1965-03-12T00:00:00+10:00\",\"email\":\"jane@gmail.com\",\"gender\":\"female\",\"height\":150.00,\"name\":\"Pleasework\",\"postcode\":3145,\"stepspermile\":3000,\"surname\":\"Doe\",\"userid\":13,\"weight\":70.00}";
            // gson.toJson(user);
            String stringFood = gson.toJson(food);
            Log.d("JSON", stringFood);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringFood.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringFood);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString()+" "+ conn.getResponseMessage());
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }


    //get report data for start date

    public static String getReportData(String userId, String startDate) {
        final String methodPath = "caltrackerwebservices.report/findCalorieDetails/"+userId+"/"+ startDate ;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {

            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }



    //get report data for start and end date

    public static String getRangeReport(String userId, String startDate, String enddate) {
        final String methodPath = "caltrackerwebservices.report/getDetailsForDateRange/"+userId+"/"+ startDate+"/"+ enddate ;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {

            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }


    //get calories consumed for date

    public static String getCaloriesConsumed(String userId, String date) {
        final String methodPath = "caltrackerwebservices.consumption/findTotalCaloriesForDay/"+userId+"/"+date;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {

            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String getCaloriesBurnedAtRest(String userId) {
        final String methodPath = "caltrackerwebservices.userdetails/totalCaloriesBurnedAtRest/"+userId;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {

            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String getCalBurnedPerStep(String userId) {
        final String methodPath = "caltrackerwebservices.userdetails/findCalorieBurnedPerStep/"+userId;
        //final String methodPath = "http://192.168.56.1:23536/MobileAssignment1/webresources/caltrackerwebservices.usercredential/userlogin/poorni/298e3e859b53866aef611ba7c7c3f112f638e7d8";

        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {

            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        Log.d("--rest", textResult);
        return textResult;
    }

}