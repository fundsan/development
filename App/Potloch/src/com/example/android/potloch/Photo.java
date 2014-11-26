package com.example.android.potloch;

import java.util.HashSet;
import java.util.Set;


import android.graphics.Bitmap;

import com.google.common.base.Objects;




public class Photo {
	
	private long id;

	private String name;

    private String text;
    private String user;
    
	
	

	private long likes;
	
	private Set<String> likedBy;
	
	private Set<Long> childrenID;
	private long parentID;

	private String date; 
	public Set<Long> getChildrenPhotos() {
		return childrenID;
	}

	public void setChildrenPhotos(Set<Long> childrenPhotos) {
		this.childrenID = childrenPhotos;
	}

	public long getParentPhoto() {
		return parentID;
	}

	public void setParentPhoto(long parentPhoto) {
		this.parentID = parentPhoto;
	}

	public Photo() {
	}

	public Photo(String name, String text, String user, String date, long id, long parentID) {
		super();
		this.name = name;
		this.text = text;
		
		this.user = user;
		this.likes = 0;
		this.date = date;
		this.likedBy = new HashSet<String>();
		this.childrenID = new HashSet<Long>();
		this.parentID = parentID;
		this.id = id;
		
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	/**
	 * Two Videos are considered equal if they have exactly the same values for
	 * their name, url, and duration.
	 * 
	 */


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(Set<String> likedBy) {
	   this.likedBy = likedBy;
	}
	public void addlikedby(String username) {
		likedBy.add(username);
	}
	
	
	
	public long getLikes() {
		return likes;
	}
	
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public void addOneLike(){
		this.likes++;
	}
	public void subtractOneLike(){
		this.likes--;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
