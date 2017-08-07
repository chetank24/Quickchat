package com.quickchat.controller;

import java.applet.AppletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatController {
	@Autowired
	ServletContext context;
	public static List<String> waitlist = new ArrayList<>();
	public Map<String, String> db = new HashMap<>();
	String jid = null;

	@RequestMapping("/test")
	public String msg(Model model, HttpSession httpSession,HttpServletRequest httpRequest) throws InterruptedException {
		if (jid == null) {
			jid = httpSession.getId();
			return "Test";
		}
		if (!jid.equals(httpSession.getId())) {
			db.put(httpSession.getId(), jid);
			db.put(jid, httpSession.getId());
			jid = null;
		}
		return msgPost(model, httpSession, httpRequest);
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String msgPost(Model model, HttpSession httpSession, HttpServletRequest httpRequest) {
		if (db.get(httpSession.getId()) == null)
			return "Test";

		String myMessages =  (String) context.getAttribute(httpSession.getId());
		if (myMessages == null) {
			myMessages = httpRequest.getParameter("message");
			context.setAttribute(httpSession.getId(), myMessages);
		} else {
			myMessages=httpRequest.getParameter("message");
		}

		String partnerMsgs = (String) context.getAttribute(db.get(httpSession.getId()));

		System.out.println("messages " + myMessages);
		System.out.println("partnerMsgs " + partnerMsgs);
		
		model.addAttribute("me", myMessages);
		model.addAttribute("you",partnerMsgs);
		return "Test";
	}

}
