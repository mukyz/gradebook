package com.softmetrix.model.DTO;

import com.softmetrix.model.Message;
import java.util.Date;

public class MessageDTO{    
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    
    private String senderName;
    private String receiverName;
    
    private String subject;
    private String message;
    private Date timestamp;
    private Boolean isRead;
    
    public MessageDTO(){
        
    }
    
    public MessageDTO(Message msg){
        this.id = msg.getId();
        if(msg.getSender() != null){
            this.senderId = msg.getSender().getId();
            this.senderName = msg.getSender().getUsername();
        }
        
        if(msg.getReceiver() != null){
            this.receiverId = msg.getReceiver().getId();
            this.receiverName = msg.getReceiver().getUsername();        
        }
        this.subject = msg.getSubject();
        this.message = msg.getMessage();        
        this.timestamp = msg.getTimeSent();
        this.isRead = msg.getIsRead();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}