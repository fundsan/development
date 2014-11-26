package server.main.java.gift;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

import com.google.common.io.Files;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import retrofit.http.Multipart;
import retrofit.mime.TypedFile;
import server.main.java.gift.PhotoRepository;

@Controller
public class TheController {

	@Autowired
	PhotoRepository photos;

	Map<Long, byte[]> imageData = new HashMap<Long, byte[]>();

	public static final String TITLE_PARAMETER = "name";

	public static final String DURATION_PARAMETER = "duration";

	public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String PHOTO_SVC_PATH = "/gift";
	public static final String PHOTO_SVC_AND_DATA_PATH = "/giftdata";
	public static final String PHOTO_DATA_PATH = PHOTO_SVC_PATH + "/{id}/data";

	// The path to search videos by title
	public static final String PHOTO_TITLE_SEARCH_PATH = PHOTO_SVC_PATH
			+ "/search/findByNameIgnoreCaseContaining";

	private static final AtomicLong currentId = new AtomicLong(0L);

	@RequestMapping(value = PHOTO_SVC_AND_DATA_PATH, method = RequestMethod.POST)
	public @ResponseBody
	Long addPhotoData(@RequestBody byte[] fullData) {
		// TODO Auto-generated method stub

		Long ID = save(fullData);

		System.out.println("work");

		return ID;

	}

	@RequestMapping(value = PHOTO_SVC_PATH + "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Photo getPhoto(@PathVariable("id") long id) {
		return photos.findOne(id);
	}

	@RequestMapping(value = PhotoSvcApi.IMAGE_DATA_PATH, method = RequestMethod.GET)
	public @ResponseBody
	byte[] getData(@PathVariable(value = PhotoSvcApi.ID_PARAMETER) Long id,
			HttpServletResponse response) {
		
		return imageData.get(id);
		
		
	}
		

	@RequestMapping(value = PHOTO_SVC_PATH, method = RequestMethod.GET)
	public @ResponseBody
	Iterable<Photo> getPhotoList() {
		// TODO Auto-generated method stub
		return this.photos.findAll();
	}

	@RequestMapping(value = PHOTO_TITLE_SEARCH_PATH, method = RequestMethod.GET)
	public @ResponseBody
	Collection<Photo> findByTitle(@RequestParam(TITLE_PARAMETER) String name) {
		// TODO Auto-generated method stub
		return this.photos.findByNameContaining(name);

	}

	@RequestMapping(value = PHOTO_SVC_PATH, method = RequestMethod.POST)
	public @ResponseBody
	Photo addVideo(@RequestBody Photo p) {
		// TODO Auto-generated method stub
		
		photos.save(p);

		return p;

	}
	@RequestMapping(value=  "/gift/{id}/likedby", method=RequestMethod.GET)
	public @ResponseBody Set<String> likedBy(@PathVariable("id") long id) {
		// TODO Auto-generated method stub
		Photo p = photos.findOne(id);
		return p.getLikedBy();
	}
	@RequestMapping(value=PHOTO_SVC_PATH+ "/{id}/like", method=RequestMethod.POST)
	public  @ResponseBody ResponseEntity<Void> likeVideo(@PathVariable("id") long id,Principal p ) {
		
		if (!photos.exists(id)){
		
				return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
			
		}
		Photo photo = photos.findOne(id);
		String username = p.getName();
		if(photo.getLikedBy().contains(username)){
			
				
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			
		}
		Set<String> theSet= photo.getLikedBy();
		theSet.add(username);
		photo.setLikes(theSet.size());
		photos.save(photo); 
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value=PHOTO_SVC_PATH+ "/{id}/unlike", method=RequestMethod.POST)
	public  @ResponseBody ResponseEntity<Void> unlikeVideo(@PathVariable("id") long id,   Principal p)  {
		
		if (!photos.exists(id)){
			
		
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		Photo photo = photos.findOne(id);
		String username = p.getName();
		if(!photo.getLikedBy().contains(username)){
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		Set<String> theSet= photo.getLikedBy();
		theSet.remove(username);
		photo.setLikes(theSet.size());
		photos.save(photo); 
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	private String getDataUrl(Long videoId) {
		String url = getUrlBaseForLocalServer() + "/gift/" + videoId + "/data";
		return url;
	}

	private String getUrlBaseForLocalServer() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String base = "http://"
				+ request.getServerName()
				+ ((request.getServerPort() != 80) ? ":"
						+ request.getServerPort() : "");
		return base;
	}

	public Long save(byte[] entity) {
		Long ID = checkAndSetId();
		imageData.put(ID, entity);
		return ID;
	}

	private Long checkAndSetId() {

		return (Long) currentId.incrementAndGet();

	}
}
