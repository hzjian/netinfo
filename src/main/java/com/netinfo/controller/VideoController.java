package com.netinfo.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.netinfo.entity.Result;
import com.netinfo.entity.SysRelateFileEntity;
import com.netinfo.entity.SysRoleEntity;
import com.netinfo.service.SysRelateFileService;
import com.netinfo.utils.ResultUtil;

@RestController
@RequestMapping("/api/video")
public class VideoController {

	private String videoLocation = "d:/videos";
	
	@Autowired
	private SysRelateFileService sysRelateFileService;

	private ConcurrentHashMap<String, File> videos = new ConcurrentHashMap<String, File>();

	@PostConstruct
	public void init() {
		File dir = new File(videoLocation);
		videos.clear();
		videos.putAll(Arrays.asList(dir.listFiles()).stream()
				.collect(Collectors.toMap((f) -> {
					String name = ((File) f).getName();
					
					return name;
				}, (f) -> (File) f)));
	}

	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{video:.+}")
	public StreamingResponseBody stream(@PathVariable String video)
			throws FileNotFoundException {
		String trueName = this.sysRelateFileService.getFileNameById(video);
		
		File videoFile = videos.get(video);

		final InputStream videoFileStream = new FileInputStream(videoFile);

		return (os) -> {
			readAndWrite(videoFileStream, os);
		};
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/{layerId}/{gid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void upload(@RequestParam("file") MultipartFile multifile,
			@PathVariable("layerId") String layerId,@PathVariable("gid") String gid) throws IOException {

		SysRelateFileEntity entity =new SysRelateFileEntity();
		entity.setFname(multifile.getOriginalFilename());
		entity.setLayerid(layerId);
		entity.setRelateid(gid);
		entity.setFtype(multifile.getContentType());
		SysRelateFileEntity saveresult =this.sysRelateFileService.saveRelateFileEntity(entity);
		
		OutputStream os = new FileOutputStream(new File(videoLocation, saveresult.getFileid()));
		readAndWrite(multifile.getInputStream(), os);
		init();
		
		
		
//		for(Map.Entry<String, File> entry: videos.entrySet()) {  
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
//        }  
		
	}
	/*
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void upload1(@RequestParam("file") MultipartFile multifile,@RequestParam("gid") String gid,@RequestParam("layerId") String layerId) throws IOException {

		
		OutputStream os = new FileOutputStream(new File(videoLocation, multifile.getOriginalFilename()));
		readAndWrite(multifile.getInputStream(), os);
		init();
		
		for(Map.Entry<String, File> entry: videos.entrySet()) {  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
       }  
		
	}*/
	@GetMapping(value = "/filelist")
	public Result<Page<SysRoleEntity>> roleList(HttpServletRequest request) {

		String gid = request.getParameter("gid");
		String layerId = request.getParameter("layerId");

		List<SysRelateFileEntity> mList = this.sysRelateFileService.findByRelateid(gid);

		return ResultUtil.success(mList);
	}

	
	@GetMapping(value = "/deletefile")
	public Result<Page<SysRoleEntity>> deletefile(HttpServletRequest request) {

		try {
			String fileid = request.getParameter("fileid");
			File videoFile = videos.get(fileid);
			videoFile.delete();
			videos.remove(fileid);
			this.sysRelateFileService.deleteFile(fileid);
		} catch (Exception e) {

			return ResultUtil.success("FAILURE");
		}
		return ResultUtil.success("SUCCESS");
	}

	@RequestMapping(method = RequestMethod.GET)
	public Set<String> list() {
		return videos.keySet();
	}
	
	private void readAndWrite(final InputStream is, OutputStream os)
			throws IOException {
		byte[] data = new byte[2048];
		int read = 0;
		while ((read = is.read(data)) > 0) {
			os.write(data, 0, read);
		}
		os.flush();
	}

}