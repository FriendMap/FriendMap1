package com.friend_map.business_layer.message;

import com.friend_map.components.beans.UserDetailsServiceImpl;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.messages.UserMessage;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.message.DialogService;
import com.friend_map.persistence_layer.services.message.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserMessageDelete {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    UserMessageUtils messageUtils;
    @Autowired
    DialogService dialogService;

    public CommandStatus deleteMessage(UUID message_id) {
        return deleteMessage(message_id, userDetailsService.getCurrentUserMin());
    }

    public CommandStatus deleteDialog(UUID dialog_id) {
        return deleteDialog(dialog_id, userDetailsService.getCurrentUserMin());
    }

    /**
     * ПОМЕТИТЬ ДИАЛОГ КАК УДАЛЕННЫЙ
     * ЕСЛИ У ОБОИХ ПОМЕЧЕН КАК УДАЛЕННЫЙ УДАЯЛЯЕТСЯ С БД
     * */
    public CommandStatus deleteDialog(UUID uuid, User user) {
        Dialog dialog;
        try {
            dialog = dialogService.findById(Dialog.class, uuid);
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
        if (dialog == null) {
            return CommandStatus.NULL;
        }
        switch (messageUtils.getCurrentUserRole(user, dialog)) {
            case FIRST_USER:
                dialog.setFirst_user_status(MessageStatus.DELETE.toString());
                if (dialog.getSecond_user_status().equals(MessageStatus.DELETE.toString())) {
                    return dialogService.delete(dialog);
                }
                return dialogService.update(dialog);
            case SECOND_USER:
                dialog.setSecond_user_status(MessageStatus.DELETE.toString());
                if (dialog.getFirst_user_status().equals(MessageStatus.DELETE.toString())) {
                    return dialogService.delete(dialog);
                }
                return dialogService.update(dialog);
        }
        return CommandStatus.ERROR;
    }

    /**
     * ПОМЕТИТЬ СООБЩЕНИЯ КАК УДАЛЕННЫЙ
     * ЕСЛИ У ОБОИХ ПОМЕЧЕН КАК УДАЛЕННЫЙ УДАЛЯЕТСЯ С БД
     * */
    public CommandStatus deleteMessage(UUID uuid, User user) {
        try {
            UserMessage message = userMessageService.findById(UserMessage.class, uuid);
            if (message == null) {
                return CommandStatus.NULL;
            }
            switch (messageUtils.getCurrentUserRole(message, user)) {
                case OTHER:
                    return CommandStatus.ACCESS_DENIED;
            }

            if (message.getGet_user_status().equals(MessageStatus.DELETE.toString())) {
                if (message.getSend_user_status().equals(MessageStatus.DELETE.toString())) {
                    return userMessageService.delete(message);
                }
                message.setGet_user_status(MessageStatus.DELETE.toString());
                return userMessageService.update(message);
            }
            if (message.getSend_user_status().equals(MessageStatus.DELETE.toString())) {
                if (message.getGet_user_status().equals(MessageStatus.DELETE.toString())) {
                    return userMessageService.delete(message);
                }
                message.setSend_user_status(MessageStatus.DELETE.toString());
                return userMessageService.update(message);
            }
            return CommandStatus.ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }
}
