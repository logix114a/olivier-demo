package com.example.olivierdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
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
	
	@GetMapping("registration")
	public ModelAndView registration() {
		return new ModelAndView("registration");
	}
	
	@GetMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@GetMapping("Produit")
	public ModelAndView produit() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject ("http://localhost:7071/Produits/101",Product.class);
		ModelAndView test1 = new ModelAndView("product");
		test1.addObject("product", product);
		
		return test1;
		
	}
	
	 
	
	@GetMapping(path = "forestview/{id}")
	public ModelAndView viewforest(@PathVariable("id") int id) {
		RestTemplate restTemplate = new RestTemplate();
		Forest forest = restTemplate.getForObject ("http://localhost:7071/Forest/"+ id,Forest.class);
		
		return new ModelAndView("forestview", "forest", forest);
	}
	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
}
