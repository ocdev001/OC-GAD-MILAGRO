package com.openkm.gadmilagro.service.impl;

import com.openkm.gadmilagro.cache.WSCacheDAO;
import com.openkm.gadmilagro.service.CommonService;
import com.openkm.gadmilagro.util.PathUtils;
import com.openkm.sdk4j.exception.AuthenticationException;
import com.openkm.sdk4j.exception.UnknownException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.openkm.sdk4j.impl.OKMWebservices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	protected WSCacheDAO wsCacheService;

	@Autowired
	protected PathUtils pathUtils;

	@Override
	public OKMWebservices getWebServices() throws AuthenticationException, UnknownException, WebserviceException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return wsCacheService.getOKMWebservices(auth.getName());
	}

	@Override
	public OKMWebservices getAdminWebServices() throws AuthenticationException, UnknownException, WebserviceException {
		return wsCacheService.getOKMWebservices(WSCacheDAO.ADMIN_USER);
	}
}
