package poornima.com.calorietrackerapplication.Fragments;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;
import android.widget.Toast;

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

import poornima.com.calorietrackerapplication.Activities.loginActivity;
import poornima.com.calorietrackerapplication.Activities.signUpActivity;
import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

public class ReportScreen extends Fragment {
    View view;

    ConstraintLayout dayLayout ;

    TextView rangeLayout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.report_fragment, container, false);

        getActivity().setTitle("Your Reports");

        dayLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
        rangeLayout = (TextView) view.findViewById(R.id.cg1Txt);

        dayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pieScreen pieScreen = new pieScreen();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,
                        pieScreen).commit();
            }
        });

        rangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barScreen barScreen = new barScreen();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,
                        barScreen).commit();

            }
        });





        return view;
    }




}
