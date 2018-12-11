package com.example.saif.gradetracker;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PiegraphFragment extends Fragment {

    PieChart pieChart;
    BarChart barChart;
    DatabaseHelper mDatabaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_piegraph,container,false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        barChart = (BarChart) view.findViewById(R.id.barChart);

        mDatabaseHelper = new DatabaseHelper(getContext());


        setUpPieChart();
        setBarChart();



        return view;
    }

    public void setUpPieChart(){

        ArrayList<String> getNameForPieChart = new ArrayList<String>();
        ArrayList<Float> getGradeForPieChart  = new ArrayList<Float>();
        ArrayList<Float> getCreditForPieChart  = new ArrayList<Float>();

        List<PieEntry> pieEntries = new ArrayList<>();


        Cursor cursor =  mDatabaseHelper.getData();

        while(cursor.moveToNext()){
            getGradeForPieChart.add(cursor.getFloat(3));
            getCreditForPieChart.add(cursor.getFloat(2));
            getNameForPieChart.add(cursor.getString(1));

        }

        for (int i=0;i<getGradeForPieChart.size();i++){
            pieEntries.add(new PieEntry(getCreditForPieChart.get(i),getNameForPieChart.get(i)));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(pieDataSet);

        data.setValueTextSize(10);

        Description description = new Description();

        description.setText("Completed Courses");
        description.setTextSize(10);

        pieChart.setDescription(description);

        pieChart.setData(data);

        pieChart.animateY(2000);




    }

    public void setBarChart(){

        ArrayList<String> getNameForBarChart = new ArrayList<String>();
        ArrayList<Float> getGradeForBarChart  = new ArrayList<Float>();
        ArrayList<Float> getCreditForBarChart  = new ArrayList<Float>();
        ArrayList<Float> getIdForBarChart  = new ArrayList<Float>();


        ArrayList<BarEntry> barEntries = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.getData();

        while(cursor.moveToNext()){
            getIdForBarChart.add(cursor.getFloat(0));
            getNameForBarChart.add(cursor.getString(1));
            getCreditForBarChart.add(cursor.getFloat(2));
            getGradeForBarChart.add(cursor.getFloat(3));
        }
        float j=0;
        for (int i=0;i<getGradeForBarChart.size();i++){
            barEntries.add(new BarEntry(j, getGradeForBarChart.get(i)));
            j++;
        }


        BarDataSet barDataSet = new BarDataSet(barEntries,"Course");



        ArrayList<String> labels = new ArrayList<>();

        for (int i=0;i<getNameForBarChart.size();i++){
            labels.add(getNameForBarChart.get(i));

        }

        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1);
        barData.setValueTextSize(10);

        Description description = new Description();

        description.setText("Courses With Grades");
        description.setTextSize(10);

        barChart.setDescription(description);


        barChart.setData(barData);
        barChart.animateY(2000);
        barChart.setBorderWidth(10);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));





    }
}
