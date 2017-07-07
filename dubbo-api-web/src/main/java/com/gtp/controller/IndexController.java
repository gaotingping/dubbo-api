package com.gtp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@RequestMapping(value = "", 
			method = { RequestMethod.GET, RequestMethod.POST }, 
			produces = {"text/html; charset=UTF-8;charset=UTF-8" })
	@ResponseBody
	public String index() {
		return "OK";
	}
}