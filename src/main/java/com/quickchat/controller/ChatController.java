package com.quickchat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	@Autowired
	ServletContext context;
	public static List<String> waitlist = new ArrayList<>();
	public Map<String, String> db = new HashMap<>();
	String jid = null;

	@RequestMapping("/test")
	public ResponseEntity<String> msg( HttpSession httpSession,HttpServletRequest httpRequest) throws InterruptedException {
		if (jid == null && !db.containsKey(httpSession.getId()) ) {
			jid = httpSession.getId();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		}
		if (jid != null && !jid.equals(httpSession.getId())) {
			db.put(httpSession.getId(), jid);
			db.put(jid, httpSession.getId());
			jid = null;
		}
		if(db.get(httpSession.getId())!= null && context.getAttribute(db.get(httpSession.getId()))==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		}


		String partnerMsgs = (String) context.getAttribute(db.get(httpSession.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(partnerMsgs);
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<String> msgPost(HttpSession httpSession, HttpServletRequest httpRequest) {
		if (db.get(httpSession.getId()) == null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);

		String myMessages =  (String) context.getAttribute(httpSession.getId());
		if (myMessages == null) {
			myMessages = httpRequest.getParameter("message");
			context.setAttribute(httpSession.getId(), myMessages);
		} else {
			myMessages=httpRequest.getParameter("message");
		}

		String partnerMsgs = (String) context.getAttribute(db.get(httpSession.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(partnerMsgs);
	}
	
	

}
