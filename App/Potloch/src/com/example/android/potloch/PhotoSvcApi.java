package com.example.android.potloch;

import java.util.Collection;


import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Streaming;


public interface  PhotoSvcApi {
	
	public static final String ID_PARAMETER = "id";
	
	public static final String TITLE_PARAMETER = "name";
	
	public final String TEST_URL = "http://10.0.2.2:8080";
	
	//public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String PHOTO_SVC_PATH = "/gift";
	public static final String PHOTO_SVC_AND_DATA_PATH = "/giftdata";
	// The path to search videos by title
	public static final String PHOTO_TITLE_SEARCH_PATH = PHOTO_SVC_PATH + "/search/findByNameContaining";
	public static final String IMAGE_DATA_PATH = PHOTO_SVC_AND_DATA_PATH + "/{id}/data";

	public static final String TOKEN_PATH = "/oauth/token";
	
	@GET(PHOTO_SVC_PATH)
	public Collection<Photo> getPhotoList();
	
	@POST(PHOTO_SVC_AND_DATA_PATH)
	public Long addPhotoData(@Body byte[] data);
	
	@POST(PHOTO_SVC_PATH)
	public Void addPhoto(@Body Photo photo);
	
	@GET(PHOTO_TITLE_SEARCH_PATH)
	public Collection<Photo> findByNameContaining(@Query(TITLE_PARAMETER) String name);
	
	@POST(PHOTO_SVC_PATH + "/{id}/like")
	public Void likePhoto(@Path("id") long id);
	
	@POST(PHOTO_SVC_PATH + "/{id}/unlike")
	public Void unlikePhoto(@Path("id") long id);	
	@GET(PHOTO_SVC_PATH + "/{id}/likedby")
	public Collection<String> getUsersWhoLikedVideo(@Path("id") long id);
    @GET(IMAGE_DATA_PATH)
    byte[] getData(@Path(ID_PARAMETER) long id);
	
}
