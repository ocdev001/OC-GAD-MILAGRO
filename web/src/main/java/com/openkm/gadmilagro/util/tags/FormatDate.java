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

package com.openkm.gadmilagro.util.tags;

import com.openkm.gadmilagro.util.FormatUtils;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

@SuppressWarnings("serial")
public class FormatDate extends TagSupport {
	private String pattern;
	private Object value;

	@Override
	public int doStartTag() {
		try {
			if (value != null) {
				String str = "";

				if (value instanceof Date) {
					if (pattern != null && !pattern.isEmpty()) {
						str = FormatUtils.formatDate((Date) value, pattern);
					} else {
						str = FormatUtils.formatDate((Date) value);
					}
				} else if (value instanceof Calendar) {
					Calendar cal = (Calendar) value;

					if (pattern != null && !pattern.isEmpty()) {
						str = FormatUtils.formatDate(new Date(cal.getTimeInMillis()), pattern);
					} else {
						str = FormatUtils.formatDate(new Date(cal.getTimeInMillis()));
					}
				}

				pageContext.getOut().write(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Tag.SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
		value = null;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
