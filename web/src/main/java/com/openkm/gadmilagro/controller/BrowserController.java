package com.openkm.gadmilagro.controller;

import com.openkm.gadmilagro.bean.PropertiesGroups;
import com.openkm.gadmilagro.bean.PropertiesGroupsGroups;
import com.openkm.gadmilagro.bean.searchForm;
import com.openkm.gadmilagro.service.BrowserService;

import com.openkm.sdk4j.exception.OKMException;
import com.openkm.sdk4j.exception.WebserviceException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/searchInternal"})
public class BrowserController {
	private static final Logger log = LoggerFactory.getLogger(com.openkm.gadmilagro.controller.BrowserController.class);

	private static final String localUrl = "http://localhost:8080/OpenKM";




	@Autowired
	private BrowserService browserService;


	@RequestMapping({"/"})
	public ModelAndView get() {
		log.debug("get()");
		try {
			ModelAndView view = new ModelAndView("/searchInternal/browser");
			view.addObject("appUrl", "http://localhost:8080/OpenKM");
			return view;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ModelAndView view = new ModelAndView("error");
			view.addObject("exception", e);
			return view;
		}
	}

	@PostMapping({"/find"})
	public ModelAndView find(@RequestParam String author, @RequestParam String title, @RequestParam String edit, @RequestParam String year) {

		try {
			ModelAndView view = new ModelAndView("/searchInternal/browser");
			System.out.println("SI ENTROOOOO");
			long test = 0L;
			List<searchForm> documentsInfo = this.browserService.searchDocuments("",author, title, edit, year, test);
			view.addObject("documentsInfo", documentsInfo);
			return view;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ModelAndView view = new ModelAndView("error");
			view.addObject("exception", e);
			return view;
		}
	}

	/*@PostMapping({"/findMetadata"})
	public ModelAndView findMetadata(@RequestParam String okGroups, @RequestParam Map<String, String> okgInputs) {
		log.debug("find({}, {}, {}, {}, {})", okGroups);
		Map<String, String> okpGroupsName = new HashMap<>();
		int cont = 0;
		try {
			ModelAndView view = new ModelAndView("/searchInternal/browser");
			System.out.println("GRUPO DE METADATA" + okGroups);
			System.out.println("GRUPO DE METADATA" + okgInputs);
			for (Map.Entry<String, String> entry : okgInputs.entrySet()) {
				String nombreInput = entry.getKey();
				String valorInput = entry.getValue();
				System.out.println("Nombre del Input: " + nombreInput);
				System.out.println("Valor del Input: " + valorInput);
				cont++;
				if (cont > 2)
					okpGroupsName.put(nombreInput, valorInput);
			}
			List<searchForm> documentsInfo = this.browserService.searchMetadata(okGroups, "", "", "", 0L, okpGroupsName);
			view.addObject("documentsInfo", documentsInfo);
			return view;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ModelAndView view = new ModelAndView("error");
			view.addObject("exception", e);
			return view;
		}
	}*/
	@PostMapping({"/findContent"})
	public ModelAndView findContent( @RequestParam String content) {

		try {
			ModelAndView view = new ModelAndView("searchInternal/content");
			System.out.println("SI ENTROOOOO");

			long test = 0L;
			List<searchForm> documentsInfo = this.browserService.searchDocuments(content,"", "","" , "",test);
			view.addObject("documentsInfo", documentsInfo);
			return view;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ModelAndView view = new ModelAndView("error");
			view.addObject("exception", e);
			return view;
		}
	}


	/*@GetMapping({"/getPropertiesGroupsGroups"})
	@ResponseBody
	public List<PropertiesGroupsGroups> getPropertiesGroupsGroups(@RequestParam String okgroup) throws OKMException, WebserviceException, IOException {
		return this.browserService.getPropertiesGroupsGroups(okgroup);
	}*/

	/*@GetMapping({"/getMonthList"})
	@ResponseBody
	public List<String> getMonthList(@RequestParam String cif, @RequestParam String docType, @RequestParam String year) throws OKMException, WebserviceException, IOException {
		DocTypeInfo docTypeInfo = new DocTypeInfo(docType);
		return this.browserService.getMonthList(cif, docTypeInfo.getName(), year);
	}*/




}
