package poornima.com.calorietrackerapplication.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

public class barScreen extends Fragment implements OnChartGestureListener {
    View view;
    EditText startDate, endDate;
    final Calendar myCalendar = Calendar.getInstance();

    final Calendar myCalendarEnd = Calendar.getInstance();

    Button submitBtn;
    String userId;
    Boolean isChartThere = false;
    private BarChart chart;
    FrameLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.bar_screen, container, false);
         parent = view.findViewById(R.id.parentLayout);
        getActivity().setTitle("Your Reports");
        startDate = (EditText) view.findViewById(R.id.startDateTxt);
        endDate = (EditText) view.findViewById(R.id.endDateTxt);

        submitBtn = (Button) view.findViewById(R.id.pieSubmit);
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

        final DatePickerDialog.OnDateSetListener endDateBldr = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEndDate();
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

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), endDateBldr, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        chart = new BarChart(getActivity());
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isChartThere == true){

                    parent.removeView(chart);
                }


                String startD = startDate.getText().toString();
                String endD = endDate.getText().toString();


                getBarData getBarData = new getBarData();
                getBarData.execute(userId, startD, endD);




            }
        });


        chart.getDescription().setEnabled(false);
        chart.setOnChartGestureListener(this);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);


        return view;
    }

    //get the bar Data
    private class getBarData extends AsyncTask<String, Void, String> {
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
            results =  RestWS.getRangeReport(params[0], params[1], params[2]);
            Log.d("Results", results);

            return results;

        }

        @Override
        protected void onPostExecute(String result) {
            // progressDialog.dismiss();


            float calBurned;
            float calConsumed;
            float totalSteps;

            JSONObject barData = null;
            try {
                barData = new JSONObject(results);
                calBurned = Float.parseFloat(barData.getString("Total Calories Burned"));
                calConsumed = Float.parseFloat(barData.getString("Total Calories Consumed"));
                totalSteps = Float.parseFloat(barData.getString("Total Steps"));
                chart.setData(generateBarData( calBurned, calConsumed, totalSteps));
                chart.getAxisRight().setEnabled(false);
                XAxis xAxis = chart.getXAxis();
                xAxis.setEnabled(false);
                isChartThere = true;
                parent.addView(chart);
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

    private void updateLabelEndDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END");
        chart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart long pressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart fling. VelocityX: " + velocityX + ", VelocityY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    protected BarData generateBarData(float calBurned, float calConsumed, float totalSteps) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();

            ArrayList ent = new ArrayList();
            ent.add(calBurned);
            ent.add(calConsumed);
            ent.add(totalSteps);

            ArrayList<BarEntry> entries = new ArrayList<>();

            for(int i=0; i< ent.size(); i ++ ){
                entries.add(new BarEntry(i, (Float) ent.get(i)));
                BarDataSet ds = new BarDataSet(entries, getLabel(i));
                ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
                sets.add(ds);
            }

        BarData d = new BarData(sets);
        Log.d("GENERARED", ""+ d.toString());

        return d;
    }

    private final String[] mLabels = new String[] { "Calories Burned", "Calories Consumed", "Total Steps"};


    private String getLabel(int i) {
        return mLabels[i];
    }

}
