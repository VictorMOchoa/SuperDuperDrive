package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private long fileSize;
    private byte[] fileData;
    private Integer userId;

    public File(Integer fileId, String filename, String contenttype, long filesize, byte[] filedata, Integer userId) {
        this.fileId = fileId;
        this.fileName = filename;
        this.contentType = contenttype;
        this.fileSize = filesize;
        this.fileData = filedata;
        this.userId = userId;
    }

    public File() {
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getContentType() {
        return contentType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFileSize(long size) {
        this.fileSize = size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileName(String originalFilename) {
        this.fileName = originalFilename;
    }

    public void setFileData(byte[] bytes) {
        this.fileData = bytes;
    }

    public byte[] getFiledata() {
        return fileData;
    }

    public String getFilename() {
        return fileName;
    }
}