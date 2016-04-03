package com.friend_map.business_layer.message;

import com.friend_map.business_layer.friend.FriendStatusService;
import com.friend_map.components.enums.FriendStatus;
import com.friend_map.persistence_layer.entities.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.friend_map.components.beans.UserDetailsServiceImpl;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.message.DialogService;
import com.friend_map.persistence_layer.services.message.UserMessageService;

import java.util.Date;
import java.util.UUID;

@Service
public class UserMessageSend {

    @Autowired
    DialogService dialogService;
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    FriendStatusService friendStatusService;

    /** Отправка сообщения */
    public CommandStatus sendMessage(User getUser, String text) {
        User sendUser = userDetailsService.getCurrentUserMin();
        return sendMessage(sendUser, getUser, text);
    }

    /**
     * Перед отправкой проверяет id получателья и id отправителя,
     * если совпадают возвращает CURRENT_USER
     * потом определяет находится ли пользователи в дружеских отношениях, если нет вернет ACCESS_DENIED
     * потом ищет диалог пользователей, если их нет то создаст новый диалог
     * создает сообщение и отправляет сообщение */
    public CommandStatus sendMessage(User sendUser, User getUser, String text) {
        if (sendUser.getId().equals(getUser.getId())) {
            return CommandStatus.CURRENT_USER;
        }
        FriendStatus friendStatus = friendStatusService.friendStatus(sendUser, getUser);
        switch (friendStatus) {
            case FRIEND:
                Dialog dialog = dialogService.getDialogByUsers(sendUser, getUser);
                if (dialog != null) {
                    UUID uuid;
                    try {
                        uuid = dialogService.save(new Dialog(sendUser, getUser));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return CommandStatus.ERROR;
                    }
                    dialog.setId(uuid);
                    dialog.setDate(new Date());
                    dialog.setFirst_user_status(MessageStatus.ACTIVATE.toString());
                    dialog.setSecond_user_status(MessageStatus.ACTIVATE.toString());
                    dialog.setFirst_user(sendUser);
                    dialog.setSecond_user(getUser);
                }
                return userMessageService.persist(createMessage(sendUser, getUser, dialog, text));
            default:
                return CommandStatus.ACCESS_DENIED;
        }
    }

    /** Создает сообщение между пользователями */
    public UserMessage createMessage(User sendUser, User getUser, Dialog dialog, String text) {
        UserMessage userMessage = new UserMessage();
        userMessage.setDate(new Date());
        userMessage.setDialog(dialog);
        userMessage.setGet_user(getUser);
        userMessage.setSend_user(sendUser);
        userMessage.setGet_user_status(MessageStatus.ACTIVATE.toString());
        userMessage.setSend_user_status(MessageStatus.ACTIVATE.toString());
        userMessage.setStatus(MessageStatus.SEND.toString());
        userMessage.setText(text);
        return userMessage;
    }
}
