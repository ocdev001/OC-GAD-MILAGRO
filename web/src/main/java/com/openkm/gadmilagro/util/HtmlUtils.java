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

import com.openkm.sdk4j.bean.form.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author agallego
 */
public class HtmlUtils {

	/**
	 * Generate html string from FormList Elements
	 */
	public String generateHtmlFromFormList(List<FormElement> formElementList, Map<String, String> values,
										   String dateFormat, boolean requiredAllowed) {
		int dateInputs = 0;
		Map<String, String> dependencies = new HashMap<>();
		Map<String, String> child = new HashMap<>();
		String html = "";

		if (null != formElementList && formElementList.size() > 0) {
			// First save dependencies for select
			for (FormElement element : formElementList) {
				if (element instanceof Select) {
					Select select = (Select) element;
					for (Option option : select.getOptions()) {
						if (!option.getParentValue().isEmpty()) {
							dependencies.put(option.getParentValue(), getHtmlName(select.getName()));
							child.put(option.getValue(), option.getParentValue());
						}
					}
				}
			}

			for (FormElement element : formElementList) {
				if (element instanceof Input && "date".equalsIgnoreCase(((Input) element).getType())) {
					// This is required to generate different identified
					dateInputs++;
				}
				html += generateHtmlFromFormElement(element, values, dependencies, child, dateInputs, dateFormat, requiredAllowed);
			}
		}

		return html;
	}

