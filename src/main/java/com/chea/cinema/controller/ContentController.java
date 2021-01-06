package com.chea.cinema.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ContentController {

	@Autowired
	private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

	@GetMapping("/mp4")
	public String mp4(@RequestParam String id, HttpServletResponse response) {

		System.out.println("Hello");

		return "Hello" + id;

	}

	@RequestMapping("/hello")
	public String index() {
		return "hello";
	}

//	  @RequestMapping("/mp4/{name}")
//	    public String mp4(@PathVariable("name") String name,@RequestParam String sign,HttpServletResponse response) {
//		  
//		  
//		  
//		  return "Hello"+name+"-sign"+ sign; 
//	  }
	@RequestMapping("/mp4/{name}.mp4")
	public void hello(@PathVariable("name") String name, @RequestParam String sign, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MinioClient minioClient = new MinioClient("http://39.152.129.170:9000", "admin", "j5811227");

		InputStream is = null;
		try {

			is = minioClient.getObject("movie", "1.mp4");

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment; filename=" + name);

			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (java.nio.file.NoSuchFileException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}
	
}
