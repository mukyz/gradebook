package com.softmetrix.repository;

import com.softmetrix.model.Message;
import com.softmetrix.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessagesRepository extends JpaRepository<Message, Integer>{    
    public List<Message> findByReceiverAndIdGreaterThanOrderByTimeSentDesc(User user, Integer id);
    public List<Message> findBySenderOrderByTimeSentDesc(User user);
    
    @Query("select count(*) from Message as msg where msg.receiver.id=?1 and is_read = 0")
    public Long getUnreadCount(Integer userId);    
}
