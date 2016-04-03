package com.friend_map.persistence_layer.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.friend_map.persistence_layer.entities.admin.ClientKey;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "user_data_table")
public class UserData {

    public static final String ID = "id";
    public static final String KEY = "clientKey";
    public static final String NICKNAME = "nickname";
    public static final String USER = "user";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    /** НИК */
    @Column(name = NICKNAME, unique = true, nullable = false, length = 32)
    private String nickname;

    /**
     * ПОЛЬЗОВАТЕЛЬ TODO: ДОЛЖНО БЫТЬ 1 К 1, НЕ ПОСТАВИЛ ИЗ ЗА ПРОБЛЕМ С КЭШИРОВАНИЕМ
     *  */
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User user;

    /** КЛИЕНТ */
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = KEY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private ClientKey clientKey;
}
