package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.basictwitter.models.CurrentUser;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
//		Intent i = new Intent(this, TimelineActivity.class);
//		startActivity(i);
		TwitterClient client = TwitterApplication.getRestClient();
		client.getVerifiedCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				CurrentUser.setCurrentUser(User.fromJson(json));
				Intent i = new Intent(LoginActivity.this, TimelineActivity.class);
				startActivity(i);
			}
		});
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
