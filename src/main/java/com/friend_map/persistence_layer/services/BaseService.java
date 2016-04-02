package com.friend_map.persistence_layer.services;

import com.friend_map.components.enums.CommandStatus;
import com.friend_map.persistence_layer.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BaseService {

    @Autowired
    private BaseDao baseDao;
    public static final int max = 30;
    public static final int number = 0;

    public <T> T findById(Class<T> type, final UUID id) {
        return baseDao.findById(type, id);
    }

    public <T> List<T> getAll(Class<T> type) {
        return baseDao.getAll(type);
    }

    public CommandStatus persist(Object object) {
        try {
            baseDao.persist(object);
            return CommandStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }

    public UUID save(Object object) throws Exception {
        return baseDao.save(object);
    }

    public CommandStatus update(Object object) {
        try {
            baseDao.update(object);
            return CommandStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }

    public CommandStatus merge(Object object) {
        try {
            baseDao.merge(object);
            return CommandStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }

    public CommandStatus delete(Object object) {
        try {
            baseDao.delete(object);
            return CommandStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }
}
