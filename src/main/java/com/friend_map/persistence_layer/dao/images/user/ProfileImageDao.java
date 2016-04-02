package com.friend_map.persistence_layer.dao.images.user;

import com.friend_map.persistence_layer.dao.BaseDao;
import com.friend_map.persistence_layer.entities.image.ProfileImage;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProfileImageDao extends BaseDao {

    /** ПОИСК ФОТОГРАФИИ ОТПРЕДЕЛЕННОГО ПОЛЬЗОВАТЕЛЯ */
    public ProfileImage findByUser(User user) {
        Criteria criteria = getSession().createCriteria(ProfileImage.class);
        criteria.add(Restrictions.eq(ProfileImage.USER, user));
        criteria.setCacheable(true);
        return (ProfileImage) criteria.uniqueResult();
    }
}
