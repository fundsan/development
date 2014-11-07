package com.example.android.potloch;

import java.util.HashSet;
import java.util.Set;


import com.google.common.base.Objects;




public class Photo {
	
	private long id;

	private String name;
	private String url;

	private long likes;
	
	private Set<String> likedBy;
	
	private Set<Long> childrenID;
	private long parentID; 
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

	public Photo(String name, String url, long parentID) {
		super();
		this.name = name;
		this.url = url;
		this.likes = 0;
		this.likedBy = new HashSet<String>();
		this.childrenID = new HashSet<Long>();
		this.parentID = parentID;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Two Videos will generate the same hashcode if they have exactly the same
	 * values for their name, url, and duration.
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(name, url);
	}

	/**
	 * Two Videos are considered equal if they have exactly the same values for
	 * their name, url, and duration.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Photo) {
			Photo other = (Photo) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(name, other.name)
					&& Objects.equal(url, other.url);
					
		} else {
			return false;
		}
	}

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

}
