package com.friend_map.persistence_layer.dao.user;

import com.friend_map.persistence_layer.dao.BaseDao;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class UserDao extends BaseDao {

    public User findById(UUID id) {
        return super.findById(User.class, id);
    }

    /** ПОИСК ПО ИМЕНИ ПОЛЛЬЗОВАТЕЛЯ (ЛОГИН) */
    public User findByUsername(String username) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq(User.USERNAME, username));
        criteria.setCacheable(true);
        return (User) criteria.uniqueResult();
    }

    /** ПОИСК ПОЛЬЗОВАТЕЛЕЙ ПО СОДЕРЖАНИЮ СИМВОЛА В НИКЕ */
    @SuppressWarnings("unchecked")
    public List<User> likeByNickname(String nickname, int max, int first) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.like(User.NICKNAME, nickname, MatchMode.ANYWHERE));
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
