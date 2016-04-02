package com.friend_map.persistence_layer.dao.friend;

import com.friend_map.persistence_layer.dao.BaseDao;
import com.friend_map.persistence_layer.entities.friend.Friend;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FriendDao extends BaseDao {

    /** ПОИСК ОТНОШЕНИЙ ПО ПОЛЬЗОВАТЕЛЮ */
    @SuppressWarnings("unchecked")
    public List<Friend> getByUser(User user) {
        Criteria criteria = getSession().createCriteria(Friend.class);

        Criterion rest2 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, user));
        Criterion rest1 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, user));

        criteria.add(Restrictions.or(rest1, rest2));
        criteria.setCacheable(true);
        return criteria.list();
    }

    /** ПОИСК ОТНОШЕНИЙ МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ */
    @SuppressWarnings("unchecked")
    public Friend getByUsers(User user, User anotherUser) {
        Criteria criteria = getSession().createCriteria(Friend.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, anotherUser));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, user));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, anotherUser));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);
        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.setCacheable(true);
        return (Friend) criteria.uniqueResult();
    }

    /** ПОИСК ОТНОШЕНИЙ ПО ПОЛЬЗОВАТЕЛЮ И СТАТУСУ */
    @SuppressWarnings("unchecked")
    public List<Friend> getByUserAndStatus(User user, String status) {
        Criteria criteria = getSession().createCriteria(Friend.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, user));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER_STATUS, status));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER_STATUS, status));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);

        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.setCacheable(true);

        return criteria.list();
    }
}
