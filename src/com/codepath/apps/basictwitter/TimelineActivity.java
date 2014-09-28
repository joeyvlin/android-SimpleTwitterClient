package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

//import eu.erikw.PullToRefreshListView;
//import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends FragmentActivity {
	private final int COMPOSE_CODE = 100;
//	private Long sinceId;
//	private Long maxId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);	
		setupTabs();
//		sinceId = 1L;
//		ArrayList<Tweet> tweetList = Tweet.getInitialTweetList();
//		if(tweetList != null && tweetList.size() > 0){
//			fragmentTweetsList.addAll(tweetList);
//		}
//		
//		if(aTweets.getCount() > 0){
//			Integer totalCount = aTweets.getCount();
//			sinceId = aTweets.getItem(0).getUid();
//			maxId = aTweets.getItem(totalCount-1).getUid();
//		}
//		populateTimeline(sinceId, null);
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
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
	
//	private void populateTimeline(Long since_id, Long max_id) {
//		Log.d("DEBUG", "calling populateTimeline");
//		client.getHometimeline(since_id, max_id, new JsonHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(JSONArray json) {
//				Log.d("DEBUG", json.toString());
//				aTweets.addAll(Tweet.fromJsonArray(json));
//				int count = aTweets.getCount();
//				sinceId = aTweets.getItem(0).getUid();
//				maxId = aTweets.getItem(count - 1).getUid();
//			}
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", "failure populatetimeline");
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		});
//	}
//	
//	private void fetchTimelineAsync() {
//		if (checkNetworkConnection()) {
//			client.getHometimeline(sinceId, maxId, new JsonHttpResponseHandler() {
//				@Override
//				public void onSuccess(JSONArray json) {
//					aTweets.addAll(Tweet.fromJsonArray(json));
//					lvTweets.onRefreshComplete();
//				}
//				
//				@Override
//				public void onFailure(Throwable e) {
//					Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//				}
//			});
//		}
//	}
	
	
//	 public Boolean isNetworkAvailable() {
//		 ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		 NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//		 return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
//	 }
//	 
//	 public boolean checkNetworkConnection(){
//		 if(isNetworkAvailable()){
//			 return true;
//		 }else{
//			 Toast.makeText(this, "Network connection is unavability", Toast.LENGTH_LONG).show();
//			 return false;
//		 }
//	 }
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (COMPOSE_CODE == requestCode && data != null) {
//			Tweet newTweet = (Tweet) data.getSerializableExtra("tweet");
//			aTweets.insert(newTweet, 0);
//		}
//	}
}
