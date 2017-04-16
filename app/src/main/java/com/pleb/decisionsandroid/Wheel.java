package com.pleb.decisionsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sev on 16/04/2017.
 */

public class Wheel extends AppCompatActivity{

    String Names[] = {"ZAK&Sev", "Marti", "Lisa", "Svaal", "Robin"};
    float occurrence[] = {2f, 1f, 1f, 1f, 1f};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPieChart();
    }

    private void setupPieChart() {

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < occurrence.length; i++){
            pieEntries.add(new PieEntry(occurrence[i], Names[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Names");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(2000);
        chart.invalidate();
    }

}
