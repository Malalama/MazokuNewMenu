package com.example.f.testmenu;

/**
 * Created by f on 04/03/2018.
 */


/**
 * Created by florentchampigny on 24/02/15.
 */
public class ListItem {

    private String titleLevel;
    private String rec1;
    private String rec2;
    private String rec3;
    private int gamesPlayed;
    private int gamesWon;
    private int winPerc;
    private int longestWinStreak;

    public ListItem(String titleLevel, String rec1, String rec2, String rec3, int gamesPlayed, int gamesWon, int winPerc, int longestWinStreak) {
        this.titleLevel = titleLevel;
        this.rec1 = rec1;
        this.rec2 = rec2;
        this.rec3 = rec3;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.winPerc = winPerc;
        this.longestWinStreak = longestWinStreak;
    }

    public String getTitleLevel() {return titleLevel;}
    public String getRec1(){return rec1;}
    public String getRec2(){return rec2;}
    public String getRec3(){return rec3;}
    public int getGamesPlayed() {return gamesPlayed;}
    public int getGamesWon() {return gamesWon;}
    public int getWinPerc() {return winPerc;}
    public int getLongestWinStreak() {return longestWinStreak;}

    public void setTitleLevel(String titleLevel) {this.titleLevel = titleLevel;}
    public void setRec1(String rec1) {this.rec1 = rec1;}
    public void setRec2(String rec2) {this.rec2 = rec2;}
    public void setRec3(String rec3) {this.rec3 = rec3;}
    public void setGamesPlayed(int gamesPlayed) {this.gamesPlayed = gamesPlayed;}
    public void setGamesWon(int gamesWon) {this.gamesWon = gamesWon;}
    public void setWinPerc(int winPerc) {this.winPerc = winPerc;}
    public void setLongestWinStreak(int longestWinStreak) {this.longestWinStreak = longestWinStreak;}

}