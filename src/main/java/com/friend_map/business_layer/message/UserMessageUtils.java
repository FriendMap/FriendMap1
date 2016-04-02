package com.friend_map.business_layer.message;

import com.friend_map.persistence_layer.entities.messages.UserMessage;
import org.springframework.stereotype.Service;
import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.user.User;

@Service
public class UserMessageUtils {

    /** Опредеяет роль пользователья в сообщении
     * Отправщик - sendUser
     * Получатель - getUser */
    public MessageStatus getCurrentUserRole(UserMessage userMessage, User currentUser) {
        User sendUser = userMessage.getGet_user();
        User getUser = userMessage.getSend_user();
        if (currentUser.getId().equals(sendUser.getId())) {
            return MessageStatus.SEND_USER;
        }
        if (currentUser.getId().equals(getUser.getId())) {
            return MessageStatus.GET_USER;
        }
        return MessageStatus.OTHER;
    }

    /** Опредеяет номер пользователья в диалоге
     * Первый пользователь - firstUser
     * Второй пользователь - secondUser */
    public MessageStatus getCurrentUserRole(User currentUser, Dialog dialog) {
        User firstUser = dialog.getFirst_user();
        User secondUser = dialog.getSecond_user();
        if (firstUser.getId().equals(currentUser.getId())) {
            return MessageStatus.FIRST_USER;
        }
        if (secondUser.getId().equals(currentUser.getId())) {
            return MessageStatus.SECOND_USER;
        }
        return MessageStatus.OTHER;
    }
}
