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

package com.openkm.gadmilagro.util;

import com.openkm.gadmilagro.bean.FileChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by pavila on 26/05/17.
 */
public class WebUtils {
	private static final Logger log = LoggerFactory.getLogger(WebUtils.class);
	public static final String EMPTY_STRING = "";

	/**
	 * Prepare to send the file.
	 */
	public static void prepareSendFile(HttpServletRequest request, HttpServletResponse response, String fileName,
									   String mimeType, boolean inline) throws UnsupportedEncodingException {
		log.debug("prepareSendFile({}, {}, {})", fileName, mimeType, inline);
		String userAgent = getHeader(request, "user-agent").toLowerCase();
		fileName = PathUtils.decodeEntities(fileName);

		//Allow to use files in different domains
		response.setHeader("Access-Control-Allow-Origin", "*");

		// Set MIME type
		response.setContentType(mimeType);

		if (userAgent.contains("msie") || userAgent.contains("trident")) {
			log.debug("Agent: Explorer ({})", request.getServerPort());
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");

			if (request.getServerPort() == 443) {
				log.debug("HTTPS detected! Apply IE workaround...");
				response.setHeader("Cache-Control", "max-age=1");
				response.setHeader("Pragma", "public");
			}
		} else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
			log.debug("Agent: iPhone - iPad");
			// Do nothing
		} else if (userAgent.contains("android")) {
			log.debug("Agent: Android");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (userAgent.contains("mozilla")) {
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}

		if (inline) {
			response.setHeader("Content-disposition", "inline; filename=\"" + fileName + "\"");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		}
	}

	/**
	 * Get HTTP header.
	 */
	public static String getHeader(HttpServletRequest request, String name) {
		String value = request.getHeader(name);
		if (value != null) {
			return value;
		} else {
			return EMPTY_STRING;
		}
	}

	/**
	 * getJSessionIdFromUrl
	 */
	public static String getJSessionIdFromUrl(String url) {
		if (url.lastIndexOf(";jsessionid=") != -1) {
			return url.substring(url.lastIndexOf(";jsessionid=") + ";jsessionid=".length());
		}
		return null;
	}

	/**
	 * getString
	 */
	public static String getString(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		String stringValue = EMPTY_STRING;

		if (value != null) {
			return value;
		}

		return stringValue;
	}

	/**
	 * getLong
	 */
	public static long getLong(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);
		long longValue = 0;

		if (strValue != null && !EMPTY_STRING.equals(strValue)) {
			try {
				longValue = Long.parseLong(strValue);
			} catch (Throwable t) {
				// Ignore
			}
		}

		return longValue;
	}

	public static FileChunk getFileChunk(HttpServletRequest request) {
		String dzuuid = getString(request, FileChunk.DZUUID);
		if (dzuuid != null && !dzuuid.equals(EMPTY_STRING)) {
			FileChunk fileChunk = new FileChunk();
			fileChunk.setDzuuid(dzuuid);
			fileChunk.setDzchunkindex(getLong(request, FileChunk.DZCHUNKINDEX));
			fileChunk.setDztotalchunkcount(getLong(request, FileChunk.DZTOTALFILESIZE));
			fileChunk.setDzchunksize(getLong(request, FileChunk.DZCHUNKSIZE));
			fileChunk.setDztotalchunkcount(getLong(request, FileChunk.DZTOTALCHUNKCOUNT));
			fileChunk.setDzchunkbyteoffset(getLong(request, FileChunk.DZCHUNKBYTEOFFSET));
			return fileChunk;
		}
		return null;
	}
}
