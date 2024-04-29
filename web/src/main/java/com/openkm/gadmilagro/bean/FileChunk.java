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

import java.io.File;
import java.io.Serializable;

/**
 * FileChunk
 *
 * @author jllort
 */
public class FileChunk implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String DZUUID = "dzuuid";
	public static final String DZCHUNKINDEX = "dzchunkindex";
	public static final String DZTOTALFILESIZE = "dztotalfilesize";
	public static final String DZCHUNKSIZE = "dzchunksize";
	public static final String DZTOTALCHUNKCOUNT = "dztotalchunkcount";
	public static final String DZCHUNKBYTEOFFSET = "dzchunkbyteoffset";

	private String dzuuid;
	private long dzchunkindex;
	private long dztotalfilesize;
	private long dzchunksize;
	private long dztotalchunkcount;
	private long dzchunkbyteoffset;
	private File tmpFile;
	private String contentType;
	private String originalFileName;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public File getTmpFile() {
		return tmpFile;
	}

	public void setTmpFile(File tmpFile) {
		this.tmpFile = tmpFile;
	}

	public String getDzuuid() {
		return dzuuid;
	}

	public void setDzuuid(String dzuuid) {
		this.dzuuid = dzuuid;
	}

	public long getDzchunkindex() {
		return dzchunkindex;
	}

	public void setDzchunkindex(long dzchunkindex) {
		this.dzchunkindex = dzchunkindex;
	}

	public long getDztotalfilesize() {
		return dztotalfilesize;
	}

	public void setDztotalfilesize(long dztotalfilesize) {
		this.dztotalfilesize = dztotalfilesize;
	}

	public long getDzchunksize() {
		return dzchunksize;
	}

	public void setDzchunksize(long dzchunksize) {
		this.dzchunksize = dzchunksize;
	}

	public long getDztotalchunkcount() {
		return dztotalchunkcount;
	}

	public void setDztotalchunkcount(long dztotalchunkcount) {
		this.dztotalchunkcount = dztotalchunkcount;
	}

	public long getDzchunkbyteoffset() {
		return dzchunkbyteoffset;
	}

	public void setDzchunkbyteoffset(long dzchunkbyteoffset) {
		this.dzchunkbyteoffset = dzchunkbyteoffset;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("dzuuid=").append(dzuuid);
		sb.append(", dzchunkindex=").append(dzchunkindex);
		sb.append(", dztotalfilesize=").append(dztotalfilesize);
		sb.append(", dzchunksize=").append(dzchunksize);
		sb.append(", dztotalchunkcount=").append(dztotalchunkcount);
		sb.append(", dzchunkbyteoffset=").append(dzchunkbyteoffset);
		sb.append(", contentType=").append(contentType);
		sb.append(", originalFileName=").append(originalFileName);
		sb.append("}");
		return sb.toString();
	}
}
