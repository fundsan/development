package server.main.java.gift;

import java.util.Collection;


import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;


public interface  PhotoSvcApi {
	
		
		public static final String TITLE_PARAMETER = "title";
		
		public final String TEST_URL = "http://localhost:8080";

		// The path where we expect the VideoSvc to live
		public static final String PHOTO_SVC_PATH = "/video";

		// The path to search videos by title
		public static final String PHOTO_TITLE_SEARCH_PATH = PHOTO_SVC_PATH + "/search/findByName";
		
		
		@GET(PHOTO_SVC_PATH)
		public Collection<Photo> getPhotoList();
		
		@POST(PHOTO_SVC_PATH)
		public Void addPhoto(@Body Photo p);
		
		@GET(PHOTO_TITLE_SEARCH_PATH)
		public Collection<Photo> findByTitle(@Query(TITLE_PARAMETER) String title);
		
	

}
