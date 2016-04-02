package com.friend_map.persistence_layer.entities.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.friend_map.components.enums.MessageStatus;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/** ДИАЛОГИ МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ */
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "dialog_table")
public class Dialog {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String FIRST_USER = "first_user";
    public static final String SECOND_USER = "second_user";
    public static final String FIRST_USER_STATUS = "first_user_status";
    public static final String SECOND_USER_STATUS = "second_user_status";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    /** СТАТУС УДАЛЕНИЯ */
    @JsonIgnore
    @Column(name = FIRST_USER_STATUS, nullable = false)
    private String first_user_status;

    /** СТАТУС УДАЛЕНИЯ */
    @JsonIgnore
    @Column(name = SECOND_USER_STATUS, nullable = false)
    private String second_user_status;

    /** ДАТА СОЗДАНИЯ БЕСЕДЫ */
    @Column(name = DATE, unique = false, nullable = false)
    private Date date;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = FIRST_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User first_user;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = SECOND_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User second_user;

    @JsonBackReference
    @OneToMany(mappedBy = UserMessage.DIALOG, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserMessage> messages = new ArrayList<>();

    public Dialog(User first_user, User second_user) {
        this.first_user = first_user;
        this.second_user = second_user;
        this.date = new Date();
        this.first_user_status = MessageStatus.ACTIVATE.toString();
        this.second_user_status = MessageStatus.ACTIVATE.toString();
    }

    public Dialog() {
    }

    public Dialog(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirst_user_status() {
        return first_user_status;
    }

    public void setFirst_user_status(String first_user_status) {
        this.first_user_status = first_user_status;
    }

    public String getSecond_user_status() {
        return second_user_status;
    }

    public void setSecond_user_status(String second_user_status) {
        this.second_user_status = second_user_status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getFirst_user() {
        return first_user;
    }

    public void setFirst_user(User first_user) {
        this.first_user = first_user;
    }

    public User getSecond_user() {
        return second_user;
    }

    public void setSecond_user(User second_user) {
        this.second_user = second_user;
    }

    public List<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<UserMessage> messages) {
        this.messages = messages;
    }
}
