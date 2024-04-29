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

import com.openkm.gadmilagro.bean.PropertiesGroups;
import com.openkm.gadmilagro.bean.PropertiesGroupsGroups;
import com.openkm.gadmilagro.bean.searchForm;
import com.openkm.gadmilagro.cache.WSCacheDAO;
import com.openkm.gadmilagro.config.Config;
import com.openkm.gadmilagro.config.I18nConfig;

import com.openkm.gadmilagro.service.BrowserService;


import com.openkm.gadmilagro.util.PathUtils;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Note;
import com.openkm.sdk4j.bean.PropertyGroup;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.bean.QueryResult;
import com.openkm.sdk4j.bean.SimpleNodeBase;
import com.openkm.sdk4j.bean.SimpleNodeBaseList;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AuthenticationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.NoSuchGroupException;
import com.openkm.sdk4j.exception.ParseException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.PluginNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknownException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.openkm.sdk4j.impl.OKMWebservices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("browserService")
public class BrowserServiceImpl implements BrowserService {
	private static final Logger log = LoggerFactory.getLogger(com.openkm.gadmilagro.service.impl.BrowserServiceImpl.class);

	@Autowired
	private I18nConfig i18nConfig;

	@Autowired
	private WSCacheDAO wsDao;

	@Autowired
	private Config cfg;


