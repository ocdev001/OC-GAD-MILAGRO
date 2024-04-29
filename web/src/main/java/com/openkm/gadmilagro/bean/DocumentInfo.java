package com.openkm.gadmilagro.bean;

public class DocumentInfo {
    private final String name;
    private final String notes;
    private final String content;
    

    public DocumentInfo (String name,String notes,String content) {
        this.name = name;
        this.notes = notes;
        this.content = content;
    }
    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getContent() {
        return content;
    }
}

