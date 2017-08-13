package com.quickchat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	
	public static String jid = null;
	public static Map<String, String> mapping = new HashMap<>();
	public static Map<String, String> messages = new HashMap<>();


	
	
	@RequestMapping(value = "/setm")
	public ResponseEntity<String> msgPost(HttpSession httpSession, HttpServletRequest httpRequest) {

		if (mapping.get(httpSession.getId()) == null)
			setup(httpSession, httpRequest);//return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);

		String myMessages = httpRequest.getParameter("message");
		messages.put(httpSession.getId(), myMessages);

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@RequestMapping(value = "/getm")
	public ResponseEntity<String> msgGet(HttpSession httpSession, HttpServletRequest httpRequest) {

		if (mapping.get(httpSession.getId()) == null)
			setup(httpSession, httpRequest);

		String partnerMsgs = (String) messages.get(mapping.get(httpSession.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(partnerMsgs);
	}
	
	
	
	@RequestMapping("/setup")
	public ResponseEntity<String> setup( HttpSession httpSession,HttpServletRequest httpRequest) {
		if (jid == null && !mapping.containsKey(httpSession.getId()) ) {
			jid = httpSession.getId();
			return ResponseEntity.status(HttpStatus.OK).body("Waiting");
		}
		if (jid != null && !jid.equals(httpSession.getId())) {
			mapping.put(httpSession.getId(), jid);
			mapping.put(jid, httpSession.getId());
			jid = null;
		}
		if(jid != null && mapping.get(jid)==null )
			return ResponseEntity.status(HttpStatus.OK).body("Waiting");
		/*if(mapping.get(httpSession.getId())!= null && context.getAttribute(mapping.get(httpSession.getId()))==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		}*/
		return ResponseEntity.status(HttpStatus.OK).body("Connected");
	}
	
	@RequestMapping("/clean")
	public ResponseEntity<String> clean( HttpSession httpSession,HttpServletRequest httpRequest) {
		mapping = new HashMap<>();
		return ResponseEntity.status(HttpStatus.OK).body("Cleaned");
	}
	
}
