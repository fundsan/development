package server.main.java.gift;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;


@Entity
public class Photo {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String text; 
	
	
	private long parentID; 
	private long likes;
	private String user;
	private String date;
	@ElementCollection
	private Set<String> likedBy;
	@ElementCollection
	private Set<Long> childrenID;
	
	
	public Set<Long> getChildrenID() {
		return childrenID;
	}

	public void setChildrenID(Set<Long> childrenPhotos) {
		this.childrenID = childrenPhotos;
	}

	public long getParentID() {
		return parentID;
	}

	public void setParentID(long parentPhoto) {
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
		this.id = id;
		this.parentID = parentID;
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
