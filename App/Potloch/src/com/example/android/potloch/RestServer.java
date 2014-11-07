package com.example.android.potloch;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class RestServer {
	private static PhotoSvcApi instance = null;
	   protected RestServer() {
	      // Exists only to defeat instantiation.
	   }
	   public static PhotoSvcApi getInstance() {
	      if(instance == null) {
	         instance =  new RestAdapter.Builder()
				.setEndpoint(PhotoSvcApi.TEST_URL).setLogLevel(LogLevel.FULL).build()
				.create(PhotoSvcApi.class);
	      }
	      return instance;
	   }
	}

