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
import com.openkm.gadmilagro.service.CommonService;

import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Note;
import com.openkm.sdk4j.bean.SimpleNodeBase;

import com.openkm.sdk4j.impl.OKMWebservices;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/propertyGroup"})
public class PropertyGroupController {
	private static final Logger log = LoggerFactory.getLogger(com.openkm.gadmilagro.controller.PropertyGroupController.class);





	@Autowired
	private BrowserService browserService;

	@Autowired
	private CommonService cs;

	@Autowired
	private Config cfg;



	@ModelAttribute("PROPERTY_ID_TOTAL")
	public String getConstantPropInvoiceTotal() {
		return "total";
	}

	@ModelAttribute("PROPERTY_ID_CIF")
	public String getConstantPropInvoiceCif() {
		return "cif";
	}

	@ModelAttribute("PROPERTY_TYPE_DATE")
	public String getConstantPropTypeDate() {
		return "date";
	}


	@GetMapping({"getPreviewURL"})
	@ResponseBody
	public String getPreviewURL(@RequestParam String uuid) throws Exception {
		try {
			Document doc = this.browserService.getDocumentById(uuid);
			return generatePreviewUrl((doc != null) ? doc.getUuid() : null);
		} catch (Exception e) {
			log.error("{}", e.toString());
			throw e;
		}
	}

	private String generatePreviewUrl(String uuid) throws Exception {
		StringBuilder previewUrl = new StringBuilder();
		OKMWebservices ws = this.cs.getAdminWebServices();
		if (StringUtils.isNotEmpty(uuid)) {
			previewUrl.append(this.cfg.getPreviewToOpenKMUrl());
			String token = ws.node.generateDownloadToken(uuid, true, false, null);
			//previewUrl.append("&pdfUrl=");
			previewUrl.append(getPdfUrl(token));
		}
		return previewUrl.toString();
	}

	private String getPdfUrl(String token) throws Exception {
		String previewUrl = cfg.OPENKM_URL_PREVIEW + "/converter?PTK=" + token +
				"&inline=true&toPdf=true&downloadType=preview&version=1.0";
		log.debug("previewUrl: {}", previewUrl);
		return URLEncoder.encode(previewUrl, StandardCharsets.UTF_8);
	}

	private SimpleNodeBase getFirstDocument(List<SimpleNodeBase> docs) {
		if (docs != null && docs.size() > 0)
			return docs.get(0);
		return null;
	}



	@RequestMapping({"/formPropertyGroup"})
	public String header() {
		return "property_group/include/form_property_group";
	}

	@GetMapping({"getNotes"})
	@ResponseBody
	public List<Note> getNotes(@RequestParam String uuid) throws Exception {
		try {
			return this.browserService.getNotesById(uuid);
		} catch (Exception e) {
			log.error("{}", e.toString());
			throw e;
		}
	}

	@GetMapping({"getOKG"})
	@ResponseBody
	public Map<String, Map<String, String>> getOKG(@RequestParam String uuid) throws Exception {
		try {
			return this.browserService.getPropertiesGroupsbyId(uuid);
		} catch (Exception e) {
			log.error("{}", e.toString());
			throw e;
		}
	}
}
