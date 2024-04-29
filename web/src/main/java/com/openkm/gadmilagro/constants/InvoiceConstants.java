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

package com.openkm.gadmilagro.constants;

public class InvoiceConstants {
	// Metadata group
	public static final String PROPERTY_GROUP_INVOICE = "okg:invoice";
	public static final String PROPERTY_GROUP_DOCUMENT_TYPE = "okg:invoice_doc_type";

	// Properties group
	public static final String PROPERTY_DOCUMENT_TYPE_TYPE = "okp:invoice_doc_type.type";
	public static final String PROPERTY_INVOICE_STATUS = "okp:invoice.status";
	public static final String PROPERTY_INVOICE_YEAR = "okp:invoice.year";
	public static final String PROPERTY_INVOICE_MONTH = "okp:invoice.month";
	public static final String PROPERTY_INVOICE_CIF = "okp:invoice.cif";

	public static final String PROPERTY_ID_CIF = "cif";
	public static final String PROPERTY_ID_VAT = "vat";
	public static final String PROPERTY_ID_DATE = "date";
	public static final String PROPERTY_ID_TOTAL = "total";
	public static final String PROPERTY_ID_STATUS = "status";
	public static final String PROPERTY_ID_YEAR = "year";
	public static final String PROPERTY_ID_MONTH = "month";
	public static final String PROPERTY_ID_USER = "user";
	public static final String PROPERTY_ID_COMPANY_CODE = "prov_code";
	public static final String PROPERTY_ID_COMPANY_NAME = "prov_name";

	public static final String ID_USER_ADMIN = "admin";

	public static final String ROLE_STANDARD = "ROLE_STANDARD";
	public static final String ROLE_ADVANCED = "ROLE_ADVANCED";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
}
