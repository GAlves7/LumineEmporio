package com.api.lumine_emporio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TestController {
	
	@GetMapping
	public String get() {
		return "Get deu certo";
	}
	
	@PostMapping
	public String post() {
		return "Post deu certo";
	}
}
