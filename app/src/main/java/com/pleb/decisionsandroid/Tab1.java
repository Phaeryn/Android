package com.pleb.decisionsandroid;

/**
 * Created by Maxime on 11/04/2017.
 */

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuInflater;
import android.content.DialogInterface;



public class Tab1 extends Fragment{


    //Our Decisions object - If memory leak, It'll be here
    public static DecisionsFile decisions;
    ArrayList<ArrayList<String>> decisionsArray;
    ListView listView;
    ListViewAdapter mAdapter;

    //How many default remaining Decisions
    //private final int REMAINING_COUNT = 100;

    //For remaining Decisions, public static, because we can change it
    //Though, I should probably save the value in preferences
    //int remainingInt;
    //boolean remainingBool;
    //boolean fiveBool;
    //boolean tenBool;
    SharedPreferences prefs;

    //our toast
    Toast toasty;

    //Views
    TextView count;
    TextView notFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        //Create our File manager
        decisions = new DecisionsFile(getActivity().getApplicationContext());
        setHasOptionsMenu(true);

        //Initilize listview
        listView = (ListView) rootView.findViewById(R.id.listView);

        //set up toast
        toasty = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);

        //View that is often updated
        count = (TextView) rootView.findViewById(R.id.listCount);
        notFound = (TextView) rootView.findViewById(R.id.notFound);
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //Calculate the remaining decisions/Initialize
        reInitialize();
    }

    //Easily reInitializes everything for us
    private void reInitialize()
    {
        //Get our decisions
        decisionsArray = decisions.getDecisions();

        //Get preferences
        prefs = getContext().getSharedPreferences("MyPrefs", 0);

        if (decisionsArray.size() == 0)
            count.setText("Press the '+' button to create a list");
        else
            count.setText("Lists created: " + decisionsArray.size() + "");

        //Need to check if we have decisions
        if(decisions.isEmpty())
        {
            //Hide our listview
            listView.setVisibility(View.GONE);
            //Make sure not found is visible
            notFound.setVisibility(View.VISIBLE);

            //Do not alter remainining Int's size
        }
        else
        {
            //Hide our Textview
            notFound.setVisibility(View.GONE);

            //Set up our listview
            listView.setVisibility(View.VISIBLE);
            createList();
        }
    }


    private void createList()
    {
        //First we must put all of the names of our questions into an Array
        //decisionsArray = decisions.getDecisions(); - Called in Reinitialize
        String[] questions = new String[decisionsArray.size()];

        for(int i = 0; i < decisionsArray.size(); ++i)
        {
            //Put the questions of the given index into the Array
            questions[i] = decisionsArray.get(i).get(0);
            Log.d("LIST", questions[i]);
        }


        //setting up adapter and putting in the list view
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, questions);
        //listView.setAdapter(adapter);
        ListViewAdapter<String> adapter = new ListViewAdapter<String>(getContext(), R.layout.listview_swipe, R.id.text_data, questions);
        listView.setAdapter(adapter);

        //});
        //mAdapter.setMode(Attributes.Mode.Multiple);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((SwipeLayout)(listView.getChildAt(position - listView.getFirstVisiblePosition()))).open(true);
                Intent intent = new Intent(getActivity().getApplicationContext(), Decision.class);
                intent.putExtra("INDEX", position);
                startActivity(intent);
            }
        });


        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();


                //have to create final alert dialog builder out here for long click
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //create another for delete note
                final AlertDialog.Builder delete = new AlertDialog.Builder(getContext());
                final int pos = position;

                //setting up alert dialog
                builder.setMessage("Options")
                        .setNegativeButton("EDIT LIST", new DialogInterface.OnClickListener()
                        {
                            //Edit Decision
                            public void onClick(DialogInterface dialog, int id)
                            {

                                ///Open the Decisions thingy, and give the index to our next activity
                                Intent intent = new Intent(getActivity().getApplicationContext(), EditDecision.class);
                                intent.putExtra("INDEX", pos);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("DELETE LIST", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                delete.setMessage("Confirm delete?")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id)
                                            {
                                                //Yes They Would like to Delete
                                                //Remove the array at the position they clicked
                                                decisions.removeDecision(pos);

                                                //Recreate the listview and re-get the array and stuff
                                                reInitialize();

                                                //If above doesnt work, restart the activity, by starting it, and
                                                //finishing this one
                                            }
                                        })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                            }
                                        });
                                // Create the Alert and return true for the case
                                delete.create();
                                delete.show();
                            }
                        });
                // Create the Alert and return true for the case
                builder.create();
                builder.show();


                return true;
            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("Listview", "onScroll");
            }
        });


        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });




        /*

        //Our on ItemClick Listener
        OnItemClickListener listclick = new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                //Open the Decisions thingy, and give the index to our next activity
                Intent intent = new Intent(getActivity().getApplicationContext(), Decision.class);
                intent.putExtra("INDEX", position);
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(listclick);

        //NOW OUR LONG CLICK LISTENER

        //have to create final alert dialog builder out here for long click
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        //create another for delete note
        final AlertDialog.Builder delete = new AlertDialog.Builder(getActivity().getApplicationContext());
        //listener for long click
        OnItemLongClickListener longClick = new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                //setting up alert dialog
                builder.setMessage("Long click")
                        .setNegativeButton("Long edit decision", new DialogInterface.OnClickListener()
                        {
                            //Edit Decision
                            public void onClick(DialogInterface dialog, int id)
                            {

                                ///Open the Decisions thingy, and give the index to our next activity
                                Intent intent = new Intent(getActivity().getApplicationContext(), EditDecision.class);
                                intent.putExtra("INDEX", position);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Long delete decision", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                delete.setMessage("Delete confirm")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id)
                                            {
                                                //Yes They Would like to Delete
                                                //Remove the array at the position they clicked
                                                decisions.removeDecision(position);

                                                //Recreate the listview and re-get the array and stuff
                                                reInitialize();

                                                //If above doesnt work, restart the activity, by starting it, and
                                                //finishing this one
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                            }
                                        });
                                // Create the Alert and return true for the case
                                delete.create();
                                delete.show();
                            }
                        });
                // Create the Alert and return true for the case
                builder.create();
                builder.show();
                return true;
            }
        };

        listView.setOnItemLongClickListener(longClick);*/

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        getActivity().getMenuInflater().inflate(R.menu.start, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
