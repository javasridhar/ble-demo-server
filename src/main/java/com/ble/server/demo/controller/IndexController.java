package com.ble.server.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String printIndexMessage() {
		return "You are running on ble-demo-server heroku app";
	}
}
