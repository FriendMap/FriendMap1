package com.friend_map.persistence_layer.dao.message;

import com.friend_map.persistence_layer.dao.BaseDao;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.messages.UserMessage;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserMessageDao extends BaseDao {

    public static final int MAX_RESULT = 30;

    /** ПОИСК СООБЩЕНИЙ ПО ПОЛЬЗОВАТЕЛЮ И ДИАЛОГУ (PS: НЕЗНАЮ ЗАЧЕМ НАПИСАЛ xD */
    @SuppressWarnings("unchecked")
    public List<UserMessage> getMessagesByDialogAndUser(Dialog dialog, User user, String status, int first) {
        Criteria criteria = getSession().createCriteria(UserMessage.class);

        criteria.add(Restrictions.eq(UserMessage.DIALOG, dialog)).setCacheable(true);

        Criterion getUserRest = Restrictions.and(Restrictions.eq(UserMessage.GET_USER, user));
        Criterion sendUserRest = Restrictions.and(Restrictions.eq(UserMessage.SEND_USER, user));

        Criterion getUserStatus = Restrictions.and(Restrictions.eq(UserMessage.GET_USER_STATUS, status));
        Criterion sendUserStatus = Restrictions.and(Restrictions.eq(UserMessage.SEND_USER_STATUS, status));

        LogicalExpression getUserExpression = Restrictions.and(getUserRest, getUserStatus);
        LogicalExpression sendUserExpression = Restrictions.and(sendUserRest, sendUserStatus);

        LogicalExpression expression = Restrictions.or(getUserExpression, sendUserExpression);

        criteria.add(expression).setCacheable(true);
        criteria.addOrder(Order.desc(UserMessage.DATE));
        criteria.setMaxResults(MAX_RESULT);
        criteria.setFirstResult(first);
        criteria.setCacheable(true);
        return criteria.list();
    }

    /** ПОИСК СООБЩЕНИЯ В ДИАЛОГЕ ПО СОДЕРЖАНИЮ СЛОВА В ТЕКСТЕ */
    @SuppressWarnings("unchecked")
    public List<UserMessage> findMessages(Dialog dialog, User user, String status, String text, int max, int first) {
        Criteria criteria = getSession().createCriteria(UserMessage.class);

        criteria.add(Restrictions.like(UserMessage.TEXT, text, MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq(UserMessage.DIALOG, dialog));

        Criterion getUserRest = Restrictions.and(Restrictions.eq(UserMessage.GET_USER, user));
        Criterion sendUserRest = Restrictions.and(Restrictions.eq(UserMessage.SEND_USER, user));

        Criterion getUserStatus = Restrictions.and(Restrictions.eq(UserMessage.GET_USER_STATUS, status));
        Criterion sendUserStatus = Restrictions.and(Restrictions.eq(UserMessage.SEND_USER_STATUS, status));

        LogicalExpression getUserExpression = Restrictions.and(getUserRest, getUserStatus);
        LogicalExpression sendUserExpression = Restrictions.and(sendUserRest, sendUserStatus);

        LogicalExpression expression = Restrictions.or(getUserExpression, sendUserExpression);

        criteria.add(expression);
        criteria.addOrder(Order.desc(UserMessage.DATE));
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        criteria.setCacheable(true);

        return criteria.list();
    }

    /** ПОИСК СООБЩЕНИЙ ДИАЛОГА */
    @SuppressWarnings("unchecked")
    public List<UserMessage> getMessagesByDialog(Dialog dialog, String status, int max, int first) {
        Criteria criteria = getSession().createCriteria(UserMessage.class);

        criteria.add(Restrictions.eq(UserMessage.DIALOG, dialog));

        Criterion getUserStatus = Restrictions.and(Restrictions.eq(UserMessage.GET_USER_STATUS, status));
        Criterion sendUserStatus = Restrictions.and(Restrictions.eq(UserMessage.SEND_USER_STATUS, status));

        LogicalExpression getUserExpression = Restrictions.or(sendUserStatus, getUserStatus);

        criteria.add(getUserExpression);
        criteria.addOrder(Order.desc(UserMessage.DATE));
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        criteria.setCacheable(true);

        return criteria.list();
    }

}
