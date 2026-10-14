package com.greenfield.smartcommunity.domain.request;

import java.time.LocalDateTime;

public class Attachment {
    private final String attachmentId;
    private final String filePath;
    private final String fileType;
    private final LocalDateTime uploadedAt;

    public Attachment(String attachmentId, String filePath, String fileType) {
        this.attachmentId = attachmentId;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadedAt = LocalDateTime.now();
    }

    public String getAttachmentId() { return attachmentId; }
    public String getFilePath() { return filePath; }
    public String getFileType() { return fileType; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
}