package com.friend_map.persistence_layer.dao.message;

import com.friend_map.persistence_layer.dao.BaseDao;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.messages.UserMessage;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DialogDao extends BaseDao {

    /** ПОЛУЧИТЬ ДИАЛОГИ ПОЛЬЗОВАТЕЛЯ */
    @SuppressWarnings("unchecked")
    public List<Dialog> getUserDialogs(User user, String status) {
        Criteria criteria = getSession().createCriteria(Dialog.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER, user));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER_STATUS, status));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER_STATUS, status));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);

        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.addOrder(Order.desc(UserMessage.DATE));
        criteria.setCacheable(true);
        return criteria.list();
    }

    /** ПОИСК ДИАЛОГА МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ */
    @SuppressWarnings("unchecked")
    public Dialog getDialogByUsers(User user, User anotherUser) {
        Criteria criteria = getSession().createCriteria(Dialog.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER, anotherUser));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER, user));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER, anotherUser));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);
        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.setCacheable(true);
        return (Dialog) criteria.uniqueResult();
    }
}
