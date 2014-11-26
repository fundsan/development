package com.example.android.potloch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	// Util method to make it easier to transform a stream into a byte array
	   static byte[] streamToBytes(InputStream stream) throws IOException {
	       ByteArrayOutputStream baos = new ByteArrayOutputStream();
	       if (stream != null) {
	           byte[] buf = new byte[1024];
	           int r;
	           while ((r = stream.read(buf)) != -1) {
	               baos.write(buf, 0, r);
	           }
	       }
	       return baos.toByteArray();
	   }


	}

