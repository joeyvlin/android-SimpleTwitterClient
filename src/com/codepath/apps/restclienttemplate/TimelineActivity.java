package com.codepath.apps.restclienttemplate;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	
	private Long sinceId;
	private Long maxId;
	
	private final int COMPOSE_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		lvTweets.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				fetchTimelineAsync();
			}
		});
		tweets = new ArrayList<Tweet>();
//		aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline(null, maxId);
			}
			
		});
		sinceId = 1L;
		ArrayList<Tweet> tweetList = Tweet.getInitialTweetList();
		if(tweetList != null && tweetList.size() > 0){
			aTweets.addAll(tweetList);
		}
		
		if(aTweets.getCount() > 0){
			Integer totalCount = aTweets.getCount();
			sinceId = aTweets.getItem(0).getUid();
			maxId = aTweets.getItem(totalCount-1).getUid();
		}
		populateTimeline(sinceId, null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.compose_tweet) {
			Intent intent = new Intent(this, ComposeActivity.class);
			startActivityForResult(intent, COMPOSE_CODE);
		}
		return true;
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (COMPOSE_CODE == requestCode && data != null) {
			Tweet newTweet = (Tweet) data.getSerializableExtra("tweet");
			aTweets.insert(newTweet, 0);
		}
	}
	
	private void populateTimeline(Long since_id, Long max_id) {
		Log.d("DEBUG", "calling populateTimeline");
		client.getHometimeline(since_id, max_id, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("DEBUG", json.toString());
				aTweets.addAll(Tweet.fromJsonArray(json));
				int count = aTweets.getCount();
				sinceId = aTweets.getItem(0).getUid();
				maxId = aTweets.getItem(count - 1).getUid();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", "failure populatetimeline");
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	private void fetchTimelineAsync() {
		if (checkNetworkConnection()) {
			client.getHometimeline(sinceId, maxId, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray json) {
					aTweets.addAll(Tweet.fromJsonArray(json));
//					ArrayList<Tweet> newTweets = Tweet.fromJsonArray(json);
//					for(int i=newTweets.size()-1; i>=0; i--){
//						aTweets.insert(newTweets.get(i), 0);
//					}
					lvTweets.onRefreshComplete();
				}
				
				@Override
				public void onFailure(Throwable e) {
					Log.d("DEBUG", "Fetch timeline error: " + e.toString());
				}
			});
		}
	}
	
	 public Boolean isNetworkAvailable() {
		 ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		 return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	 }
	 
	 public boolean checkNetworkConnection(){
		 if(isNetworkAvailable()){
			 return true;
		 }else{
			 Toast.makeText(this, "Network connection is unavability", Toast.LENGTH_LONG).show();
			 return false;
		 }
	 }
}
