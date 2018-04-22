package com.netinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePagelanding {

	@GetMapping(value="/")
	public String landingPage()
	{
		return "index";
	}
	/*
	@GetMapping(value="/map")
	public String landingMap()
	{
		return "map";
	}
	*/
	@GetMapping(value="/login")
	public String landingLogin()
	{
		return "login";
	}
}
