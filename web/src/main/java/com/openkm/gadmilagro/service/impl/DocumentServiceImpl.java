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

package com.openkm.gadmilagro.service.impl;

import com.openkm.gadmilagro.cache.WSCacheDAO;
import com.openkm.gadmilagro.service.DocumentService;
import com.openkm.gadmilagro.constants.MimeTypeConstants;
import com.openkm.sdk4j.bean.Document;
import com.openkm.gadmilagro.util.PathUtils;
import com.openkm.sdk4j.exception.*;
import com.openkm.sdk4j.impl.OKMWebservices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * DocumentServiceImpl
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService {
	private static final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);



	@Autowired
	private WSCacheDAO wsDao;


	@Override
	public InputStream getContent(String node) throws RepositoryException, PathNotFoundException, AccessDeniedException,
			DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		OKMWebservices ws = wsDao.getOKMWebservices(WSCacheDAO.ADMIN_USER);
		return ws.document.getContent(node);
	}

	@Override
	public Document getProperties(String node) throws RepositoryException, PathNotFoundException, DatabaseException,
			UnknownException, WebserviceException, AccessDeniedException, AuthenticationException {
		OKMWebservices ws = wsDao.getOKMWebservices(WSCacheDAO.ADMIN_USER);
		return ws.document.getProperties(node);
	}
	@Override
	public InputStream getContentInPdfFormat(String node) throws RepositoryException, PathNotFoundException, DatabaseException,
			UnknownException, WebserviceException, AccessDeniedException, ConversionException, AuthenticationException {
		OKMWebservices ws = wsDao.getOKMWebservices(WSCacheDAO.ADMIN_USER);
		Document doc = getProperties(node);

		if (MimeTypeConstants.validImageMagick.contains(doc.getMimeType())) {
			return ws.conversion.imageConvert(getContent(node), PathUtils.getName(doc.getPath()), "", MimeTypeConstants.MIME_PDF);
		} else if (MimeTypeConstants.validOpenOffice.contains(doc.getMimeType())) {
			return ws.conversion.doc2pdf(getContent(node), PathUtils.getName(doc.getPath()));
		}

		return ws.document.getContent(node);
	}
}
