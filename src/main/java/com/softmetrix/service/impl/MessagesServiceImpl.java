package com.softmetrix.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softmetrix.model.Message;
import com.softmetrix.model.Role;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import com.softmetrix.model.DTO.MessageDTO;
import com.softmetrix.model.DTO.NewMessageForm;
import com.softmetrix.repository.MessagesRepository;
import com.softmetrix.service.MessagesService;
import com.softmetrix.service.UserService;

@Service
public class MessagesServiceImpl implements MessagesService{
    @Autowired
    private MessagesRepository messagesRepository;
    
    @Autowired UserService userService;

    @Override
    public void sendMessage(User sender, NewMessageForm newMessageForm) {
        Message message = new Message();        
        User receiver = userService.findById(newMessageForm.getReceiverId()).orElse(null);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSubject(newMessageForm.getSubject());
        message.setMessage(newMessageForm.getMessage());
        
        messagesRepository.saveAndFlush(message);        
    }

    @Override
    public List<MessageDTO> getReceivedMessages(User user, Integer id) {
        
        List<Message> msgList= messagesRepository.findByReceiverAndIdGreaterThanOrderByTimeSentDesc(user, id);
        
        if(msgList == null) return null;
        
        List<MessageDTO> messages = new ArrayList<>();
        
        msgList.forEach((t) -> {
            messages.add(new MessageDTO(t));
        });
        
        return messages;
    }

    @Override
    public List<MessageDTO> getSentMessages(User user) {
        List<Message> msgList= messagesRepository.findBySenderOrderByTimeSentDesc(user);
        
        if(msgList == null)return null;
        
        List<MessageDTO> messages = new ArrayList<>();
        
        msgList.forEach((t) -> {
            messages.add(new MessageDTO(t));
        });
        
        return messages;
    }

    @Override
    public void markRead(Message msg) {        
        if(msg!=null){
            msg.setIsRead(Boolean.TRUE);
            messagesRepository.saveAndFlush(msg);
        }
    }

    @Override
    public Long getCount(User user) {
        return messagesRepository.getUnreadCount(user.getId());
    }

    @Override
    public Optional<Message> getMessageById(Integer id) {
        return messagesRepository.findById(id);
    }

    @Override
    public List<User> getAvailableReceievers(User user) {
        if(user.getRole().getRoleName().equals(Role.TEACHER)){
            if(user.getSchoolClass()!= null){
                List<Student> students = user.getSchoolClass().getStudents();
                List<User> parents = new ArrayList<>();

                students.forEach((student) -> {
                    if  (
                            student.getParent()!= null && 
                            !parents.contains(student.getParent())
                        ) 
                        parents.add(student.getParent());
                });

                if(parents.isEmpty()) return null;
                else return parents;
            } else return null;
        } else if(user.getRole().getRoleName().equals(Role.PARENT)){
            List<Student> children = user.getChildren();

            List<User> teachers = new ArrayList<>();

            children.forEach((child) -> { 
                if  (
                        child.getSchoolClass()!=null && 
                        child.getSchoolClass().getTeacher()!= null && 
                        !teachers.contains(child.getSchoolClass().getTeacher())
                    ) 
                    teachers.add(child.getSchoolClass().getTeacher());                    
            });

            if(teachers.isEmpty()) return null;
            else return teachers;                
        } else return null;
    }    

    @Override
    public boolean canRead(User user, Integer msgId) {
        Message msg = messagesRepository.findById(msgId).orElse(null);
        if(msg == null) return false;
        
        if(msg.getSender().equals(user) || msg.getReceiver().equals(user)) return true;
        else return false;
    }
}
