package com.friend_map.persistence_layer.services.message;

import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.dao.message.UserMessageDao;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.messages.UserMessage;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageService extends BaseService {

    public static final int first = 0;

    @Autowired
    UserMessageDao messageDao;

    public List<UserMessage> getMessagesByDialog(Dialog dialog, int first) {
        return messageDao.getMessagesByDialog(dialog, MessageStatus.ACTIVATE.toString(), max, first);
    }

    public List<UserMessage> getMessagesByDialogAndUser(Dialog dialog, User user, String status, int first) {
        return messageDao.getMessagesByDialogAndUser(dialog, user, status, first);
    }

    public List<UserMessage> findMessages(Dialog dialog, User user, String text) {
        return messageDao.findMessages(dialog, user, MessageStatus.ACTIVATE.toString(), text, max, first);
    }
}