package com.openkm.gadmilagro.util;

import com.openkm.sdk4j.bean.Document;

import java.util.List;

public class ListUtils {

	/*
	 * Returns the first document of the list.
	 */
	public static Document getFirstDocument(List<Document> docs) {
		if (docs != null && docs.size() > 0) {
			return docs.get(0);
		} else {
			return null;
		}
	}
}
