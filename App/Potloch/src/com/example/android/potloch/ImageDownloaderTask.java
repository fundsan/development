package com.example.android.potloch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloaderTask extends AsyncTask<ImageView, Void, Bitmap> {

ImageView imageView = null;

@Override
protected Bitmap doInBackground(ImageView... imageViews) {
    this.imageView = imageViews[0];
    return download_Image((String)imageView.getTag());
}

@Override
protected void onPostExecute(Bitmap result) {
    imageView.setImageBitmap(result);
}


private Bitmap download_Image(String url) {
	
	
	
	
	
	
	return null;
}
}