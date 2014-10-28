package com.example.android.potloch;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

class MyErrorHandler implements ErrorHandler {
	  @Override public Throwable handleError(RetrofitError cause) {
	    Response r = cause.getResponse();
	    if (r != null && r.getStatus() == 401) {
	      return new Throwable(cause);
	    }
	    return cause;
	  }
	}
