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


		// if (mapping.get(httpSession.getId()) == null)
		// setup(httpSession, httpRequest);

		String myMessages = httpRequest.getParameter("message");
		messages.put(httpSession.getId(), myMessages);

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@RequestMapping(value = "/getm")
	public ResponseEntity<String> msgGet(HttpSession httpSession, HttpServletRequest httpRequest) {

		// if (mapping.get(httpSession.getId()) == null)
		// setup(httpSession, httpRequest);
		
		/*System.out.println("my ID ="+httpSession.getId() );
		System.out.println("Partner ID ="+mapping.get(httpSession.getId()) );
		System.out.println("My msg ="+messages.get(httpSession.getId()) );
		System.out.println("Partner msg ="+messages.get(mapping.get(httpSession.getId())));*/
		String partnerMsgs = (String) messages.get(mapping.get(httpSession.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(partnerMsgs);
	}

	@RequestMapping("/setup")
	public synchronized ResponseEntity<String> setup(HttpSession httpSession, HttpServletRequest httpRequest) {
		//System.out.println(httpSession.getId() + "  in setup");
		
		if (jid == null && !mapping.containsKey(httpSession.getId())) {
			jid = httpSession.getId();
			//System.out.println(httpSession.getId() + "  is waiting");
			return ResponseEntity.status(HttpStatus.OK).body("Waiting");
		}
		if (jid != null && !jid.equals(httpSession.getId())) {
			mapping.put(httpSession.getId(), jid);
			mapping.put(jid, httpSession.getId());
			jid = null;
		}
		if (jid != null && mapping.get(jid) == null) {
			//System.out.println(httpSession.getId() + "  is waiting");
			return ResponseEntity.status(HttpStatus.OK).body("Waiting");
		}
		
		

		return ResponseEntity.status(HttpStatus.OK).body("Connected");
	}
	
	@RequestMapping("/verifyConnection")
	public synchronized ResponseEntity<String> verifyConnection(HttpSession httpSession, HttpServletRequest httpRequest) {
		//System.out.println(httpSession.getId() + "  in verifyConnection");
				if(httpSession.getId().equals(mapping.get(mapping.get(httpSession.getId()))))
		{
			return ResponseEntity.status(HttpStatus.OK).body("Connected");
		}
		if(httpSession.getId().equals(jid))
		{
			return ResponseEntity.status(HttpStatus.OK).body("Waiting");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Disconnected");
	}

	@RequestMapping("/reset")
	public ResponseEntity<String> clean(HttpSession httpSession, HttpServletRequest httpRequest) {
		mapping = new HashMap<>();
		messages = new HashMap<>();
		return ResponseEntity.status(HttpStatus.OK).body("Cleaned");
	}

	@RequestMapping("/cleanUp")
	public synchronized ResponseEntity<String> cleanUp(HttpSession httpSession, HttpServletRequest httpRequest) {

		if (messages.get(mapping.get(httpSession.getId())) != null)
			messages.remove(mapping.get(httpSession.getId()));

		if (messages.get(httpSession.getId()) != null)
			messages.remove(httpSession.getId());

		if (mapping.get(mapping.get(httpSession.getId())) != null)
			mapping.remove(mapping.get(httpSession.getId()));

		if (mapping.get(httpSession.getId()) != null)
			mapping.remove(httpSession.getId());

		return ResponseEntity.status(HttpStatus.OK).body("Cleaned");
	}

}
