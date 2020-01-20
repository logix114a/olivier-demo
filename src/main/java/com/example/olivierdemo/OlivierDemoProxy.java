package com.example.olivierdemo;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "forest", url = "localhost:9104")
public interface OlivierDemoProxy {
	@GetMapping(value = "/forestlist")
	List<Forest> forestlist();
	
	 @PostMapping("/forestadd")
	    Forest createforest(@RequestBody Forest forest);	 
	 
	 @GetMapping(value = "/forestview/{id}")
		Forest forestview(@PathVariable("id") int id);
		
	 
}
