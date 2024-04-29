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

import com.openkm.gadmilagro.bean.PropertiesGroups;
import com.openkm.gadmilagro.bean.PropertiesGroupsGroups;
import com.openkm.gadmilagro.bean.searchForm;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Note;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AuthenticationException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.NoSuchGroupException;
import com.openkm.sdk4j.exception.NoSuchPropertyException;
import com.openkm.sdk4j.exception.ParseException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.PluginNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknownException;
import com.openkm.sdk4j.exception.ValidationFormException;
import com.openkm.sdk4j.exception.WebserviceException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BrowserService {

	Document getDocumentById(String paramString) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, AuthenticationException;

	List<Note> getNotesById(String paramString) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, AuthenticationException;

	List<PropertiesGroups>  getSelectConfig() throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException, AutomationException, AuthenticationException, PluginNotFoundException, ValidationFormException;

	Map<String, Map<String, String>> getPropertiesGroupsbyId(String paramString) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException, AutomationException, AuthenticationException, PluginNotFoundException, ValidationFormException;

	List<PropertiesGroupsGroups> getPropertiesGroupsGroups(String paramString) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException, AutomationException, AuthenticationException, PluginNotFoundException, ValidationFormException;

	List<String> getYearList(String paramString1, String paramString2) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException, AutomationException, AuthenticationException, PluginNotFoundException, ValidationFormException;

	List<String> getMonthList(String paramString1, String paramString2, String paramString3) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException, AutomationException, AuthenticationException, PluginNotFoundException, ValidationFormException;

	//List<searchForm> searchMetadata(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, Map<String, String> paramMap) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchGroupException, AuthenticationException, PluginNotFoundException;

	List<searchForm> searchDocuments(String paramString0,String paramString1, String paramString2, String paramString3, String paramString4, long paramLong) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException, ExtensionException, AutomationException, AuthenticationException, PluginNotFoundException, ValidationFormException;
}
