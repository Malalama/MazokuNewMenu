package com.example.f.testmenu;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.Random;
import java.util.stream.IntStream;


import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /** Called when the activity is first created. */

    private AdView mAdView;

    private UtilsMath utilmath = new UtilsMath();
    private UtilsGrid utilgrid = new UtilsGrid();

    private MazeCreator mazecreator = null;

    private int recordEasyMilli;
    private int recordMediumMilli;
    private int recordHardMilli;

    private int recordEasyMilli1;
    private int recordEasyMilli2;
    private int recordEasyMilli3;
    private int recordMediumMilli1;
    private int recordMediumMilli2;
    private int recordMediumMilli3;
    private int recordHardMilli1;
    private int recordHardMilli2;
    private int recordHardMilli3;


    private int[] recordArray = new int[4];
    private int[] recordEasyArray = new int[4];
    private int[] recordMediumArray = new int[4];
    private int[] recordHardArray = new int[4];

    private int recordMilli;
    private String recordSettingName;

    private int numberOfGames = 10;
    private GridView g;
    private int numCol = 3;
    private int cellWidth;
    private int heightMyItem;
    private int widthMyItem;

    public static final String PREFS_NAME = "MyApp_Settings";
    public String gameLevel;
    public Log log;

    //For the timer
    private Button menuButton;
    private Button pauseButton;
    private Button previousButton;
    private Button nextButton;
    private TextView timerValue;
    private TextView mazeNumber;

    private SharedPreferences settings;

    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    //End of --- For the timer

    private int mazeID;
    Maze maze;
    List<String> items = new ArrayList<String>();
    boolean[][] beenThere;
    boolean dragging = false;
    private Context context;
    boolean gameWon = false;
    boolean gameLost = false;
    boolean firstMoveHappened = false;
    long timePenalties = 0L;
    int[] randomGames;

    //FC - Crossfade stuff - 2014 08 13
    private View mSplashView;
    private View mGameView;
    private int mShortAnimationDuration;
    //End of FC - Crossfade stuff - 2014 08 13

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setBackgroundDrawable(new ColorDrawable());


        //super(context);
        //this.context = (Activity)context;

        //super.onCreate(icicle);

        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_main);

        //Ads
        MobileAds.initialize(this, "ca-app-pub-9353328510428218~4290578930");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        g = (GridView)findViewById(R.id.myGrid);
        g.setVerticalSpacing(1);
        g.setHorizontalSpacing(1);



        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//
//        // Writing data to SharedPreferences
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("gameLevel", "Easy");
//        editor.commit();

        // Reading from SharedPreferences
        gameLevel = settings.getString("gameLevel", "Easy");
        recordEasyMilli = settings.getInt("recordEasy", 5999999);
        recordMediumMilli = settings.getInt("recordMedium", 5999999);
        recordHardMilli = settings.getInt("recordHard", 5999999);


        SharedPreferences.Editor editor = settings.edit();


        recordEasyMilli1 = settings.getInt("recordEasy1", 5999999);
        recordEasyMilli2 = settings.getInt("recordEasy2", 5999999);
        recordEasyMilli3 = settings.getInt("recordEasy3", 5999999);
        recordMediumMilli1 = settings.getInt("recordMedium1", 5999999);
        recordMediumMilli2 = settings.getInt("recordMedium2", 5999999);
        recordMediumMilli3 = settings.getInt("recordMedium3", 5999999);
        recordHardMilli1 = settings.getInt("recordHard1", 5999999);
        recordHardMilli2 = settings.getInt("recordHard2", 5999999);
        recordHardMilli3 = settings.getInt("recordHard3", 5999999);

        recordEasyArray[0] = recordEasyMilli1;
        recordEasyArray[1] = recordEasyMilli2;
        recordEasyArray[2] = recordEasyMilli3;

        recordMediumArray[0] = recordMediumMilli1;
        recordMediumArray[1] = recordMediumMilli2;
        recordMediumArray[2] = recordMediumMilli3;

        recordHardArray[0] = recordHardMilli1;
        recordHardArray[1] = recordHardMilli2;
        recordHardArray[2] = recordHardMilli3;

        g.setNumColumns(numCol);
        selectRandomGames();
        mazeID++;



        try {mazecreator = new MazeCreator(getApplicationContext(), gameLevel);} catch (IOException e) {e.printStackTrace();}

        prepareGame();
        addListenerToGrid();



        //For the timer
        timerValue = (TextView) findViewById(R.id.timerValue);
        mazeNumber = (TextView) findViewById(R.id.mazeNumber);





