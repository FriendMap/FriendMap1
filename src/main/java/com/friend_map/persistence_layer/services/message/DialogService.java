package com.friend_map.persistence_layer.services.message;

import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.dao.message.DialogDao;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialogService extends BaseService {

    @Autowired
    DialogDao dialogDao;

    public List<Dialog> getUserDialogs(User user, String status) {
        return dialogDao.getUserDialogs(user, status);
    }

    public List<Dialog> getUserDialogs(User user) {
        return dialogDao.getUserDialogs(user, MessageStatus.ACTIVATE.toString());
    }

    public Dialog getDialogByUsers(User user, User anotherUser) {
        return dialogDao.getDialogByUsers(user, anotherUser);
    }
}
