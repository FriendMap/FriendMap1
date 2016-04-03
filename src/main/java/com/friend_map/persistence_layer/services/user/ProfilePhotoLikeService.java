package com.friend_map.persistence_layer.services.user;

import com.friend_map.components.converter.JsonConverter;
import com.friend_map.persistence_layer.pojo.user.UserPhotoLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProfilePhotoLikeService {

    private static final String post_like_key = "post_like";

    /** РАБОТАЕМ С РЕДИС */
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    JsonConverter jsonConverter;

    /** СНАЧАЛА ПОЛУЧАЕТ ЛАЙКИ ПОСТА
     * ПОТОМ ПРОВЕРЯЕТ ПРОГОЛОСОВАЛ ЛИ ПОЛЬЗОВАТЕЛЬ ЕСЛИ НЕТ ТО ДОБАВЛЯЕТ, ИНАЧЕ УДАЛЯЕТ */
    @Async
    @SuppressWarnings("unchecked")
    public void vote(String id, UserPhotoLike userPostLike) throws Exception {
        String likes = redisTemplate.opsForHash().get(post_like_key, id).toString();
        Map<String, String> newLikes = jsonConverter.jsonToMap(likes);

        String vote = newLikes.get(userPostLike.getUser());

        if (vote != null) {
            newLikes.remove(userPostLike.getUser());
            if (!vote.equals(userPostLike.getVote())) {
                newLikes.put(userPostLike.getUser(), userPostLike.getVote());
            }
        } else {
            newLikes.put(userPostLike.getUser(), userPostLike.getVote());
        }

        redisTemplate.opsForHash().put(post_like_key, id, jsonConverter.mapToJson(newLikes));
    }

    /** УДАЛЯЕТ ЛАЙКИ ПОСТА */
    @Async
    @SuppressWarnings("unchecked")
    public void delete(String id) throws Exception {
        redisTemplate.opsForHash().delete(post_like_key, id);
    }

    @SuppressWarnings("unchecked")
    public List<UserPhotoLike> findByPostId(String post_id) {
        try {
            String likes = redisTemplate.opsForHash().get(post_like_key, post_id).toString();
            Map<String, String> newMap = jsonConverter.jsonToMap(likes);
            List<UserPhotoLike> likeList = new ArrayList<>();
            for (Map.Entry<String, String> map : newMap.entrySet()) {
                likeList.add(new UserPhotoLike(map.getKey(), map.getValue()));
            }
            return likeList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
