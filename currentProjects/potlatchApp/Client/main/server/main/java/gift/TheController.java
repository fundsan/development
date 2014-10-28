package server.main.java.gift;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import server.main.java.gift.PhotoRepository;

@Controller
public class TheController {

	
	@Autowired
	PhotoRepository photos;
	

	public static final String TITLE_PARAMETER = "title";
	
	public static final String DURATION_PARAMETER = "duration";

	public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String PHOTO_SVC_PATH = "/photo";

	// The path to search videos by title
	public static final String PHOTO_TITLE_SEARCH_PATH = PHOTO_SVC_PATH + "/search/findByName";
	
	// The path to search videos by title
	public static final String PHOTO_DURATION_SEARCH_PATH = PHOTO_SVC_PATH + "/search/findByDurationLessThan";

	private static final AtomicLong currentId = new AtomicLong(0L);
	
	
	
	@RequestMapping(value=PHOTO_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Photo addPhoto(@RequestBody Photo p)  {
		// TODO Auto-generated method stub
		
		photos.save(p);
		   
		   
		return p;
		
	}

	@RequestMapping(value=PHOTO_SVC_PATH+ "/{id}", method=RequestMethod.GET)
	public @ResponseBody Photo getPhoto(@PathVariable("id") long id){
		return photos.findOne(id);
	}

	@RequestMapping(value=PHOTO_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Iterable<Photo> getPhotoList() {
		// TODO Auto-generated method stub
		return this.photos.findAll();
	}
	@RequestMapping(value= PHOTO_TITLE_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Photo> findByTitle(@RequestParam(TITLE_PARAMETER)  String title) {
		// TODO Auto-generated method stub
		return this.photos.findByName(title);
	}
}
