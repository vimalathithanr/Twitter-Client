package com.codepath.tweets.tweets;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.tweets.tweets.models.Tweet;
import com.codepath.tweets.tweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private ImageView ivCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(v);

        ivCompose = (ImageView) findViewById(R.id.ivCompose);
        ivCompose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent in = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
                startActivityForResult(in, 1);
            }
        });

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                client.addPage(page);
                client = TwitterApplication.getRestClient();
                populateTimeline();
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        client = TwitterApplication.getRestClient();
        populateTimeline();

    }


    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Entering2", "Hello2");
        String newTweet = data.getStringExtra("tweet");
        Tweet newTweets = new Tweet();
        User newUser = new User();
        newTweets.setComposeTweet(newTweet);
        newTweets.setBody(newTweet);
        newTweets.setCreatedAt(data.getStringExtra("createdAt"));

        newUser.setName(data.getStringExtra("name"));
        newUser.setScreenName(data.getStringExtra("screenName"));
        newUser.setProfileImageUrl(data.getStringExtra("profileImage"));
        newTweets.setUser(newUser);

        tweets.add(0, newTweets);

        aTweets.notifyDataSetChanged();
        lvTweets.setSelectionAfterHeaderView();
    }

}
