package com.chea.cinema.controller;
 

import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ContentController {


	@RequestMapping("/hello")
	public String index() {
		return "hello";
	}


 
 
	@RequestMapping("/movie/{name}.m3u8")
	public void  m(@PathVariable("name") String name, @RequestParam String sign,HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	    URL url = null;
 
	    InputStream is = null;

		try {
			url = new URL("http://39.152.129.170:10085/2/"+name+".m3u8");
			is = url.openStream();
			 

			response.setContentType("application/vnd.apple.mpegurl");
			response.setHeader("Content-Disposition", "attachment; filename=" + name+".m3u8");

			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (java.nio.file.NoSuchFileException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}
	 
		@RequestMapping("/movie/{name}.ts")
		public void  m(@PathVariable("name") String name,  HttpServletRequest request,
				HttpServletResponse response) throws Exception {

		    URL url = null;
	 
		    InputStream is = null;

			try {
				url = new URL("http://39.152.129.170:10085/2/"+name+".ts");
				is = url.openStream();
				 

				response.setContentType("video/mp2t");
				response.setHeader("Content-Disposition", "attachment; filename=" + name+".ts");

				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			} catch (java.nio.file.NoSuchFileException e) {
				response.setStatus(HttpStatus.NOT_FOUND.value());
			} catch (Exception e) {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}

		}
}
