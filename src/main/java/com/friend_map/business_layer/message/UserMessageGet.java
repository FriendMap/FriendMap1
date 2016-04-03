package com.friend_map.business_layer.message;

import com.friend_map.persistence_layer.entities.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.friend_map.components.beans.UserDetailsServiceImpl;
import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.message.DialogService;
import com.friend_map.persistence_layer.services.message.UserMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserMessageGet {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    UserMessageUtils userMessageUtils;
    @Autowired
    DialogService dialogService;

    public List<UserMessage> getUserMessages(UUID uuid) {
        return getUserMessages(uuid, 0);
    }

    /**
     * ПОЛУЧИТЬ СООБЩЕНИИ ОПРЕДЕЛЕННОГО ПОЛЬЗОВАТЕЛЯ
     * */
    public List<UserMessage> getUserMessages(UUID uuid, int first) {
        try {
            User current_user = userDetailsService.getCurrentUserMin();
            Dialog dialog = dialogService.findById(Dialog.class, uuid);
            MessageStatus messageStatus = userMessageUtils.getCurrentUserRole(current_user, dialog);
            if (messageStatus == MessageStatus.FIRST_USER) {
                return userMessageService.getMessagesByDialog(dialog, first);
            }
            if (messageStatus == MessageStatus.SECOND_USER) {
                return userMessageService.getMessagesByDialog(dialog, first);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * ПОЛУЧИТЬ ДИАЛОГИ ПОЛЬЗОВАТЕЛЯ
     * */
    public List<Dialog> getUserDialogs() {
        try {
            User current_user = userDetailsService.getCurrentUserMin();
            return dialogService.getUserDialogs(current_user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
