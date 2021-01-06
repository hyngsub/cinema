package com.chea.cinema.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ContentController {
 
//	
//	@Autowired
//	private ContentService   contentService;
	
	@GetMapping("/cc")
    public String  cc() {

 
		System.out.println("Hello" );
		
		return "Hello" ;

	}
	  @RequestMapping("/")
	  String index() {
	    return "index";
	  }

}
