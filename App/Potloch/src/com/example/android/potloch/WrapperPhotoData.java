package com.example.android.potloch;

import retrofit.mime.TypedFile;

public class WrapperPhotoData {
private Photo photo;

private TypedFile file;

public WrapperPhotoData(Photo p, TypedFile file){
	this.photo = p;
	this.file = file;
}

public Photo getPhoto() {
	return photo;
}

public void setPhoto(Photo photo) {
	this.photo = photo;
}

public TypedFile getFile() {
	return file;
}

public void setFile(TypedFile file) {
	this.file = file;
}
}