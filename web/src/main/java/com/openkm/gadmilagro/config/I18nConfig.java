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

import com.openkm.gadmilagro.service.CommonService;
import com.openkm.sdk4j.exception.*;
import com.openkm.sdk4j.impl.OKMWebservices;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
	private static final Logger log = LoggerFactory.getLogger(I18nConfig.class);
	private static Locale DEFAULT_LOCALE = new Locale("en", "GB");
	private static final String TRANSLATION_MODULE = "eds";

	@Autowired
	private CommonService cs;

	@Autowired
	private Config cfg;

	@Bean
	public MessageSource messageSource() {
		return new OKMMessageSource();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(cfg.getDefaultLocale());
		return localeResolver;
	}

	private class OKMMessageSource implements MessageSource {
		private Map<String, Map<String, String>> translations;

		@Override
		public String getMessage(MessageSourceResolvable arg0, Locale arg1) throws NoSuchMessageException {
			return null;
		}

		@Override
		public String getMessage(String key, Object[] arg1, Locale locale) throws NoSuchMessageException {
			log.debug("getMessage({}, {})", key, locale);
			return getTranslationsByLanguage(locale).get(key);
		}

		@Override
		public String getMessage(String key, Object[] arg1, String defaultValue, Locale locale) {
			if (StringUtils.isNotEmpty(key) && key.contains("login")) {
				locale = DEFAULT_LOCALE;
			}

			log.debug("getMessage({}, {}, {})", key, defaultValue, locale);
			String translation = getTranslationsByLanguage(locale).get(key);
			if (translation == null && defaultValue != null) {
				translation = defaultValue;
			}

			return translation;
		}

		/**
		 * Get translations by language
		 */
		private Map<String, String> getTranslationsByLanguage(Locale locale) {
			Map<String, String> translation = new HashMap<>();

			try {
				translation = getTranslations().get(locale.getLanguage() + "-" + locale.getCountry());
				if (translation == null) {
					translation = getTranslations().get(DEFAULT_LOCALE.getLanguage() + "-" + DEFAULT_LOCALE.getCountry());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			return translation;
		}

		/**
		 * Get current translations
		 */
		private Map<String, Map<String, String>> getTranslations() throws DatabaseException, UnknownException, WebserviceException,
				AccessDeniedException, AuthenticationException {
			if (translations == null) {
				initTranslations();
			}

			return translations;
		}

		/**
		 * Initialize translations
		 */
		private void initTranslations() throws DatabaseException, UnknownException, WebserviceException, AccessDeniedException,
				AuthenticationException {
			log.debug("initTranslations()");
			String[] languages = cfg.AVAILABLE_LANGUAGES.split(",");
			OKMWebservices ws = cs.getAdminWebServices();
			translations = new HashMap<>();

			for (String language : languages) {
				Map<String, String> translation = ws.repository.getTranslations(language.trim(), TRANSLATION_MODULE);
				translations.put(language, translation);
			}

			DEFAULT_LOCALE = cfg.getDefaultLocale();
		}
	}
}
