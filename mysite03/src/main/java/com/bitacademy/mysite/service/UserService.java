package com.bitacademy.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
}
