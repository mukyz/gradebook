package com.softmetrix.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name = "messages")
public class Message{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="subject")
    private String subject;
    
    @Column(name = "message")
    private String message;
    
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    @Column(name = "timestamp", insertable = false)    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSent;
    
    @Column(name = "is_read")
    private Boolean isRead;
    
    @PrePersist
    protected void onCreate(){
        if(this.isRead == null) isRead = Boolean.FALSE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }   

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}