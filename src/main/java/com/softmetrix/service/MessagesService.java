package com.softmetrix.service;

import com.softmetrix.model.DTO.MessageDTO;
import com.softmetrix.model.DTO.NewMessageForm;
import com.softmetrix.model.User;
import com.softmetrix.model.Message;
import java.util.List;
import java.util.Optional;

public interface MessagesService {
    public void sendMessage(User sender, NewMessageForm newMessageForm);
    public void markRead(Message msg);    
    public List<MessageDTO> getReceivedMessages(User user, Integer id);
    public List<MessageDTO> getSentMessages(User user);    
    public Optional<Message> getMessageById(Integer id);    
    public Long getCount(User user);
    public List<User> getAvailableReceievers(User user);
    public boolean canRead(User user, Integer msgId);
}
