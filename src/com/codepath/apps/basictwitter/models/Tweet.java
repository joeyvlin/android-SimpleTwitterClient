package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Tweets")
public class Tweet extends Model implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7533687468598857891L;

	@Column(name="body")
	private String body;
	
	@Column(name="u_id", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="user")
	private User user;
	
	@Column(name="screenName")
	private String screenName;
	
	public Tweet() {
		super();
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		try {
			Tweet tweet = findTweet(jsonObject.getLong("id"));
			if(tweet == null) {
				// Extra values from the json to populate the member variables
				tweet = new Tweet();
				tweet.body = jsonObject.getString("text");
				tweet.uid = jsonObject.getLong("id");
				tweet.createdAt = jsonObject.getString("created_at");
				tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
				tweet.screenName = jsonObject.getString("screen_name");
				tweet.save();
			}
			return tweet;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
}
	
	public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
	      // Process each result in json array, decode and convert to business object
	      for (int i=0; i < jsonArray.length(); i++) {
	          JSONObject tweetJson = null;
	          try {
	          	tweetJson = jsonArray.getJSONObject(i);
	          } catch (Exception e) {
	              e.printStackTrace();
	              continue;
	          }

	          Tweet tweet = Tweet.fromJson(tweetJson);
	          if (tweet != null) {
	          	tweets.add(tweet);
	          }
	      }

	      return tweets;
	}
	
	public static Tweet findTweet(Long id){
		return new Select().from(Tweet.class).where("u_id = ?", id).executeSingle();
	}
	
	public static ArrayList<Tweet> getInitialTweetList(){
		List<Tweet> tempList = new Select()
		.from(Tweet.class)
		.orderBy("Tweets.u_id DESC")
		.execute();
		return (ArrayList<Tweet>)tempList;
		}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	
	public String getScreenName() {
		return screenName;
	}

	@Override
	public String toString() {
		return getBody() + " - " + getUser().getScreenName();
	}
	
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);

		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return relativeDate;
	}

}
