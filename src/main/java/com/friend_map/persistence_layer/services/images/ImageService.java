package com.friend_map.persistence_layer.services.images;

import com.friend_map.business_layer.image.ImageUtils;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.persistence_layer.dao.BaseDao;
import com.friend_map.persistence_layer.entities.image.UserImage;
import com.friend_map.persistence_layer.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseService {

    @Autowired
    ImageUtils fileService;
    @Autowired
    BaseDao baseDao;

    /** УДАЛЯЕМ ФАЙЛЫ */
    public CommandStatus delete(UserImage image, String image_category) {
        try {
            fileService.deleteImages(image, image_category);
            baseDao.delete(image);
            return CommandStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }
}
