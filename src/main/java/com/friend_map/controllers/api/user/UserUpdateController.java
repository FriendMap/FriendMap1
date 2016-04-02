package com.friend_map.controllers.api.user;

import com.friend_map.business_layer.auth.PasswordEncoderService;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.beans.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.friend_map.business_layer.image.ProfileImageSaver;
import com.friend_map.components.enums.Roles;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.user.UserService;

@RestController
@RequestMapping(value = "user/update")
@PropertySource(value = { "classpath:app.properties" })
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
public class UserUpdateController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    ProfileImageSaver profileImageSaver;
    @Autowired
    PasswordEncoderService passwordEncoder;
    @Value("${profile.image.directory}")
    String imageCatalog;

    @RequestMapping(value = "password", method = RequestMethod.POST)
    public CommandStatusResult updatePassword(@RequestParam(value = "password") String password) {
        User current_user = userDetailsService.getCurrentUserMin();
        current_user.setPassword(passwordEncoder.encodingPassword(password));
        return new CommandStatusResult(userService.update(current_user));
    }

    @RequestMapping(value = "username", method = RequestMethod.POST)
    public CommandStatusResult updateUsername(@RequestParam(value = "username") String username) {
        User current_user = userDetailsService.getCurrentUserMin();
        current_user.setUsername(username);
        return new CommandStatusResult(userService.update(current_user));
    }

    @RequestMapping(value = "nickname", method = RequestMethod.POST)
    public CommandStatusResult updateNickname(@RequestParam(value = "nickname") String nickname) {
        User current_user = userDetailsService.getCurrentUserMin();
        current_user.setNickname(nickname);
        return new CommandStatusResult(userService.update(current_user));
    }

    @RequestMapping(value = "image", method = RequestMethod.POST)
    public CommandStatusResult updateImage(@RequestParam(value = "image") MultipartFile file) {
        User current_user = userDetailsService.getCurrentUserMin();
        return new CommandStatusResult(profileImageSaver.setImage(file, current_user, imageCatalog));
    }
}
