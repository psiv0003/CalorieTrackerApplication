package poornima.com.calorietrackerapplication.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

public class pieScreen  extends Fragment {

    View view;
    private PieChart chart;
    EditText startDate, endDate;
    final Calendar myCalendar = Calendar.getInstance();

    final Calendar myCalendarEnd = Calendar.getInstance();

    Button submitPie;
    String userId;
    Typeface tf;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.pie_fragment, container, false);

        getActivity().setTitle("Your Reports");
        startDate = (EditText) view.findViewById(R.id.startDateTxt);
        submitPie = (Button) view.findViewById(R.id.pieSubmit);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");

        final DatePickerDialog.OnDateSetListener startDateBldr = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartDate();
            }

        };


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), startDateBldr, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        chart = view.findViewById(R.id.pieChart1);


        submitPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startD = startDate.getText().toString();

                getPieData getPieData = new getPieData();

                getPieData.execute(userId, startD);


            }
        });



        return view;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Fitness Data\n" );
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    //get the pie Data
    private class getPieData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
            //  progressDialog = ProgressDialog.show(getActivity(),
            //      "ProgressDialog", "Waiting...");
            // progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            results =  RestWS.getReportData(params[0], params[1]);
            Log.d("Results", results);


            return results;

        }

        @Override
        protected void onPostExecute(String result) {
            // progressDialog.dismiss();

            try {
                chart.setCenterText(generateCenterText());
                chart.setCenterTextSize(10f);

                chart.setHoleRadius(45f);
                chart.setTransparentCircleRadius(50f);

                Legend l = chart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setDrawInside(false);
                chart.setData(generatePieData(results));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private void updateLabelStartDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDate.setText(sdf.format(myCalendar.getTime()));
    }
    protected PieData generatePieData(String data) throws JSONException {
        Log.d("PIE", "pie");

        ArrayList<PieEntry> entries1 = new ArrayList<>();
        double calBurned;
        double calRemaning;
        double calConsumed;



        JSONObject pieData = new JSONObject(data);

        calBurned = Double.parseDouble(pieData.getString("Total Calories Burned"));
        calRemaning = Double.parseDouble(pieData.getString("Remainng Calories"));
        calConsumed = Double.parseDouble(pieData.getString("Total Calories Consumed"));
        PieDataSet ds1 = new PieDataSet(entries1, "Your Data");

        if(calBurned == 0.0 && calConsumed== 0.0 && calConsumed ==0.0){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            alertDialogBuilder.setTitle("No Data")
                    .setMessage("Oops! Looks like you no data for that date! Please renter the date!")
                    .setCancelable(false)
                    .setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        else{

        }

        if(calRemaning < 0){
            calRemaning = Math.abs(calRemaning);
            entries1.add(new PieEntry((float) calRemaning, "Calorie Deficit" ));

        }
        else{
            entries1.add(new PieEntry((float) calRemaning, "Calorie Surplus" ));
        }
        entries1.add(new PieEntry((float) calConsumed, "Calories Consumed " ));
        entries1.add(new PieEntry((float) calBurned, "Calories Burned " ));


        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        Log.d("Data", d.getDataSet().toString());
        return d;


    }

}
