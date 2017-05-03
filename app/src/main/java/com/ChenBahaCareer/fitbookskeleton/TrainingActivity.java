package com.ChenBahaCareer.fitbookskeleton;

/**
 * Created by IK on 25/12/2014.
 */
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends ActionBarActivity {

    ArrayList<String> allExers = new ArrayList<String>();
    String id;
    int itemClicked;
    String wName;
    TableLayout tableLayout;
    static ArrayList<String> current_friends = new ArrayList<String>();
    EditText exerName;
    EditText weightNum;
    EditText setNum;
    EditText repNum;
    EditText goalRepNum;
    TextView exer,weight,set,rep,goalRep;

    GridLayout.Spec exerSpec = GridLayout.spec(0);
    GridLayout.Spec weightSpec = GridLayout.spec(0);
    GridLayout.Spec repSpec = GridLayout.spec(0);
    GridLayout.Spec setSpec = GridLayout.spec(1);
    GridLayout.Spec goalRepSpec = GridLayout.spec(1);

    GridLayout.Spec exerCol = GridLayout.spec(0);
    GridLayout.Spec weightCol = GridLayout.spec(1);
    GridLayout.Spec repCol = GridLayout.spec(2);
    GridLayout.Spec setCol = GridLayout.spec(0);
    GridLayout.Spec repGoalCol = GridLayout.spec(1);

    GridLayout.LayoutParams exerParams = new GridLayout.LayoutParams(exerSpec,exerCol);
    GridLayout.LayoutParams weightParams = new GridLayout.LayoutParams(weightSpec,weightCol);
    GridLayout.LayoutParams repParams = new GridLayout.LayoutParams(repSpec,repCol);
    GridLayout.LayoutParams setParams = new GridLayout.LayoutParams(setSpec,setCol);
    GridLayout.LayoutParams goalRepParams = new GridLayout.LayoutParams(goalRepSpec,repGoalCol);

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Parse.initialize(this, "ZlzULTgrXQC0lyYZLb8kX7fgz2TQU2YDRsjGkQsg", "NT6sZm7jhwdSfYffKbFWBe4GeRT0VyB36sxHHL6L");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabletest);
        View btn = findViewById(R.id.button3);
        Bundle b = getIntent().getExtras();
        if (b!= null){
            String [] str;
            String toSplit = b.getString("objID");
            str = toSplit.split(" ");
            id = str[0];
            itemClicked = Integer.parseInt(str[1]);
            wName = str[2];
            System.out.println("Selected OBJID: " + id);
            System.out.println("Selected NUMBER: " + itemClicked);
            System.out.println("Selected NAME: " + wName);
        }
        prepData(itemClicked);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Friendship");
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Friendship");
        query.whereEqualTo("toUser", ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("fromUser", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("status", "Mutual");
        query2.whereEqualTo("status", "Mutual");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null)
                    for (int i = 0; i < parseObjects.size(); i++) {
                        current_friends.add(parseObjects.get(i).get("fromUser").toString());
                    }
                else
                    System.out.println("Query Failed");
            }
        });
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null)
                    for (int i = 0; i < parseObjects.size(); i++) {
                        current_friends.add(parseObjects.get(i).get("toUser").toString());
                    }
                else
                    System.out.println("Query Failed");
            }
        });
    }

    public void newRow2 (View v){
        View btn = findViewById(R.id.button3);
        btn.setVisibility(View.VISIBLE);
        //LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutParams tableParams = new TableRow.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT,GridLayout.LayoutParams.WRAP_CONTENT);
        GridLayout tbr = new GridLayout(this);
        tbr.setColumnCount(4);
        tbr.setRowCount(2);

        tableLayout  = (TableLayout) findViewById(R.id.MainTrainingLayout);

        exerName = new EditText(this);
        exerName.setHint("Exercise");
        exerName.setId(View.generateViewId());
        exerName.setLayoutParams(exerParams);
        exerName.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        weightNum = new EditText(this);
        weightNum.setTextSize(15);
        weightNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        weightNum.setHint("Weight");
        weightNum.setId(View.generateViewId());
        weightNum.setLayoutParams(weightParams);

        repNum = new EditText(this);
        repNum.setTextSize(15);
        repNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        repNum.setHint("Reps");
        repNum.setId(View.generateViewId());
        repNum.setLayoutParams(repParams);

        setNum = new EditText(this);
        setNum.setTextSize(15);
        setNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        setNum.setHint("Sets");
        setNum.setId(View.generateViewId());
        setNum.setLayoutParams(setParams);

        goalRepNum = new EditText(this);
        goalRepNum.setTextSize(15);
        goalRepNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        goalRepNum.setHint("Goal Reps");
        goalRepNum.setId(View.generateViewId());
        goalRepNum.setLayoutParams(goalRepParams);

        exerName.setNextFocusDownId(weightNum.getId());
        weightNum.setNextFocusDownId(repNum.getId());
        repNum.setNextFocusDownId(setNum.getId());
        setNum.setNextFocusDownId(goalRepNum.getId());


        test = new TextView(this);
        test.setText("Test");

        tbr.addView(exerName,0);
        tbr.addView(weightNum,1);
        tbr.addView(repNum,2);
        tbr.addView(setNum,3);
        tbr.addView(goalRepNum,4);
        tableLayout.addView(tbr);
        System.out.println("Rows: " + tableLayout.getChildCount());
        System.out.println("ExerID: " + exerName.getId());
        System.out.println("WeightID: " + weightNum.getId());

        GridLayout test = (GridLayout) tableLayout.getChildAt(1);
        EditText  edittest = (EditText) test.getChildAt(0);
        String str = edittest.getText().toString();
        System.out.println("TESTNAME: " + str);

    }

    public void getExercise(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        allExers.clear();

        TableLayout exercises = (TableLayout) findViewById(R.id.MainTrainingLayout);
        for (int i = 1; i < exercises.getChildCount(); i++){
            GridLayout exer = (GridLayout) exercises.getChildAt(i);
            EditText exername = (EditText) exer.getChildAt(0);
            EditText weight = (EditText) exer.getChildAt(1);
            EditText rep = (EditText) exer.getChildAt(2);
            EditText set = (EditText) exer.getChildAt(3);
            EditText goalrep = (EditText) exer.getChildAt(4);
            String e = exername.getText().toString();
            String w = "Weight: "+ weight.getText().toString().replace ("Weight: ","");
            String r = "Reps: "+ rep.getText().toString().replace("Reps: ", "");
            String s = "Sets: "+ set.getText().toString().replace("Sets: ", "");
            String gr = "Goal Reps: "+ goalrep.getText().toString().replace("Goal Reps: ", "");
            allExers.add(e);
            allExers.add(w);
            allExers.add(r);
            allExers.add(s);
            allExers.add(gr);
        }
        System.out.println(allExers);
        ParseQuery <ParseObject> exerQuery = ParseQuery.getQuery("Workouts");
        exerQuery.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                try {
                    parseObject.remove("Exercises");
                    parseObject.addAll("Exercises",allExers);
                    parseObject.saveInBackground();
                    Toast.makeText(TrainingActivity.this,"Exercise Saved!",Toast.LENGTH_SHORT).show();
                }catch (NullPointerException f){
                    parseObject.addAll("Exercises",allExers);
                    parseObject.saveInBackground();
                    Toast.makeText(TrainingActivity.this,"Exercise Saved!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //feed//
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Feed");
        query2.whereContainedIn("username", current_friends);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < parseObjects.size(); i++) {
                        String goo = ParseUser.getCurrentUser().getUsername() + " has updated their workout called " + wName;
                        parseObjects.get(i).add("feed", goo);
                        parseObjects.get(i).saveInBackground();

                    }
                } else
                    System.out.println("Workout feed failed.");

            }
        });
    }

    public void prepData(final int t){
        ParseQuery<ParseObject> user = ParseQuery.getQuery("Workouts");
        user.whereEqualTo("Username", ParseUser.getCurrentUser().getUsername());

        ParseQuery<ParseObject> share = ParseQuery.getQuery("Workouts");
        share.whereEqualTo("Share", ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(user);
        queries.add(share);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                try {
                    System.out.println("NUMBER OF WORKOUTS: " + list.size());
                    List<String> array = list.get(t).getList("Exercises");
                    System.out.println("EXERCISES: " + array);
                    getData(array);
                }catch(NullPointerException f){
                    Toast.makeText(TrainingActivity.this, "No exercises in this workout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(List<String> array){

        tableLayout  = (TableLayout) findViewById(R.id.MainTrainingLayout);
        ArrayList <String> exers = new ArrayList<String>();
        exers.addAll(array);
        for (int x = 1; x < tableLayout.getChildCount(); x++){
            tableLayout.removeViewAt(x);
        }

        for (int i = 0; i < exers.size()/5; i++){
            GridLayout tbr = new GridLayout(this);
            tbr.setColumnCount(4);
            tbr.setRowCount(2);

            EditText loadedExer = new EditText(this);
            loadedExer.setText(exers.get(5 * i));
            loadedExer.setId(View.generateViewId());
            loadedExer.setLayoutParams(exerParams);

            EditText loadedWeight = new EditText(this);
            loadedWeight.setText(exers.get(1 + (5 * i)));
            loadedWeight.setId(View.generateViewId());
            loadedWeight.setLayoutParams(weightParams);
            loadedWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
            loadedWeight.setOnFocusChangeListener(new appendOnFocusListener());

            EditText loadedRep = new EditText(this);
            loadedRep.setText(exers.get(2 + (5 * i)));
            loadedRep.setId(View.generateViewId());
            loadedRep.setLayoutParams(repParams);
            loadedRep.setInputType(InputType.TYPE_CLASS_NUMBER);
            loadedRep.setOnFocusChangeListener(new appendOnFocusListener());

            EditText loadedSet = new EditText(this);
            loadedSet.setText(exers.get(3 + (5 * i)));
            loadedSet.setId(View.generateViewId());
            loadedSet.setLayoutParams(setParams);
            loadedSet.setInputType(InputType.TYPE_CLASS_NUMBER);
            loadedSet.setOnFocusChangeListener(new appendOnFocusListener());

            EditText loadedGoal = new EditText(this);
            loadedGoal.setText(exers.get(4 + (5 * i)));
            loadedGoal.setId(View.generateViewId());
            loadedGoal.setLayoutParams(goalRepParams);
            loadedGoal.setInputType(InputType.TYPE_CLASS_NUMBER);
            loadedGoal.setOnFocusChangeListener(new appendOnFocusListener());

            loadedExer.setNextFocusForwardId(loadedWeight.getId());
            loadedWeight.setNextFocusForwardId(loadedRep.getId());
            loadedRep.setNextFocusForwardId(loadedSet.getId());
            loadedSet.setNextFocusForwardId(loadedGoal.getId());

            tbr.addView(loadedExer, 0);
            tbr.addView(loadedWeight,1);
            tbr.addView(loadedRep,2);
            tbr.addView(loadedSet,3);
            tbr.addView(loadedGoal,4);
            tableLayout.addView(tbr);
        }


    }

    public void storeData(){
            System.out.println("id: "+id);
            System.out.println("allExers: "+ allExers);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Workouts");
            query.whereEqualTo("WorkoutName",id);
            query.getInBackground(id, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException g) {
                    if (g == null) {
                        object.addAll("Exercises", allExers);
                        System.out.println("getInBackground allExers: "+ allExers);
                        object.saveInBackground();
                        Toast.makeText(TrainingActivity.this, "Workout Saved", Toast.LENGTH_SHORT).show();
                        //allExers.clear();
                    } else {
                        Log.d("failed", "failed");
                    }
                }
            });
    }


    private class appendOnFocusListener implements View.OnFocusChangeListener{
        String text = null;
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            EditText v = (EditText)view;
            if (hasFocus){
                text = v.getText().toString();
                v.setText(text.replace((text.substring(0, text.indexOf(":") + 2)), ""));
                System.out.println ("has focus text value is: " + text);
            }
            if (!hasFocus){
                v.setText(//"Weight: "+
                        text);
                System.out.println("Lost focus text value is: " + text);
                text="";

            }
        }
    }


}



