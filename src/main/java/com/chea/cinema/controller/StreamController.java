package com.chea.cinema.controller;

import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chea.cinema.entity.FileType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StreamController {

	private FileType ts = new FileType(".ts", "video/mp2t");
	private FileType m3u8 = new FileType(".m3u8", "application/vnd.apple.mpegurl");
	private String targetUrl = "http://39.152.129.170:10085/movie/";
	private String version = "Version:1.0";

	@RequestMapping("/test")
	public String test() {
		return "test";
	}
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	@RequestMapping("/hello1")
	public String hello1() {
		return "hello1";
	}
	@RequestMapping("/version")
	public String version() {
		return version;
	}

	@RequestMapping("/movie/{name}.m3u8")
	public void m3u8(@PathVariable("name") String name, @RequestParam String sign, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		proxy(name, m3u8, response);

	}

	@RequestMapping("/movie/{name}.ts")
	public void ts(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		proxy(name, ts, response);

	}

	public void proxy(String name, FileType fileType, HttpServletResponse response) throws Exception {

		URL url = null;
		InputStream is = null;

		try {
			url = new URL(targetUrl + name + "/1" + fileType.getSuffix());
			is = url.openStream();

			response.setContentType(fileType.getContentType());
			response.setHeader("Content-Disposition", "attachment; filename=" + name + fileType.getSuffix());

			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (java.nio.file.NoSuchFileException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}
}
