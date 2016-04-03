package com.friend_map.persistence_layer.entities.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.friend_map.persistence_layer.entities.user.UserData;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "client_keys_table")
public class ClientKey {

    public static final String ID = "id";
    public static final String KEY = "client_key";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    @Column(name = KEY, nullable = false)
    private String clientKey;

    @Column(name = NAME, nullable = false)
    private String name;

    @Column(name = EMAIL, nullable = false)
    private String emailAddress;

    /** ПОЛЬЗОВАТЕЛЬ */
    @JsonIgnore
    @OneToMany(mappedBy = UserData.KEY, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserData> users = new ArrayList<>();

}
