package com.friend_map.business_layer.image;

import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.FileTypes;
import com.friend_map.persistence_layer.entities.image.ProfileImage;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.images.user.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource(value = { "classpath:app.properties" })
public class ProfileImageSaver {

    @Autowired
    ProfileImageService profileImageService;
    @Autowired
    ImageUtils imageUtils;
    @Value("${image.directory}")
    String imageCatalog;

    ProfileImage profileImage;
    String image_category;
    String firstDir;
    String secondDir;
    String fullDir;
    String fullImageName;
    String miniatureName;
    FileTypes format;

    BufferedImage img;

    public CommandStatus setImage(MultipartFile multipartFile, User user, String category) {
        image_category = category;
        /** ПРОВЕРКА НА NULL */
        if (multipartFile == null) {
            return CommandStatus.EMPTY;
        }

        /** ЧИТАЕМ КАК ИЗОБРАЖЕНИЕ */
        try {
            img = ImageIO.read(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return CommandStatus.OTHER_FORMAT;
        }
        /** ПРОВЕРЯЕМ НА ФОРМАТ ФАЙЛА */
        format = imageUtils.getType(multipartFile.getOriginalFilename());
        if (format == FileTypes.OTHER) {
            return CommandStatus.OTHER_FORMAT;
        }
        /** СООТВЕТСВУЮЩИ ИНИЦИЛИЗИРУЕМ ПЕРЕМЕННУЮ */
        if (user.getProfileImage() != null) {
            if (imageUtils.deleteImages(user.getProfileImage(), image_category) == CommandStatus.ERROR) {
                return CommandStatus.ERROR;
            }
            profileImage = user.getProfileImage();
        } else {
            profileImage = new ProfileImage();
        }

        /** 1 ПАПКА ПО 1 СИМВОЛУ В ID */
        firstDir = user.getId().toString().substring(1, 2) + "/";
        /** 2 ПАПКА ПО 2 СИМВОЛУ В ID */
        secondDir = user.getId().toString().substring(0, 1) + "/";
        /** ПОЛНЫЙ ПУТЬ */
        fullDir = image_category + firstDir + secondDir + user.getId() + "/" ;
        /** СОЗДАЕМ ИМЯ ИЗОБРАЖЕНИИ */
        fullImageName = UUID.randomUUID().toString() + "." + format.toString();
        /** СОЗДАЕМ ИМЯ МИНИАТЮРЫ */
        miniatureName = UUID.randomUUID().toString() + "." + format.toString();
        try {
            /** ЧИТАЕМ КАК БАЙТЫ */
            byte[] bytes = multipartFile.getBytes();
            /** СОЗДАЕМ ПОЛНОЕ ИЗОБРАЖЕНИЕ */
            File full = new File(imageCatalog + fullDir + fullImageName);
            /** СОЗДАЕМ МИНИАТЮРУ */
            File miniature = new File(imageCatalog + fullDir + miniatureName);

            /** ЗАПИСЫВАЕМ МИНИТЮРУ НА ДИСК (ПОЛНОЕ ИЗОБРАЖЕНИЕ) */
            if (imageUtils.save(bytes, miniature) == CommandStatus.ERROR) {
                return CommandStatus.ERROR;
            }
            /** МЕНЯЕМ РАЗМЕР СОХРАНЕННОГО ФАЙЛА */
            imageUtils.processImage(100, 100, img, miniature, format);
            if (imageUtils.save(bytes, full) == CommandStatus.ERROR) {
                return CommandStatus.ERROR;
            }

            profileImage.setUser(user);
            profileImage.setDate(new Date());
            profileImage.setImage_category(image_category);
            profileImage.setFull_directory(fullDir);
            profileImage.setFull_image(fullImageName);
            profileImage.setMiniature(miniatureName);
            /** ПРОВЕРКА НА ДОБАВЛЕНИЕ ИЛИ ОБНОВЛЕНИЕ */
            if (user.getProfileImage() == null) {
                return profileImageService.persist(profileImage);
            } else {
                profileImage.setId(user.getProfileImage().getId());
                return profileImageService.update(profileImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }

    public ProfileImageSaver() {
    }
}
