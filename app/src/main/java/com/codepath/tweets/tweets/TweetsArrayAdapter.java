package com.codepath.tweets.tweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.tweets.tweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.ocpsoft.pretty.time.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by VRAJA03 on 2/19/2016.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTimestamp);

        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());

        tvTime.setText(dateFormat(tweet.getCreatedAt()));
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        return convertView;
    }


    public String dateFormat(String serverDate){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
        Date d = null;
        try {
            d = sdf.parse(serverDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        long time = c.getTimeInMillis();

        String prettyTimeString = new PrettyTime().format(new Date(time));
        String prettyTime = null;

        if (prettyTimeString.contains("minutes")) {
            prettyTime = prettyTimeString.replace(" minutes ago", "m");
        } else if (prettyTimeString.contains("minute")) {
            prettyTime = prettyTimeString.replace(" minute ago", "m");
        } else if (prettyTimeString.contains("hours")) {
            prettyTime = prettyTimeString.replace(" hours ago", "h");
        } else if (prettyTimeString.contains("hour")) {
            prettyTime = prettyTimeString.replace(" hour ago", "h");
        }

        return prettyTime;

    }
}
