package com.example.olivierdemo;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/")
public class OlivierDemoController {
	
	@Autowired
    private OlivierDemoProxy OlivierDemoProxy;	
	
	
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
	
	@GetMapping("myforest")
	public ModelAndView myforest() {
		/*Sort test = new Sort(Sort.Direction.ASC, "id");*/
		Iterable<Forest> forests = OlivierDemoProxy.forestlist();
		return new ModelAndView("myforest", "forests", forests);	 
	}	
	@GetMapping(path = "forestadd")
	public ModelAndView forestadd(Forest forest) {
		return new ModelAndView("forestadd");
	}
	@PostMapping(path = "forestadd")
	public ModelAndView forestadd(@Valid Forest forest, BindingResult result, RedirectAttributes redirect) {		
		OlivierDemoProxy.createforest(forest);
		return new ModelAndView("redirect:myforest");
	}


	@GetMapping(path = "forestview/{id}")
	public ModelAndView viewforest(@PathVariable("id") int id) {
		Forest forest  = OlivierDemoProxy.forestview(id);
		return new ModelAndView("forestview", "forest", forest);
	}
	
	
	@GetMapping(path = "forestmodify/{id}") // Map ONLY GET Requests
	public ModelAndView forestmodify(@PathVariable("id") int id) {
		Forest forest  = OlivierDemoProxy.forestview(id);
		return new ModelAndView("forestmodify", "forest", forest);
	}
	
	
	@PostMapping(path = "forestmodify")
	public ModelAndView forestmodify(@Valid Forest forest, BindingResult result, RedirectAttributes redirect) {
		OlivierDemoProxy.createforest(forest);
		return new ModelAndView("redirect:myforest");
	}
	@GetMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	 
	
	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
}
