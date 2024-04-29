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

package com.openkm.gadmilagro.cache.impl;

import com.openkm.gadmilagro.cache.WSCacheDAO;
import com.openkm.gadmilagro.config.Config;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.exception.AuthenticationException;
import com.openkm.sdk4j.exception.UnknownException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.openkm.sdk4j.impl.OKMWebservices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Spring MVC component to manage caching of OKMWebservices object
 */
@Service("wsCache")
public class WSCacheDAOImpl implements WSCacheDAO {
	private static final Logger log = LoggerFactory.getLogger(WSCacheDAOImpl.class);
	private String okmUrl = "";

	@Autowired
	private Config cfg;

	@Autowired
	public WSCacheDAOImpl(@Value("${openkm.url}") String okmUrl) {
		this.okmUrl = okmUrl;
	}

	@Override
	@Cacheable(value = "wsCache", key = "#username")
	public OKMWebservices getOKMWebservices(String username) throws AuthenticationException, UnknownException, WebserviceException {
		log.debug("getOKMWebservices: {}", username);
		OKMWebservices ws;

		if (username.equals(ADMIN_USER)) {
			log.info("admin user: {} - {} - {}", okmUrl, cfg.ADMIN_USER, cfg.ADMIN_PASSWORD);
		    ws = OKMWebservicesFactory.getInstance(okmUrl);
			ws.login(cfg.ADMIN_USER, cfg.ADMIN_PASSWORD);
		} else {
			String password = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword();
			log.debug("regular user: {} - {} - {}", okmUrl, username, password);
			ws = OKMWebservicesFactory.getInstance(okmUrl);
			ws.login(username, password);
		}

		return ws;
	}


	@Override
	@CacheEvict(value = "wsCache", key = "#username")
	public void evictOKMWebservices(String username) {
		log.info("evict cache for {}", username);
	}

	@Override
	@Cacheable(value = "wsCache", key = "#username")
	public OKMWebservices setOKMWebservices(String username, String password) throws AuthenticationException,
			UnknownException, WebserviceException {
		log.debug("set OKMWebservices object for the first time in the cache for username {}", username);
		OKMWebservices ws = OKMWebservicesFactory.getInstance(okmUrl);
		ws.login(username, password);
		return ws;
	}
}
