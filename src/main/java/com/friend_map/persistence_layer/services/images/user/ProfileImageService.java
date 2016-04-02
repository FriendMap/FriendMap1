package com.friend_map.persistence_layer.services.images.user;

import com.friend_map.persistence_layer.dao.images.user.ProfileImageDao;
import com.friend_map.persistence_layer.entities.image.ProfileImage;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@PropertySource(value = { "classpath:app.properties" })
public class ProfileImageService extends BaseService {

    @Autowired
    ProfileImageDao profileImageDao;

    @Value("${profile.image.directory}")
    private String image_category;

    public ProfileImage findByUser(User user) {
        return profileImageDao.findByUser(user);
    }

    public ProfileImage findById(UUID uuid) {
        return profileImageDao.findById(ProfileImage.class, uuid);
    }

}
