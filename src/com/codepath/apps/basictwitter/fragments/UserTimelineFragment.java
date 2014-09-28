package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateTimeline(sinceId, maxId);
	}
	
	@Override
	protected void populateTimeline(Long since_id, Long max_id) {
		Log.d("debug", "UserTimelineFragment:populateTimeline");
		ProfileActivity activity = (ProfileActivity) getActivity();
		User u = activity.getUser();
		TwitterApplication.getRestClient().getUserTimeline(since_id, max_id, u.getUid(), new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray json) {
				Log.d("debug", "getUserTimeline: " + json.toString());
				addAll(Tweet.fromJsonArray(json));
				int count = getAdapter().getCount();
				sinceId = getAdapter().getItem(0).getUid();
				maxId = getAdapter().getItem(count - 1).getUid();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
}
