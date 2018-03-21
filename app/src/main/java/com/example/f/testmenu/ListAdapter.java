package com.example.f.testmenu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by f on 04/03/2018.
 */

public class ListAdapter extends ArrayAdapter<ListItem> {

    public ListAdapter(Context context, List<ListItem> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listviewitem,parent, false);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolder();

            viewHolder.titleLevel = (TextView) convertView.findViewById(R.id.titleLevel);
            viewHolder.rec1 = (TextView) convertView.findViewById(R.id.rec1);
            viewHolder.rec2 = (TextView) convertView.findViewById(R.id.rec2);
            viewHolder.rec3 = (TextView) convertView.findViewById(R.id.rec3);
            viewHolder.gamesPlayed = (TextView) convertView.findViewById(R.id.gamesPlayed);
            viewHolder.gamesWon = (TextView) convertView.findViewById(R.id.gamesWon);
            viewHolder.winPerc = (TextView) convertView.findViewById(R.id.winPerc);
            viewHolder.longestWinStreak = (TextView) convertView.findViewById(R.id.longestWinStreak);


//            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
//            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
//            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ListItem scores = getItem(position);

        viewHolder.titleLevel.setText(String.valueOf(scores.getTitleLevel()));
        viewHolder.rec1.setText("  " + String.valueOf(scores.getRec1()));
        viewHolder.rec2.setText("  " + String.valueOf(scores.getRec2()));
        viewHolder.rec3.setText("  " + String.valueOf(scores.getRec3()));
        viewHolder.gamesPlayed.setText("Games Played: " + String.valueOf(scores.getGamesPlayed()));
        viewHolder.gamesWon.setText("Games Won: " + String.valueOf(scores.getGamesWon()));
        viewHolder.winPerc.setText("Win Percentage: " + String.valueOf(scores.getWinPerc()));
        viewHolder.longestWinStreak.setText("Longest Win Streak: " + String.valueOf(scores.getLongestWinStreak()));

        //viewHolder.avatar.setImageDrawable(new ColorDrawable(tweet.getColor()));

        return convertView;
    }

    private class ViewHolder {
        public TextView titleLevel;
        public TextView rec1;
        public TextView rec2;
        public TextView rec3;
        public TextView gamesPlayed;
        public TextView gamesWon;
        public TextView winPerc;
        public TextView longestWinStreak;

        //public ImageView avatar;

    }
}
