/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package com.example.android.potloch;


import com.example.android.potloch.security.EasyHttpClient;
import com.example.android.potloch.security.SecuredRestBuilder;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

public class PhotoSvc {

	public static final String CLIENT_ID = "mobile";

	private static PhotoSvcApi photoSvc_;

	public static synchronized PhotoSvcApi getOrShowLogin(Context ctx) {
		if (photoSvc_ != null) {
			return photoSvc_;
		} else {
			Intent i = new Intent(ctx, MainActivity.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized PhotoSvcApi init(String server, String user,
			String pass) {

		photoSvc_ = new SecuredRestBuilder()
				.setLoginEndpoint(server + PhotoSvcApi.TOKEN_PATH)
				.setUsername(user)
				.setPassword(pass)
				.setClientId(CLIENT_ID)
				.setClient(
						new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(PhotoSvcApi.class);

		return photoSvc_;
	}
}
