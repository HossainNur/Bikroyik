package com.inovex.bikroyik.model;

import java.io.File;

public class NotificationFile {


    public static final int NEW_FILE = 0;
    public static final int DOWNLODED_FILE = 1;

    String fileId;
    String fileName;
    String fileDate;
    String fileType;
    boolean isDownloaded;
    String fileSize;
    String fileLocation;
    String description;
    File file;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    String downloadUrl;

    public NotificationFile() {
    }

    public NotificationFile(String fileId, String fileName, String fileDate, String fileType, boolean isDownloaded, String fileSize, String fileLocation, String description) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDate = fileDate;
        this.fileType = fileType;
        this.isDownloaded = isDownloaded;
        this.fileSize = fileSize;
        this.fileLocation = fileLocation;
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
