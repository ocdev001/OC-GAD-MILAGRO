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

package com.openkm.gadmilagro.bean;

import com.openkm.gadmilagro.util.HtmlUtils;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.bean.form.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupFormElements {
	private final Map<String, FormElement> mapElements = new HashMap<>();
	private List<FormElement> elements;
	private DocumentType documentType;

	public GroupFormElements(DocumentType documentType, List<FormElement> elements) {
		if (elements != null) {
			for (FormElement formElement : elements) {
				if (StringUtils.isNotEmpty(formElement.getName())) {
					// Id = From the name we get the last piece after the last point, format name example:  'okp:invoice.num_invoice' result: 'num_invoice'
					mapElements.put(formElement.getName().substring(formElement.getName().lastIndexOf(".") + 1), formElement);
				}
			}
		}

		this.elements = elements;
		this.documentType = documentType;
	}

	public List<FormElement> getElements() {
		return elements;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public boolean isRequired(List<Validator> validators) {
		HtmlUtils htmlUtils = new HtmlUtils();
		return htmlUtils.validateElementValidatorType(validators, Validator.TYPE_REQUIRED);
	}

	public boolean isNumeric(List<Validator> validators) {
		HtmlUtils htmlUtils = new HtmlUtils();
		return htmlUtils.validateElementValidatorType(validators, Validator.TYPE_NUMERIC);
	}

	public boolean isDecimal(List<Validator> validators) {
		HtmlUtils htmlUtils = new HtmlUtils();
		return htmlUtils.validateElementValidatorType(validators, Validator.TYPE_DECIMAL);
	}

	public FormElement getFormElement(String property) {
		return mapElements.get(property);
	}
}
