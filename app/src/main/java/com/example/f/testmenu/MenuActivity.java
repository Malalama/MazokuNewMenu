package com.example.f.testmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private Button EasyButton;
    private Button MediumButton;
    private Button HardButton;
    public static final String PREFS_NAME = "MyApp_Settings";
    private int recordEasyMilli1;
    private int recordEasyMilli2;
    private int recordEasyMilli3;
    private int recordMediumMilli;
    private int recordHardMilli;
    private SharedPreferences settings;
    private TextView recordEasy1;
    private TextView recordEasy2;
    private TextView recordEasy3;
    private TextView recordMedium1;
    private TextView recordMedium2;
    private TextView recordMedium3;
    private TextView recordHard1;
    private TextView recordHard2;
    private TextView recordHard3;
    private TextView recordMedium;
    private TextView recordHard;
    private UtilsMath utilmath;

    ListView mListView;

    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_menu);

        mListView = (ListView) findViewById(R.id.listview);

        afficherListeScores();


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        utilmath = new UtilsMath();

        recordEasy1 = (TextView) findViewById(R.id.recordEasy1);
        recordEasy2 = (TextView) findViewById(R.id.recordEasy2);
        recordEasy3 = (TextView) findViewById(R.id.recordEasy3);

        recordEasy1.setText("1 : " + utilmath.getTimeFromInt(settings.getInt("recordEasy1", 5999999)));
        recordEasy2.setText("2 : " + utilmath.getTimeFromInt(settings.getInt("recordEasy2", 5999999)));
        recordEasy3.setText("3 : " + utilmath.getTimeFromInt(settings.getInt("recordEasy3", 5999999)));

        recordMedium1 = (TextView) findViewById(R.id.recordMedium1);
        recordMedium2 = (TextView) findViewById(R.id.recordMedium2);
        recordMedium3 = (TextView) findViewById(R.id.recordMedium3);

        recordMedium1.setText("1 : " + utilmath.getTimeFromInt(settings.getInt("recordMedium1", 5999999)));
        recordMedium2.setText("2 : " + utilmath.getTimeFromInt(settings.getInt("recordMedium2", 5999999)));
        recordMedium3.setText("3 : " + utilmath.getTimeFromInt(settings.getInt("recordMedium3", 5999999)));

        recordHard1 = (TextView) findViewById(R.id.recordHard1);
        recordHard2 = (TextView) findViewById(R.id.recordHard2);
        recordHard3 = (TextView) findViewById(R.id.recordHard3);

        recordHard1.setText("1 : " + utilmath.getTimeFromInt(settings.getInt("recordHard1", 5999999)));
        recordHard2.setText("2 : " + utilmath.getTimeFromInt(settings.getInt("recordHard2", 5999999)));
        recordHard3.setText("3 : " + utilmath.getTimeFromInt(settings.getInt("recordHard3", 5999999)));



        EasyButton = (Button) findViewById(R.id.EasyButton);
        EasyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("gameLevel", "Easy");
                editor.commit();

                gotoGame(view);

            }
        });

        MediumButton = (Button) findViewById(R.id.MediumButton);
        MediumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("gameLevel", "Medium");
                editor.commit();

                gotoGame(view);

            }
        });

        HardButton = (Button) findViewById(R.id.HardButton);
        HardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("gameLevel", "Hard");
                editor.commit();

                gotoGame(view);

            }
        });

    }

/*    public void onBackPressed(){

    }*/


    private void afficherListeNoms(){
        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);
    }

    private List<ListItem> genererScores(){
        List<ListItem> scores = new ArrayList<ListItem>();
        scores.add(new ListItem("2x2", "1:00", "2:00","3:00",4,5,6,7));
        scores.add(new ListItem("3x3","2:00", "2:00","3:00",4,5,6,7));
        scores.add(new ListItem("4x4","3:00", "2:00","3:00",4,5,6,7));
        scores.add(new ListItem("5x5", "4:00", "2:00","3:00",4,5,6,7));

        return scores;
    }

    private void afficherListeScores(){
        List<ListItem> scores = genererScores();

        ListAdapter adapter = new ListAdapter(MenuActivity.this, scores);
        mListView.setAdapter(adapter);
    }



    public void gotoGame(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