	public Document getDocumentById(String docId) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		log.debug("getDocumentById({})", docId);
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		return ws.document.getDocumentProperties(docId);
	}

	public List<Note> getNotesById(String docId) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		log.debug("getDocumentById({})", docId);
		List<Note> notes = ws.note.listNotes(docId);
		for (Note note : notes)
			System.out.println(note);
		return notes;
	}


	/*public List<searchForm> searchMetadata(String name, String notes, String content, String author, long size, Map<String, String> okpGroupsName) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchGroupException, AuthenticationException, PluginNotFoundException {
		log.debug("getExtendedDocuments({})", name);
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		List<searchForm> documents = new ArrayList<>();
		System.out.println("GRUPOS OKP: " + okpGroupsName);
		Map<String, String> properties = new HashMap<>();
		try {
			String uuid = new String();
			String path = new String();
			String shortenPath = new String();
			Document docprops = new Document();
			QueryParams params1 = new QueryParams();
			params1.setDomain(1L);
			for (Map.Entry<String, String> entry : okpGroupsName.entrySet()) {
				String nombreInput = entry.getKey();
				String valorInput = entry.getValue();
				System.out.println("Nombre del Input: " + nombreInput);
				System.out.println("Valor del Input: " + valorInput);
				properties.put(nombreInput, valorInput);
			}
			System.out.println("PROPERTIES" + properties);
			params1.setProperties(properties);
			for (QueryResult qr : ws.search.find(params1, null)) {
				uuid = qr.getNode().getUuid();
				System.out.println("UUID " + uuid);
				path = ws.document.getDocumentPath(uuid);
				docprops = ws.document.getDocumentProperties(uuid);
				author = docprops.getAuthor();
				size = docprops.getActualVersion().getSize();
				size /= 1024L;
				shortenPath = PathUtils.shortenFileName(path, 35);
				documents.add(new searchForm(shortenPath, author, uuid, ,shortenPath, size));
			}
			return documents;
		} catch (Exception e) {
			e.printStackTrace();
			return documents;
		}
	}*/

	public List<searchForm> searchDocuments(String content,String author, String title, String edit, String year, long size) throws RepositoryException, AccessDeniedException, PathNotFoundException, DatabaseException, UnknownException, WebserviceException, IOException, ParseException, NoSuchGroupException, AuthenticationException, PluginNotFoundException {
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		List<searchForm> documents = new ArrayList<>();
		try {
			String uuid = new String();
			String path = new String();
			String shortenPath = new String();
			Document docprops = new Document();
			String okg="";
			String authorgad="";
			String titlegad="";
			String editgad="";
			String yeargad="";
			QueryParams params = new QueryParams();
			Map<String, String> properties = new HashMap();
			System.out.println("Valor del Input: " + author + title + edit + year);
			params.setDomain(QueryParams.DOCUMENT);
			if(!Objects.equals(content, "")){
				System.out.println("entro aqui");
				params.setContent(content);
			}else{
				if(!author.equals("")){
					properties.put("okp:gad_milagro.author", author);
				}
				if(!title.equals("")){
					properties.put("okp:gad_milagro.title", title);
				}
				if(!edit.equals("")){
					properties.put("okp:gad_milagro.edit", edit);
				}
				if(!year.equals("")){
					properties.put("okp:gad_milagro.year", year);
				}

				params.setProperties(properties);
			}
			for (QueryResult qr : ws.search.find(params, null)) {
				uuid = qr.getNode().getUuid();
				path = ws.document.getDocumentPath(uuid);
				docprops = ws.document.getDocumentProperties(uuid);
				author = docprops.getAuthor();
				size = docprops.getActualVersion().getSize();
				size /= 1024L;
				shortenPath = PathUtils.shortenFileName(path, 35);
				for (PropertyGroup pGroup : ws.propertyGroup.getPropertyGroups(uuid)) {
					if(pGroup.getName().equals(cfg.OKG_MILAGRO)){
						okg=pGroup.getName();
						Map<String, String> propertiesgad = new HashMap<>();
						propertiesgad = ws.propertyGroup.getPropertyGroupProperties(uuid, okg);
						authorgad=propertiesgad.get("okp:gad_milagro.author");
						titlegad=propertiesgad.get("okp:gad_milagro.title");
						editgad=propertiesgad.get("okp:gad_milagro.edit");
						yeargad=propertiesgad.get("okp:gad_milagro.year");
						documents.add(new searchForm(content,shortenPath, authorgad, uuid, editgad,yeargad,titlegad,shortenPath,size));
					}
				}

			}
			return documents;
		} catch (Exception e) {
			e.printStackTrace();
			return documents;
		}
	}

	public List<PropertiesGroups> getSelectConfig() throws RepositoryException, AccessDeniedException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		List<PropertiesGroups> okggroups = new ArrayList<>();
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		String listOptions= ws.repository.getConfiguration(cfg.METADATA_EXTERNAL_SELECT).getValue().toString();
		System.out.println("LISTA DEL SELECT: " + listOptions);
		String[] listOptionsArray = listOptions.split(";");
		String labelName = "";
		String valueName = "";
		int cont=1;
		try {
			for (int i=0; i < listOptionsArray.length; i++ ) {
				if(cont==1){
					labelName = listOptionsArray[i];
					cont =2;
				}else{
					valueName = listOptionsArray[i];
					cont=1;
				}
				if(cont==1){
					okggroups.add(new PropertiesGroups(labelName, valueName));
				}
			}
		} catch (Exception e) {
			log.warn("getYearList: no search results");
		}
		return okggroups;
	}

	public Map<String, Map<String, String>> getPropertiesGroupsbyId(String uuid) throws RepositoryException, AccessDeniedException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		Map<String, Map<String, String>> okggroups = new HashMap<>();
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		Map<String, String> map = new HashMap<>();
		String selects="";
		try {
			for (PropertyGroup pGroup : ws.propertyGroup.getPropertyGroups(uuid)) {
				Map<String, String> groupDetails = new HashMap<>();
				Map<String, String> properties = new HashMap<>();
				properties = ws.propertyGroup.getPropertyGroupProperties(uuid, pGroup.getName());

				for (String key : properties.keySet()) {
					for (FormElement fElement : ws.propertyGroup.getPropertyGroupForm(uuid, pGroup.getName())) {
						if (key.equals(fElement.getName())) {
							String fElementString = fElement.toString();
							String typeMetadata = fElement.getClass().getTypeName();
							if (typeMetadata.equals(this.cfg.METADATA_TYPE_SELECT)) {
								Pattern pattern = Pattern.compile("\\[(.*?)\\]");
								Matcher matcher = pattern.matcher(fElementString);

								// Buscar y extraer el contenido dentro de los corchetes
								if (matcher.find()) {
									String optionsContent = matcher.group(1);
									selects += optionsContent;
									System.out.println("Contenido de options: " + optionsContent);
								} else {
									System.out.println("No se encontró ningún contenido dentro de los corchetes.");
								}
							}
						}
					}
				}
			}
			// Definir la expresión regular para buscar el contenido entre llaves
			String regex = "\\{([^}]*)\\}";

			// Compilar la expresión regular en un objeto Pattern
			Pattern pattern = Pattern.compile(regex);

			// Crear un Matcher para buscar coincidencias en la cadena
			Matcher matcher = pattern.matcher(selects);

			// Crear una lista para almacenar los valores encontrados
			List<String> valores = new ArrayList<>();

			// Iterar sobre las coincidencias y almacenarlas en la lista
			while (matcher.find()) {
				valores.add(matcher.group(1)); // Obtener el texto capturado entre paréntesis y añadirlo a la lista
			}

			// Expresiones regulares para extraer el label y el value
			Pattern labelPattern = Pattern.compile("label=([^,]+)");
			Pattern valuePattern = Pattern.compile("value=([^,]+)");

			// Mapa para almacenar los pares label-value
			Map<String, String> resultMap = new HashMap<>();

			// Iterar sobre cada cadena en el ArrayList
			for (String valor : valores) {
				Matcher labelMatcher = labelPattern.matcher(valor);
				Matcher valueMatcher = valuePattern.matcher(valor);

				String label = "";
				String value = "";

				// Buscar el label
				if (labelMatcher.find()) {
					label = labelMatcher.group(1);
				}

				// Buscar el value
				if (valueMatcher.find()) {
					value = valueMatcher.group(1);
				}

				// Agregar el par label-value al mapa
				resultMap.put(label, value);
			}



			for (PropertyGroup pGroup : ws.propertyGroup.getPropertyGroups(uuid)) {
				Map<String, String> groupDetails = new HashMap<>();
				Map<String, String> properties = new HashMap<>();
				properties = ws.propertyGroup.getPropertyGroupProperties(uuid, pGroup.getName());
				SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat formatoDeseado = new SimpleDateFormat("yyyy-MM-dd");
				for (String key : properties.keySet()) {
					for (FormElement fElement : ws.propertyGroup.getPropertyGroupForm(uuid, pGroup.getName())) {
						if (key.equals(fElement.getName())) {
							String fElementString = fElement.toString();
							fElementString = fElementString.replaceAll("[{}]", "");
							String[] pares = fElementString.split(", ");
							for (String par : pares) {
								String[] partes = par.split("=");
								if (partes.length >= 2) {
									map.put(partes[0], partes[1]);
								} else {
									map.put(partes[0], "");
								}
							}
							String keyValue = properties.get(key);
							String typeMetadatatype = map.get("type");
							if (typeMetadatatype != null && typeMetadatatype.equals(this.cfg.METADATA_TYPE_DATE)) {
								if (keyValue != null) {
									Date fecha = formatoOriginal.parse(keyValue);
									String fechaFormateada = formatoDeseado.format(fecha);
									keyValue = fechaFormateada;
								} else {
									log.warn("El valor de la fecha es nulo para la clave: " + key);
								}
							}
							String typeMetadata = fElement.getClass().getTypeName();
							if (typeMetadata.equals(this.cfg.METADATA_TYPE_SELECT)) {
								keyValue = keyValue.replace("[", "");
								keyValue = keyValue.replace("]", "");
								keyValue = keyValue.replace("\"", "");
								// Imprimir el mapa resultante
								for (Map.Entry<String, String> entry : resultMap.entrySet()) {
									if(entry.getValue().equals(keyValue)){
										keyValue=entry.getKey();
									}
								}
							}
							groupDetails.put(fElement.getLabel(), keyValue);
						}
						map.clear();
					}
				}
				okggroups.put(pGroup.getLabel(), groupDetails);
			}
		} catch (Exception e) {
			log.warn("Error en GEtmetadat by id" + e);
		}
		return okggroups;
	}

	public List<PropertiesGroupsGroups> getPropertiesGroupsGroups(String okpgroups) throws RepositoryException, AccessDeniedException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		List<PropertiesGroupsGroups> okgmetadatagroups = new ArrayList<>();
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		String nameLabel = "";
		try {
			for (FormElement fElement : ws.propertyGroup.getPropertyGroupForm(okpgroups)) {
				System.out.println(fElement);
				okgmetadatagroups.add(new PropertiesGroupsGroups(fElement.getLabel(), fElement.getName()));
			}
		} catch (Exception e) {
			log.warn("getYearList: no search results");
		}
		return okgmetadatagroups;
	}

	public List<String> getYearList(String cif, String docType) throws RepositoryException, AccessDeniedException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		log.debug("getYearList({}, {})", cif, docType);
		List<String> years = new ArrayList<>();
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		try {
			String fldPath = this.cfg.ROOT_DESTINATION_FOLDER + "/" + cif + "/" + docType;
			String rootFolderUuid = ws.repository.getNodeUuid(fldPath);
			SimpleNodeBaseList simpleNodeBaseList = ws.node.getChildrenNodesPaginated(rootFolderUuid, 0, 100, "", "name", true, Collections.singletonList(Integer.valueOf(1)));
			for (SimpleNodeBase node : simpleNodeBaseList.getNodes())
				years.add(node.getName());
		} catch (PathNotFoundException e) {
			log.warn("getYearList: no search results");
		}
		return years;
	}

	public List<String> getMonthList(String cif, String docType, String year) throws RepositoryException, AccessDeniedException, DatabaseException, UnknownException, WebserviceException, AuthenticationException {
		log.debug("getMonthList({}, {}, {})", new Object[] { cif, docType, year });
		List<String> months = new ArrayList<>();
		OKMWebservices ws = this.wsDao.getOKMWebservices("admin");
		try {
			String fldPath = this.cfg.ROOT_DESTINATION_FOLDER + "/" + cif + "/" + docType + "/" + year;
			String rootFolderUuid = ws.repository.getNodeUuid(fldPath);
			SimpleNodeBaseList simpleNodeBaseList = ws.node.getChildrenNodesPaginated(rootFolderUuid, 0, 100, "", "name", true,
					Collections.singletonList(Integer.valueOf(1)));
			for (SimpleNodeBase node : simpleNodeBaseList.getNodes())
				months.add(node.getName());
		} catch (PathNotFoundException e) {
			log.warn("getMonthList: no search results");
		}
		return months;
	}
}
