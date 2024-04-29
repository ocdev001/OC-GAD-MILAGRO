package com.openkm.gadmilagro.service;

import com.openkm.sdk4j.exception.AuthenticationException;
import com.openkm.sdk4j.exception.UnknownException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.openkm.sdk4j.impl.OKMWebservices;

public interface CommonService {
	OKMWebservices getWebServices() throws AuthenticationException, UnknownException, WebserviceException;

	OKMWebservices getAdminWebServices() throws AuthenticationException, UnknownException, WebserviceException;
}
