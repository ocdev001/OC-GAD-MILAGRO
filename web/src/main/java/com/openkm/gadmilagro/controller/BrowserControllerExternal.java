package com.openkm.gadmilagro.controller;

import com.openkm.gadmilagro.bean.PropertiesGroupsGroups;
import com.openkm.gadmilagro.bean.searchForm;
import com.openkm.gadmilagro.service.BrowserService;
import com.openkm.sdk4j.exception.OKMException;
import com.openkm.sdk4j.exception.WebserviceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/searchExternal"})
public class BrowserControllerExternal {
	private static final Logger log = LoggerFactory.getLogger(BrowserControllerExternal.class);

	private static final String localUrl = "http://localhost:8080/OpenKM";




	@Autowired
	private BrowserService browserService;


	@RequestMapping({"/"})
	public ModelAndView get(@RequestParam String selectExternal) {
		log.debug("get()");
		try {
			ModelAndView view = new ModelAndView("/searchExternal/externalContent");
			view.addObject("selectOptions",this.browserService.getSelectConfig());
			view.addObject("appUrl", "http://localhost:8080/OpenKM");
			view.addObject("urlView", selectExternal);
			return view;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ModelAndView view = new ModelAndView("error");
			view.addObject("exception", e);
			return view;
		}
	}
}
