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

package com.openkm.gadmilagro.config;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
	public static final String PROPERTIES = "eds-capture.properties";

	public static final String PREVIEW_ENGINE = "PDF.js";

	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String HIBERNATE_DIALECT;

	@Value("${openkm.url}")
	public String OPENKM_URL;

	@Value("${openkm.url.preview}")
	public String OPENKM_URL_PREVIEW;

	@Value("${admin.user}")
	public String ADMIN_USER;

	@Value("${admin.password}")
	public String ADMIN_PASSWORD;

	@Value("${preview.download.url}")
	public String PREVIEW_DOWNLOAD_URL;

	@Value("${standard.import.node}")
	public String STANDARD_IMPORT_NODE;

	@Value("${advanced.import.node}")
	public String ADVANCED_IMPORT_NODE;

	@Value("${languages}")
	public String AVAILABLE_LANGUAGES;

	@Value("${default.language}")
	public String DEFAULT_LANGUAGE;

	@Value("${app.logo}")
	public String APP_LOGO;

	@Value("${app.logo.login}")
	public String APP_LOGO_LOGIN;

	@Value("${root.destination.folder.path}")
	public String ROOT_DESTINATION_FOLDER;

	@Value("${filter.companies.by.user.upload.documents}")
	public boolean FILTER_COMPANIES_BY_USER_DOCUMENTS;

	@Value("date")
	public String METADATA_TYPE_DATE;

	@Value("com.openkm.sdk4j.bean.form.Select")
	public String METADATA_TYPE_SELECT;

	@Value("milagro.select.external")
	public String METADATA_EXTERNAL_SELECT;

	@Value("okg:gad_milagro")
	public String OKG_MILAGRO;

	public String getPreviewToOpenKMUrl() {
		String baseUrl = this.OPENKM_URL_PREVIEW;
		if (!this.OPENKM_URL_PREVIEW.endsWith("/"))
			baseUrl = baseUrl + "/";
		return baseUrl + "Preview?previewEngine=" + "PDF.js";
	}

	public Locale getDefaultLocale() {
		Locale defaultLoale = new Locale("en", "GB");
		if (StringUtils.isNotEmpty(this.DEFAULT_LANGUAGE)) {
			String[] defaultLanguage = this.DEFAULT_LANGUAGE.split("-");
			return new Locale(defaultLanguage[0], defaultLanguage[1]);
		}
		return defaultLoale;
	}
}
