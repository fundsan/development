/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class MyController  {

	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
	
	public VideoFileManager mVideoManager;
	public static final String DATA_PARAMETER = "data";

	public static final String ID_PARAMETER = "id";

	public static final String VIDEO_SVC_PATH = "/video";
	
	public static final String VIDEO_DATA_PATH = VIDEO_SVC_PATH + "/{id}/data";
	// The VIDEO_SVC_PATH is set to "/video" in the VideoSvcApi
		// interface. We use this constant to ensure that the 
		// client and service paths for the VideoSvc are always
		// in synch.
		//
		// For some ways to improve the validation of the data
		// in the Video object, please see this Spring guide:
		// http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/validation.html#validation-beanvalidation
		//
	
		
		// Receives GET requests to /video and returns the current
		// list of videos in memory. Spring automatically converts
		// the list of videos to JSON because of the @ResponseBody
		// annotation.
	   private static final AtomicLong currentId = new AtomicLong(0L);
		
		private Map<Long,Video> videos = new HashMap<Long, Video>();
		
		@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.POST)
		public @ResponseBody Video addVideo(@RequestBody Video v)  {
			// TODO Auto-generated method stub
			
			this.save(v);
			v.setDataUrl(this.getDataUrl(v.getId()));
			   
			   
			return v;
			
		}

		@RequestMapping(value= VIDEO_DATA_PATH, method=RequestMethod.POST)
		public  @ResponseBody VideoStatus setVideoData( @PathVariable(value = ID_PARAMETER)Long id, @RequestParam("data") MultipartFile videoData) {
			
		 
				try {
					VideoFileManager.get().saveVideoData(videos.get(id), videoData.getInputStream());
				} catch (NullPointerException | IOException ne) {
					// TODO Auto-generated catch block
					throw new ResourceNotFoundException("ID non existant!, " + id);
				}
			
			
			return  new VideoStatus(VideoState.READY);
		}

		@RequestMapping(value= VIDEO_DATA_PATH, method=RequestMethod.GET)
		
		public @ResponseBody void getData( @PathVariable(value = ID_PARAMETER) Long id,
                HttpServletResponse response)  {
			    
			
			
					try {
						VideoFileManager.get().copyVideoData(videos.get(id), response.getOutputStream());
					} catch (NullPointerException | IOException ne) {
						// TODO Auto-generated catch block
						throw new ResourceNotFoundException("ID non existant!, " + id);

					}
				
			
			
		}

		@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.GET)
		public @ResponseBody Collection<Video> getVideoList() {
			// TODO Auto-generated method stub
			return this.videos.values();
		}
        private String getDataUrl(Long videoId){
            String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
            return url;
        }
     	private String getUrlBaseForLocalServer() {
		   HttpServletRequest request = 
		       ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		   String base = 
		      "http://"+request.getServerName() 
		      + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
		   return base;
		}
     	
     	public Video save(Video entity) {
    		checkAndSetId(entity);
    		videos.put((Long)entity.getId(), entity);
    		return entity;
    	}

    	private void checkAndSetId(Video entity) {
    		if(entity.getId() <= 0){
    			entity.setId((Long)currentId.incrementAndGet());
    		}
    	}
}
