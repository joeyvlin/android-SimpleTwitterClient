package com.codepath.apps.basictwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	User user;
	long uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		user = (User) getIntent().getSerializableExtra("user");
//		Log.d("debug", "profile user id: " + user.getUid());
		uid = getIntent().getLongExtra("uid", 0);
		Log.d("debug", "profile user id: " + uid);
		user = User.findUser(uid);
		Log.d("debug", "profile user retrieved name: " + user);
		setContentView(R.layout.activity_profile);
		loadProfileInfo(user);
	}

	private void loadProfileInfo(User u) {
//		TwitterApplication.getRestClient().getVerifiedCredentials(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject json) {
//				User u = User.fromJson(json);
//				getActionBar().setTitle("@" + u.getScreenName());
//				populateProfileHeader(u);
//			}
//
//			private void populateProfileHeader(User u) {
//				TextView tvName = (TextView) findViewById(R.id.tvName);
//				TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
//				TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
//				TextView tvFollowing = (TextView) findViewById(R.id.tvFollwing);
//				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
//				
//				tvName.setText(u.getName());
//				tvTagline.setText(u.getTagline());
//				tvFollowers.setText(u.getFollowerCount() + " Followers");
//				tvFollowing.setText(u.getFollowingCount() + " Following");
//				ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
//				
//			}
//		});	
		
		getActionBar().setTitle("@" + u.getScreenName());
		populateProfileHeader(u);

	}
	
	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollwing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFollowers.setText(u.getFollowerCount() + " Followers");
		tvFollowing.setText(u.getFollowingCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public User getUser() {
		return user;
	}
}
