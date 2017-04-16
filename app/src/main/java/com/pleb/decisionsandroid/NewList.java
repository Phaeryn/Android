package com.pleb.decisionsandroid;

/**
 * Created by Maxime on 15/04/2017.
 */


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.util.DialogUtils;

@SuppressLint("ShowToast")
public class NewList extends AppCompatActivity implements ColorChooserDialog.ColorCallback {
    //The decisions that the user adds
    ArrayList<String> addedDecisions;
    //decisions list
    TextView decisionList;
    //Questions EditText
    EditText editListName;
    // color chooser dialog
    private int primaryPreselect;
    // UTILITY METHODS
    private int accentPreselect;

    View colorbox;

    //our toast
    Toast toasty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlist);

        //for icon up navigation
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        //Get our views
        decisionList = (TextView) findViewById(R.id.decisionList);
        editListName = (EditText) findViewById(R.id.editListName);
        colorbox = findViewById(R.id.colorbox);


        //set up toast
        toasty = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        //Set up Array List
        addedDecisions = new ArrayList<String>();
    }

    //The Undo Button
    public void undoDecision(View view)
    {
        if(decisionList.getText().toString().isEmpty())
        {
            //Toast User
            toasty.setText("There is no item to remove!");
            toasty.show();
        }
        else
        {
            //Remove from textview
            String tempString = decisionList.getText().toString().replace(
                    addedDecisions.get(addedDecisions.size() - 1) + "\n", "");

            //Remove the added Decision
            addedDecisions.remove(addedDecisions.size() - 1);

            //Set the textview
            decisionList.setText(tempString);
        }
    }

    public void saveDecision(View view) {

        //If there is no question
        if(editListName.getText().toString().isEmpty())
        {
            //Toast User
            toasty.setText("You need to enter a list name!");
            toasty.show();
        }
        //There are no decisions
        else if(decisionList.getText().toString().isEmpty())
        {
            //Toast User
            toasty.setText("You need to enter an item");
            toasty.show();
        }
        //We're all good to save
        else
        {
            //Create Decision in our object
            Tab1.decisions.addNewDecision(editListName.getText().toString(), addedDecisions);

            //Toast User
            toasty.setText("List saved!");
            toasty.show();

            //And then Finish
            finish();
        }

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_question, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //The Up Button
        if(id == android.R.id.home)
        {
            finish();
        }
        //The save button
        else if(id == R.id.finish)
        {
            //If there is no question
            if(editListName.getText().toString().isEmpty())
            {
                //Toast User
                toasty.setText("You need to enter a list name!");
                toasty.show();
            }
            //There are no decisions
            else if(decisionList.getText().toString().isEmpty())
            {
                //Toast User
                toasty.setText("You need to enter an item!");
                toasty.show();
            }
            //We're all good to save
            else
            {
                //Create Decision in our object
                Tab1.decisions.addNewDecision(editListName.getText().toString(), addedDecisions);

                //Toast User
                toasty.setText("List saved!");
                toasty.show();

                //And then Finish
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void showColorChooserPrimary(View view) {

       new ColorChooserDialog.Builder(this, R.string.color_palette)
                .titleSub(R.string.colors)
                //.preselect(primaryPreselect)
                .show();
    }

    public void showInputItem(View view) {

        new MaterialDialog.Builder(this)
                .title("Add item")
                .inputRangeRes(1, 25, R.color.material_red_400)
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        //Get the added decisions and add it to our array list
                        String tempString = input.toString();
                        addedDecisions.add(tempString);

                        //Now add it to our textview
                        decisionList.setText(decisionList.getText().toString() + tempString + "\n");
                    }
                }).show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int color) {
        if (dialog.isAccentMode()) {
            accentPreselect = color;
            ThemeSingleton.get().positiveColor = DialogUtils.getActionTextStateList(this, color);
            ThemeSingleton.get().neutralColor = DialogUtils.getActionTextStateList(this, color);
            ThemeSingleton.get().negativeColor = DialogUtils.getActionTextStateList(this, color);
            ThemeSingleton.get().widgetColor = color;
        } else {
            primaryPreselect = color;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
                getWindow().setNavigationBarColor(color);
            }
        }
        colorbox.setBackgroundColor(color);
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }


}