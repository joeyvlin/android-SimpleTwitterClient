package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
	
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
//	private PullToRefreshListView lvTweets;
	private ListView lvTweets;
	Long sinceId;
	Long maxId;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		sinceId = 1L;
		maxId = null;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);

//		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
//		lvTweets.setOnRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
//				fetchTimelineAsync();
//			}
//		});
//		aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline(null, maxId);
			}
			
		});
		
		return v;
	}
	
	protected abstract void populateTimeline(Long sinceId, Long maxId);
	
	// Delegate the adding to the internal adapter
	protected void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	protected ArrayAdapter<Tweet> getAdapter() {
		return aTweets;
	}
}
