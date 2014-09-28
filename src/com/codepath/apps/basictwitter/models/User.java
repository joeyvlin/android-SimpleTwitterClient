package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="User")
public class User extends Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7735681622380608179L;

	@Column(name="name")
	private String name;
	
	@Column(name="u_id", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	
	@Column(name="screen_name")
	private String screenName;
	
	@Column(name="profile_image_url")
	private String profileImageUrl;

	@Column(name="followers_count")
	private int followerCount;
	
	@Column(name="following_count")
	private int followingCount;
	
	@Column(name="tagline")
	private String tagline;
	
	public static User fromJson(JSONObject jsonObject){
		try{
			User user = findUser(jsonObject.getLong("id"));
			if(user != null){
				Log.d("debug", "User found: " + user.getName() + ": " + user.getUid());
			}else{
				Log.d("debug", " Not found: " + jsonObject.getLong("id"));
			}
			if(user == null){
				Log.d("debug", "User Not found: " + jsonObject.getLong("id"));
				user = new User();
				user.name = jsonObject.getString("name");
				user.uid = jsonObject.getLong("id");
				user.screenName = jsonObject.getString("screen_name");
				user.profileImageUrl = jsonObject.getString("profile_image_url");
				user.followerCount = jsonObject.getInt("followers_count");
				user.followingCount = jsonObject.getInt("friends_count");
				user.tagline = jsonObject.getString("description");
				user.save();
				Log.d("debug", " New User Saved: " + jsonObject.getLong("id"));
			}
			return user;
		}catch(JSONException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static User findUser(Long id){
		return new Select().from(User.class).where("u_id = ?", id).executeSingle();
	}
	
	public String getName() {
		return name;
	}
	
	public long getUid() {
		return uid;
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public int getFollowerCount() {
		return followerCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}
	
	public String getTagline() {
		return tagline;
	}

}
