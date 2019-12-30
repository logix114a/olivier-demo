package com.example.olivierdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class OlivierDemoController {
	@GetMapping("")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	@GetMapping("home")
	public ModelAndView home1() {
		return new ModelAndView("home");
	}	
	@GetMapping("home-top")
	public ModelAndView hometop() {
		return new ModelAndView("home-top");
	}
	
	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
}
