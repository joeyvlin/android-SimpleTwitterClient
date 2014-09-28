package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.CurrentUser;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.R.id;
import com.codepath.apps.basictwitter.R.layout;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

EditText etNewTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		User currentUser = CurrentUser.getCurrentUser();
		ImageView ivProfileImg = (ImageView) findViewById(R.id.ivProfileImg);
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvSN = (TextView) findViewById(R.id.tvSN);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(currentUser.getProfileImageUrl(), ivProfileImg);
		tvName.setText(currentUser.getName());
		tvSN.setText("@" + currentUser.getScreenName());
	}
	
	public void onSubmitTweet(View v){
		etNewTweet = (EditText) findViewById(R.id.newTweetId);
		String newTweet = etNewTweet.getText().toString();
		TwitterApplication.getRestClient().postTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonTweet) {
            	Tweet tweet = Tweet.fromJson(jsonTweet);
        		Intent i = new Intent();
        		i.putExtra("tweet", tweet);
        		setResult(RESULT_OK, i);
        		finish();
            }
        }, newTweet);
	}
	
	
}