	/**
	 * Process each element of the form.
	 */
	public String generateHtmlFromFormElement(FormElement element, Map<String, String> values, Map<String, String> dependencies,
											  Map<String, String> child, int dateInputs, String dateFormat, boolean requiredAllowed) {
		StringBuffer buffer = new StringBuffer();
		if (element instanceof Input) {
			Input input = (Input) element;
			if (!"date".equalsIgnoreCase(input.getType())) {
				buffer.append("<label class=\"control-label\" for=\"").append(input.getName()).append("\">")
						.append(input.getLabel()).append("</label> ");
				buffer.append("<input class=\"form-control\" ");
				buffer.append("type=\"").append(input.getType()).append("\" ");
				buffer.append("name=\"").append(input.getName()).append("\" ");
				if (values != null && values.containsKey(input.getName())) {
					buffer.append("value=\"").append(values.get(input.getName())).append("\" ");
				}
				if (null != input.getPlaceholder()) {
					buffer.append("placeholder=\"").append(input.getPlaceholder()).append("\" ");
				}
				buffer.append("height=\"").append(input.getHeight()).append("\" ");
				buffer.append("width=\"").append(input.getWidth()).append("\" ");
				if (requiredAllowed && validateElementValidatorType(input.getValidators(), Validator.TYPE_REQUIRED)) {
					buffer.append("required");
				}
				buffer.append("/><br/>");
			} else {
				// input date.
				buffer.append("<div class=\"row\">");
				buffer.append("<div class=\"col-sm-6\">");
				buffer.append("<label class=\"control-label\" for=\"").append(input.getName()).append("\">")
						.append(input.getLabel()).append("</label> ");
				buffer.append("<div class='input-group date' id='datetimepicker").append(dateInputs).append("'>");
				buffer.append("<input type='text' name=\"").append(input.getName()).append("\" class=\"form-control\" ");
				if (values != null && values.containsKey(input.getName())) {
					buffer.append("value=\"").append(values.get(input.getName())).append("\" ");
				}
				if (requiredAllowed && validateElementValidatorType(input.getValidators(), Validator.TYPE_REQUIRED)) {
					buffer.append("required");
				}
				buffer.append("/>");
				buffer.append("<span class=\"input-group-addon\">");
				buffer.append("<span class=\"glyphicon glyphicon-calendar\"></span>");
				buffer.append("</span></div>");
				buffer.append("<script type=\"text/javascript\">");
				buffer.append("$(document).ready(function () {");
				buffer.append("$(function () {");
				buffer.append("$('#datetimepicker").append(dateInputs).append("').datetimepicker({format: '")
						.append(dateFormat).append("'});");
				buffer.append("});");
				buffer.append("});");
				buffer.append("</script>");
				buffer.append("</div></div>");
				buffer.append("<br/>");
			}

		} else if (element instanceof Select) {
			Select select = (Select) element;

			// looks if is a select with child
			String childName = "";
			String parentName = "";
			for (Option option : select.getOptions()) {
				if (dependencies.containsKey(option.getValue())) {
					childName = dependencies.get(option.getValue());
					parentName = getHtmlName(select.getName());
					break;
				}
			}

			buffer.append("<div class=\"row\">");
			buffer.append("<div class=\"col-sm-6\">");
			buffer.append("<label class=\"control-label\" for=\"").append(select.getName()).append("\">")
					.append(select.getLabel()).append("</label> ");
			buffer.append("<select ");
			if (requiredAllowed && validateElementValidatorType(select.getValidators(), Validator.TYPE_REQUIRED)) {
				buffer.append("required ");
			}
			buffer.append("class=\"form-control\" ");
			buffer.append("type=\"").append(select.getType()).append("\" ");
			buffer.append("name=\"").append(select.getName()).append("\" ");
			buffer.append("id=\"").append(getHtmlName(select.getName())).append("\" ");
			if (values != null && values.containsKey(select.getName())) {
				buffer.append("value=\"").append(values.get(select.getName())).append("\" ");
			}
			buffer.append("height=\"").append(select.getHeight()).append("\" ");
			if (!childName.isEmpty()) {
				buffer.append("width=\"").append(select.getWidth()).append("\" ");
				buffer.append("onchange=\"").append("javascript:changeOption('" + parentName + "', '" + childName + "')")
						.append("\" >");
			} else {
				buffer.append("width=\"").append(select.getWidth()).append("\" >");
			}
			buffer.append("<option value=\"\"></option>");
			for (Option option : select.getOptions()) {
				buffer.append("<option value=\"");
				buffer.append(option.getValue()).append("\" ");
				if (values != null && values.containsKey(select.getName())
						&& option.getValue().equalsIgnoreCase(values.get(select.getName()))) {
					buffer.append("selected ");
				}
				if (child.containsKey(option.getValue())) {
					buffer.append("parentId=\"").append(child.get(option.getValue())).append("\" ");
				}
				buffer.append(" >");
				buffer.append(option.getLabel());
				buffer.append("</option>");
			}
			buffer.append("</select></div></div><br/>");
		} else if (element instanceof TextArea) {
			TextArea textarea = (TextArea) element;
			buffer.append("<label class=\"control-label\" for=\"").append(textarea.getName()).append("\">")
					.append(textarea.getLabel()).append("</label> ");
			buffer.append("<textarea class=\"form-control\" ");
			buffer.append("name=\"").append(textarea.getName()).append("\" ");
			buffer.append("rows=\"10\" ");
			buffer.append("width=\"").append(textarea.getWidth()).append("\" ");
			if (requiredAllowed && validateElementValidatorType(textarea.getValidators(), Validator.TYPE_REQUIRED)) {
				buffer.append("required ");
			}
			buffer.append("/>");
			if (values != null && values.containsKey(textarea.getName())) {
				buffer.append(values.get(textarea.getName()));
			}
			buffer.append("</textarea><br/>");
		} else if (element instanceof Button) {
			Button button = (Button) element;
			buffer.append("<label class=\"control-label\" for=\"").append(button.getName()).append("\">")
					.append(button.getLabel()).append("</label> ");
			buffer.append("<button class=\"form-control\" ");
			buffer.append("name=\"").append(button.getName()).append("\" ");
			buffer.append("height=\"").append(button.getHeight()).append("\" ");
			buffer.append("width=\"").append(button.getWidth()).append("\" ");
			buffer.append("/><br/>");
		} else if (element instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) element;
			buffer.append("<label for=\"").append(checkBox.getName()).append("\">").append(checkBox.getLabel())
					.append("</label> ");
			buffer.append("<input type=\"checkbox\" value=\"true\" ");
			buffer.append("name=\"").append(checkBox.getName()).append("\" ");
			buffer.append("height=\"").append(checkBox.getHeight()).append("\" ");
			buffer.append("width=\"").append(checkBox.getWidth()).append("\" ");
			if (values != null && values.containsKey(checkBox.getName())) {
				buffer.append("checked");
			}
			buffer.append("><br/>");
		} else if (element instanceof Text) {
			Text text = (Text) element;
			buffer.append("<p class=\"control-label\" ");
			buffer.append("height=\"").append(text.getHeight()).append("\" ");
			buffer.append("width=\"").append(text.getWidth()).append("\" >");
			buffer.append(text.getLabel());
			buffer.append("</p><br/>");
		} else if (element instanceof Separator) {
			Separator separator = (Separator) element;
			buffer.append("<hr class=\"control-label\" ");
			buffer.append("width=\"").append(separator.getWidth()).append("\" />");
			if (separator.getLabel() != null) {
				buffer.append(separator.getLabel());
			}
			buffer.append("<br/>");
		}

		return buffer.toString();
	}

	/**
	 * Get correct id for html
	 */
	private String getHtmlName(String name) {
		if (name.indexOf(".") > 0) {
			name = name.substring(name.indexOf(".") + 1);
		}
		return name;
	}

	/**
	 * Check the validator type
	 */
	public boolean validateElementValidatorType(List<Validator> validators, String validatorType) {
		if (null != validators && validators.size() > 0) {
			for (Validator validator : validators) {
				if (validatorType.equalsIgnoreCase(validator.getType())) {
					return true;
				}
			}
		}
		return false;
	}
}
