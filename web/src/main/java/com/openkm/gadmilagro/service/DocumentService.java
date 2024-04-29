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

package com.openkm.gadmilagro.service;

import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.exception.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface DocumentService {
	InputStream getContent(String node) throws RepositoryException, IOException, PathNotFoundException,
			AccessDeniedException, DatabaseException, UnknownException, WebserviceException, AuthenticationException;

	Document getProperties(String node) throws RepositoryException, PathNotFoundException, DatabaseException,
			UnknownException, WebserviceException, AccessDeniedException, AuthenticationException;

	InputStream getContentInPdfFormat(String node)
			throws RepositoryException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException,
			AccessDeniedException, IOException, ConversionException, AuthenticationException;


}
