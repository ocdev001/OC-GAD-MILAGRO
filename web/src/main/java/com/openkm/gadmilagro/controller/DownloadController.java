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

import com.openkm.gadmilagro.service.DocumentService;
import com.openkm.gadmilagro.util.FileUtils;
import com.openkm.gadmilagro.util.PathUtils;
import com.openkm.gadmilagro.util.WebUtils;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.exception.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/download")
public class DownloadController {
	private static final Logger log = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	private DocumentService documentService;

	/**
	 * download
	 */
	@GetMapping
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException, RepositoryException,
			PathNotFoundException, AccessDeniedException, DatabaseException, UnknownException, WebserviceException,
			AuthenticationException {
		log.debug("download({}, {})", request, response);
		String node = request.getParameter("node");
		boolean inline = true;

		if (request.getParameter("attachment") != null && !request.getParameter("attachment").isEmpty()) {
			inline = false;
		}

		try (InputStream is = documentService.getContent(node)) {
			Document doc = documentService.getProperties(node);
			WebUtils.prepareSendFile(request, response, PathUtils.getName(doc.getPath()), doc.getMimeType(), inline);

			// Set length
			//response.setContentLength(is.available()); // Cause a bug, because at this point InputStream still has not its real size.
			response.setContentLength(new Long(doc.getActualVersion().getSize()).intValue());

			ServletOutputStream sos = response.getOutputStream();
			IOUtils.copy(is, sos);
			sos.flush();
			sos.close();
		}
	}

	/**
	 * downloadForPreview
	 */
	@GetMapping("/downloadForPreview")
	public void downloadForPreview(HttpServletRequest request, HttpServletResponse response) throws IOException,
			RepositoryException, PathNotFoundException, AccessDeniedException, DatabaseException, UnknownException,
			WebserviceException, NotImplementedException, ConversionException, AuthenticationException {
		log.debug("downloadForPreview({}, {})", request, response);
		String jSessionId = WebUtils.getJSessionIdFromUrl(request.getRequestURI());
		String node = request.getParameter("node");

		if (jSessionId != null && jSessionId.equalsIgnoreCase(request.getSession().getId())) {
			boolean conversion = !StringUtils.isEmpty(request.getParameter("conversion"));
			boolean inline = StringUtils.isEmpty(request.getParameter("attachment"));
			Document doc = documentService.getProperties(node);
			String fileName = PathUtils.getName(doc.getPath());

			if (conversion) {
				try (InputStream is = documentService.getContentInPdfFormat(node)) {
					fileName = FileUtils.getFileName(fileName) + ".pdf";
					WebUtils.prepareSendFile(request, response, fileName, doc.getMimeType(), inline);
					sendFile(is, response);
				}
			} else {
				try (InputStream is = documentService.getContent(node)) {
					WebUtils.prepareSendFile(request, response, fileName, doc.getMimeType(), inline);
					response.setContentLength(new Long(doc.getActualVersion().getSize()).intValue());
					sendFile(is, response);
				}
			}
		}
	}

	private void sendFile(InputStream is, HttpServletResponse response) throws IOException {
		ServletOutputStream sos = response.getOutputStream();
		IOUtils.copy(is, sos);
		sos.flush();
		sos.close();
	}
}
