package com.codepath.tweets.tweets;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.tweets.tweets.models.Tweet;

import java.util.List;

/**
 * Created by VRAJA03 on 2/19/2016.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }
}
