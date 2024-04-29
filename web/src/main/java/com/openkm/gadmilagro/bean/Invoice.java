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

import java.io.Serializable;
import java.util.Calendar;

public class Invoice implements Serializable {
	private String type;
	private String cif;
	private String vat;
	private String total;
	private Calendar date;
	private String year;
	private String month;
	private String user;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCif() {
		return cif;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getVat() {
		return vat;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal() {
		return total;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getDate() {
		return date;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return month;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("cif=").append(cif);
		sb.append(", vat=").append(vat);
		sb.append(", total=").append(total);
		sb.append(", date=").append(date);
		sb.append("}");
		return sb.toString();
	}
}
