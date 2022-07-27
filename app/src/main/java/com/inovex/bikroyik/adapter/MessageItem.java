package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 11/14/2018.
 */

public class MessageItem {
    String messageSender;
    String messageDate;
    String messageTime;
    String messageTitle;
    String message;
    String messageSenderId;

    public MessageItem() {
    }

    public MessageItem(String messageSender, String messageDate, String messageTime, String messageTitle, String message, String messageSenderId) {
        this.messageSender = messageSender;
        this.messageDate = messageDate;
        this.messageTime = messageTime;
        this.messageTitle = messageTitle;
        this.message = message;
        this.messageSenderId = messageSenderId;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageSenderId() {
        return messageSenderId;
    }

    public void setMessageSenderId(String messageSenderId) {
        this.messageSenderId = messageSenderId;
    }
}
