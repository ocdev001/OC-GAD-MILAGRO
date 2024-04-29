/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.gadmilagro.controller;

import com.openkm.gadmilagro.config.Config;
import com.openkm.gadmilagro.service.BrowserService;
import com.openkm.sdk4j.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;

/**
 * Created by pavila on 17/06/16.
 */
@Controller
public class DefaultController {



	@Autowired
	private Config config;

	@Autowired
	private BrowserService browserService;


	@ModelAttribute("urlLogoLogin")
	public String setUrlLogoLogin() {
		return config.APP_LOGO_LOGIN;
	}

	@ModelAttribute("urlLogo")
	public String setUrlLogo() {
		return config.APP_LOGO;
	}

	@GetMapping("/")
	public ModelAndView home() throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException,
			UnknownException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException,
			AutomationException, AuthenticationException, WebserviceException, IOException, PluginNotFoundException, ValidationFormException {
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();*/
		ModelAndView view = new ModelAndView("/index");
		view.addObject("selectOptions",this.browserService.getSelectConfig());
		// Refresh cache
		/*browserSingleton.hasUploadedDocuments(true);

		if (role.contains(InvoiceConstants.ROLE_STANDARD)) {
			return "redirect:/browser/";
		} else if (role.contains(InvoiceConstants.ROLE_ADVANCED)) {
			return "redirect:/browser/";
		} else if (role.contains(InvoiceConstants.ROLE_ADMIN)) {
			return "redirect:/admin";*/
		//} else {

			return view;
		//}
	}

	@RequestMapping("/header")
	public String header() {
		return "include/header";
	}

	@RequestMapping("/menu")
	public String menu() {

		return "include/menu";
	}

	@RequestMapping("/footer")
	public String footer(Model model) {
		//model.addAttribute("git", gitProps);
		return "include/footer";
	}


}