/*        pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });*/
        //End of --- For the timer

/*        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //if (gameWon){

                if (mazeID < numberOfGames) {
                    mazeID++;
                    prepareGame();
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }
                //}

            }
        });

        previousButton = (Button) findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //if (gameWon){
                if (mazeID > 1) {
                    mazeID--;
                    prepareGame();
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }
                //}

            }
        });*/


        mGameView = findViewById(R.id.root);
        //mSplashView = findViewById(R.id.SplashTextView);
        // Initially hide the content view.
        //mGameView.setVisibility(View.GONE);
        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = 750; //getResources().getInteger(android.R.integer.config_shortAnimTime);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_level1) {
            // Handle the camera action
        } else if (id == R.id.nav_level2) {

        } else if (id == R.id.nav_level3) {

        } else if (id == R.id.nav_trophy) {

            startActivity(new Intent(this, MenuActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoMenu(View view){

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);

    }

    ///**
    //* Cross-fades between {@link #mContentView} and {@link #mLoadingView}.
    //*/
    @SuppressLint("NewApi")
    private void CrossFader() {
        // Decide which view to hide and which to show.
        final View showView = mGameView;
        final View hideView = mSplashView;

        // Set the "show" view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);

        // Animate the "show" view to 100% opacity, and clear any animation listener set on
        // the view. Remember that listeners are not limited to the specific animation
        // describes in the chained method calls. Listeners are set on the
        // ViewPropertyAnimator object for the view, which persists across several
        // animations.
        showView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
        // to GONE as an optimization step (it won't participate in layout passes, etc.)
        hideView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            updatedTime += timePenalties;

            timerValue.setText(utilmath.getTimeFromInt((int) updatedTime));

            customHandler.postDelayed(this, 0);

        }
    };



    public void updateMazeNumber(String toThis) {
        TextView textView = (TextView) findViewById(R.id.mazeNumber);
        textView.setText(toThis);
    }


    public void selectRandomGames(){

        UniqueRandomNumbers a = new UniqueRandomNumbers();
        randomGames = a.getRandomNumbers(numberOfGames, 1, 50);

        System.out.println("---------------");
        for(int i = 1; i <= numberOfGames; i++)
        {System.out.println("Random Value " + i + " : " + randomGames[i]);}
        System.out.println("---------------");

    }


    private void resetTimerValue(){

        //We reset the timer
        timePenalties = 0;
        updatedTime = 0;
        timeSwapBuff = 0;

    }



    private Maze CreateMaze(MazeCreator mazecreator){
        try {
            return mazecreator.getMaze(randomGames[mazeID], getApplicationContext());
        }
        catch (IOException e) {e.printStackTrace(); return null;}
    }

    private void prepareGame() {


        System.out.println("-------prepareGame-------");
        System.out.println(new java.util.Date());




        System.out.println(new java.util.Date());

        cellWidth = utilgrid.getCellWidth(numCol);


        //final Maze maze = CreateMaze(mazecreator);
        maze = CreateMaze(mazecreator);

        int[][] TilesPoints = maze.getTilesPoints();
        items.clear();

        System.out.println("After items.clear(); --- " + new java.util.Date());

        System.out.println("numCol = " + numCol);

        for(int i=0;i<numCol;i++) {
            for(int j=0;j<numCol;j++) {
                items.add(Integer.toString(TilesPoints[i][j]));
            }
        }

        System.out.println("After for ---- " + new java.util.Date());

        items.set(0, ""); // = "B";
        items.set(utilgrid.getLastCell(numCol), ""); //

        firstMoveHappened = false;
        gameWon = false;

        //We draw our Grid View
        drawGridView(maze, items);

        //We reset the timer
        //resetTimerValue();

        //We set the maze number
        updateMazeNumber("" + String.format("%02d", mazeID) + "/" + numberOfGames + "  ");
        //String.format("%02d", secs)

        System.out.println(new java.util.Date());
        System.out.println("///-------prepareGame-------///");

    }

    public void drawGridView(final Maze maze, List<String> items){

        ArrayAdapter<String> notes = new ArrayAdapter<String>(getBaseContext(), R.layout.my_item_view, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);



                int x_pos = utilmath.getXFromPosition(position, numCol);
                int y_pos = utilmath.getYFromPosition(position, numCol);
                int[][] TilesPoints = maze.getTilesPoints();

                beenThere = maze.getBeenThere();
                //System.out.println("(drawGridView) Position (" + position + ")  ->  XY (" + x_pos + y_pos + ")");



                if (beenThere[x_pos][y_pos]==true) {
                    if (gameWon){
                        view.setBackgroundColor(getResources().getColor(R.color.palette1_wingreen));
                    }
                    else if (gameLost){

                        view.setBackgroundColor(getResources().getColor(R.color.palette1_losered)); //Color.argb(255, 255, 0, 51));
                    }
                    else{
                        view.setBackgroundColor(getResources().getColor(R.color.palette1_seablue));
                    }
                }

                //System.out.println("(drawGridView) view.getWidth(): " + view.getWidth());
                //System.out.println("(drawGridView) view.getHeight(): " + view.getHeight());

                //view.setLayoutParams(new ViewGroup.LayoutParams(view.getWidth(), view.getWidth()));

                return view;
            }
        };

        g.setAdapter(notes);

    }

    public void updateBestScores(int newScore){

        System.out.println("---updateBestScores---");
        System.out.println("newScore: " + newScore);
        //System.out.println("gameLevel: '" + gameLevel + "'");
        System.out.println(gameLevel.equals("Easy"));
        System.out.println(gameLevel.equals("Medium"));

        if (gameLevel.equals("Easy")){

            if (newScore < recordEasyArray[0]){

                System.out.println("New record n°1!!");

                recordEasyArray[2] = recordEasyArray[1];
                recordEasyArray[1] = recordEasyArray[0];
                recordEasyArray[0] = newScore;

            }
            else if (newScore < recordEasyArray[1]){

                System.out.println("New record n°2!!");

                recordEasyArray[2] = recordEasyArray[1];
                recordEasyArray[1] = newScore;

            }
            else if (newScore < recordEasyArray[2]){

                System.out.println("New record n°3!!");

                recordEasyArray[2] = newScore;

            }

        }

        if (gameLevel.equals("Medium")){

            System.out.println("Here");

            if (newScore < recordMediumArray[0]){

                recordMediumArray[2] = recordMediumArray[1];
                recordMediumArray[1] = recordMediumArray[0];
                recordMediumArray[0] = newScore;

            }
            else if (newScore < recordMediumArray[1]){

                recordMediumArray[2] = recordMediumArray[1];
                recordMediumArray[1] = newScore;

            }
            else if (newScore < recordMediumArray[2]){

                recordMediumArray[2] = newScore;

            }

        }

        if (gameLevel.equals("Hard")){

            if (newScore < recordHardArray[0]){

                recordHardArray[2] = recordHardArray[1];
                recordHardArray[1] = recordHardArray[0];
                recordHardArray[0] = newScore;

            }
            else if (newScore < recordHardArray[1]){

                recordHardArray[2] = recordHardArray[1];
                recordHardArray[1] = newScore;

            }
            else if (newScore < recordHardArray[2]){

                recordHardArray[2] = newScore;

            }

        }

        updateArrayBestScore();


    }

    public void updateArrayBestScore(){

        SharedPreferences.Editor editor = settings.edit();

        if (gameLevel == "Easy") {

            editor.putInt("recordEasy1", recordEasyArray[0]).commit();
            editor.putInt("recordEasy2", recordEasyArray[1]).commit();
            editor.putInt("recordEasy3", recordEasyArray[2]).commit();

        }

        else if (gameLevel == "Medium") {



            editor.putInt("recordMedium1", recordMediumArray[0]).commit();
            editor.putInt("recordMedium2", recordMediumArray[1]).commit();
            editor.putInt("recordMedium3", recordMediumArray[2]).commit();

        }

        else if (gameLevel == "Hard") {

            editor.putInt("recordHard1", recordHardArray[0]).commit();
            editor.putInt("recordHard2", recordHardArray[1]).commit();
            editor.putInt("recordHard3", recordHardArray[2]).commit();

        }

    }


   public void WonSnackbar() {

 /*        // Works
        //Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Won!!",
        //        Snackbar.LENGTH_SHORT).show();
        //End of Works

        String textSnackbar = "Won " + String.format("%02d", mazeID) + "/" + numberOfGames;

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.myCoordinatorLayout), textSnackbar, Snackbar.LENGTH_SHORT);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.GREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        snackbar.show();
  */
    }

    public void addListenerToGrid() {


 /*       g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
/*
        g.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                int action = me.getActionMasked();
                float currentXPosition = me.getX();
                float currentYPosition = me.getY();
                int position = g.pointToPosition((int) currentXPosition, (int) currentYPosition);

                System.out.println("position: " + position);

                //Toast.makeText(MainActivity.this, "" + position,
                //Toast.LENGTH_SHORT).show();

                if (action == MotionEvent.ACTION_UP) {
                // Key was pressed here
                }


                return true;
            }

        });*/

        g.setOnTouchListener(new AdapterView.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent me) {




                int action = me.getActionMasked();
                float touchX = me.getX();
                float touchY = me.getY();
                int position = g.pointToPosition((int) touchX, (int) touchY);

                //We get the current (X,Y) position on our maze
                int currentX = maze.getCurrentX();
                int currentY = maze.getCurrentY();

                //System.out.println("currentX = " + currentX);
                //System.out.println("currentY = " + currentY);

                int totalCellWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (float) cellWidth, getResources().getDisplayMetrics());

                //System.out.println("(addListenerToGrid) totalCellWidth = " + totalCellWidth);


                int totalCellHeight = totalCellWidth; //250

                int cellX = 0;
                int cellY = 0;
                boolean moved;



                //As soon as the game is won, we disable the moves
                if (gameWon == false){ // || maze.isCurrentCellNextToEndCell()){

                    cellY = position / 5;
                    cellX = position % 5;

                    switch (me.getAction() & MotionEvent.ACTION_MASK) {

                        //Touch gesture started: the screen was previously not touched and now is.
                        //... So, the player has one finger on a cell of our maze
                        case MotionEvent.ACTION_DOWN:

                            System.out.println("===ACTION_DOWN===");

                            gameLost = false;

                            if (firstMoveHappened == false){
                                firstMoveHappened = true;
                                startTime = SystemClock.uptimeMillis();
                                customHandler.postDelayed(updateTimerThread, 0);
                            }

                            System.out.println("MotionEvent.ACTION_DOWN");

                            //---Change 2018 03 21---:
                            //cellX = (int)Math.floor(touchX/totalCellWidth);
                            //cellY = (int)Math.floor(touchY/totalCellHeight);

                            //cellX = position / 5;
                            //cellY = (position+1) % 5;

                            //---End of Change 2018 03 21---

                            System.out.println("Previous: " + currentX + "," + currentY + " and New: " + cellX + "," + cellY);

                            //maze.setBeenThereOneCell(cellX, cellY, !beenThere[cellX][cellY]);


                            if (((cellX - currentX == 1 || cellX - currentX == -1) && (cellY == currentY))
                                    || ((cellY - currentY == 1 || cellY - currentY == -1) && (cellX == currentX))){

                                System.out.println("The new cell is a neighbor of the previous one");

                                moved = false;

                                if (cellX-currentX ==  1) moved = maze.move(Maze.RIGHT);
                                if (cellX-currentX == -1) moved = maze.move(Maze.LEFT);
                                if (cellY-currentY ==  1) moved = maze.move(Maze.DOWN);
                                if (cellY-currentY == -1) moved = maze.move(Maze.UP);

                                //If we moved to a different cell, we redraw the view
                                if(moved) {

                                    System.out.println("Yep, it moved");

                                    beenThere[cellX][cellY] = true;
                                    maze.update_path(cellX,  cellY);

                                    currentX = maze.getCurrentX();
                                    currentY = maze.getCurrentY();
                                    beenThere[currentX][currentY] = true;

                                    drawGridView(maze, items);
                                }



                                //If we reached the last cell
                                if(maze.isCurrentCellEndCell()){


                                    //If the game is complete, we count the number of points and show the finish dialog if needed
                                    if(maze.isGameWon()) {
                                        System.out.println("The Game is Finished!!!");
                                        maze.update_nb_points();
                                        gameWon = maze.isGameWon();

                                        timeSwapBuff += timeInMilliseconds;
                                        customHandler.removeCallbacks(updateTimerThread);
                                        drawGridView(maze, items);

                                        if (mazeID == 10){
                                            System.out.println("Finished the 10th game!!!");
                                            drawGridView(maze, items);
                                            updateBestScores((int) updatedTime);
                                        }
                                        else {
                                            //WonSnackbar();
                                            //drawGridView(maze, items);
                                            mazeID++;
                                            prepareGame();
                                        }
                                    }
                                    else {
                                        gameLost = true;
                                        drawGridView(maze, items);
                                        //Penalty for losing :)
                                        timePenalties += 10000;
                                    }


                                }

                            }
                            //Else if the cell pressed/selected is on the path
                            else if (maze.getCellInPath(cellX, cellY)){
                                System.out.println("Have we been there yet (" + cellX + "," + cellY + "): " + maze.getBeenThereOneCell(cellX, cellY));

                                // We rebuild the path
                                System.out.println("If the cell pressed/selected is on the path");
                                maze.update_path(cellX, cellY);

                                // Then we redraw the view
                                beenThere[cellX][cellY] = true;
                                maze.update_path(cellX,  cellY);

                                currentX = maze.getCurrentX();
                                currentY = maze.getCurrentY();
                                beenThere[currentX][currentY] = true;

                                drawGridView(maze, items);

                            }

                            if(cellX == currentX && cellY == currentY) {

                                System.out.println("Dragging...AAA");

                                //touch gesture in the cell where the ball is
                                dragging = true;
                                System.out.println("dragging = true");
                            }

                            break;

                        case MotionEvent.ACTION_UP:

                            System.out.println("case MotionEvent.ACTION_UP");

                            //touch gesture completed
                            dragging = false;
                            //return true;

                            break;

                        case MotionEvent.ACTION_MOVE:

                            gameLost = false;

                            System.out.println("case MotionEvent.ACTION_MOVE");

                            if(dragging) {

                                System.out.println("DRAGGING");

                                //cellX = (int)Math.floor(touchX/totalCellWidth);
                                //cellY = (int)Math.floor(touchY/totalCellHeight);

                                if((cellX != currentX && cellY == currentY)
                                        || (cellY != currentY && cellX == currentX)) {
                                    //either X or Y changed
                                    moved = false;

                                    if (cellX-currentX ==  1) moved = maze.move(Maze.RIGHT);
                                    if (cellX-currentX == -1) moved = maze.move(Maze.LEFT);
                                    if (cellY-currentY ==  1) moved = maze.move(Maze.DOWN);
                                    if (cellY-currentY == -1) moved = maze.move(Maze.UP);

                                    //If we moved to a different cell, we redraw the view
                                    if(moved) {
                                        gameWon = maze.isGameWon();
                                        System.out.println("MOVED");

                                        beenThere[cellX][cellY] = true;
                                        maze.update_path(cellX,  cellY);
                                        drawGridView(maze, items);
                                    }

                                    //If we reached the last cell
                                    if(maze.isCurrentCellEndCell()){ // || maze.isCurrentCellNextToEndCell()){


                                        //If the game is complete, we count the number of points and show the finish dialog if needed
                                        if(maze.isGameWon()) {
                                            System.out.println("The Game is Finished!..");
                                            maze.update_nb_points();
                                            gameWon = maze.isGameWon();

                                            timeSwapBuff += timeInMilliseconds;
                                            customHandler.removeCallbacks(updateTimerThread);
                                            drawGridView(maze, items);

                                            if (mazeID == 10){
                                                System.out.println("Finished the 10th game!!!");
                                                drawGridView(maze, items);
                                                updateBestScores((int) updatedTime);

                                            }
                                            else {

                                                //WonSnackbar();
                                                //drawGridView(maze, items);
                                                mazeID++;
                                                prepareGame();
                                            }
                                            //If we want to go automatically to next game after winning
                                            //mazeID++;
                                            //prepareGame();

                                        }
                                        else {
                                            gameLost = true;
                                            drawGridView(maze, items);
                                            //Penalty for losing :)
                                            timePenalties += 10000;
                                        }


                                    }

                                }
                                return true;


                            }
                            break;

                        default: break;


                    }
                }
                return true;
            }
        });
    }

}
