package server.main.java.gift;

import java.util.Collection;


import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;


public interface  PhotoSvcApi {
	
		

	public static final String TITLE_PARAMETER = "name";
	
	public final String TEST_URL = "http://localhost:8080";


	public static final String ID_PARAMETER = "id";
	
	
	//public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String PHOTO_SVC_PATH = "/gift";
	public static final String PHOTO_SVC_AND_DATA_PATH = "/giftdata";
	// The path to search videos by title
	public static final String PHOTO_TITLE_SEARCH_PATH = PHOTO_SVC_PATH + "/search/findByNameContaining";
	public static final String IMAGE_DATA_PATH = PHOTO_SVC_AND_DATA_PATH + "/{id}/data";
	
	
}
