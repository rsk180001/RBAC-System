package com.ideas.websecurity.dto;

public class FileMetadata {
    private String fileName;
    private String path;

    public FileMetadata(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }
}
