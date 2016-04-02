package com.friend_map.persistence_layer.entities.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "message_table")
public class UserMessage {

    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String DATE = "date";
    public static final String STATUS = "status";
    public static final String SEND_USER_STATUS = "send_user_status";
    public static final String GET_USER_STATUS = "get_user_status";
    public static final String SEND_USER = "send_user";
    public static final String GET_USER = "get_user";
    public static final String DIALOG = "dialog";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    /** ТЕКСТ СООБЩЕНИЯ */
    @Column(name = TEXT, unique = false, nullable = false)
    private String text;

    /** ДАТА ОТПРАВКИ СООБЩЕНИЯ */
    @Column(name = DATE, unique = false, nullable = false)
    private Date date;

    /** СТАТУС СООБЩЕНИЯ (ПРОЧИТАЛ, ДОСТАВЛЕН ИТП) */
    @Column(name = STATUS, unique = false, nullable = false)
    private String status;

    /** СТАТУС УДАЛЕНИЯ */
    @JsonIgnore
    @Column(name = SEND_USER_STATUS, nullable = false)
    private String send_user_status;

    /** СТАТУС УДАЛЕНИЯ */
    @JsonIgnore
    @Column(name = GET_USER_STATUS, nullable = false)
    private String get_user_status;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = SEND_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User send_user;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = GET_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User get_user;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = DIALOG)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Dialog dialog;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSend_user_status() {
        return send_user_status;
    }

    public void setSend_user_status(String send_user_status) {
        this.send_user_status = send_user_status;
    }

    public String getGet_user_status() {
        return get_user_status;
    }

    public void setGet_user_status(String get_user_status) {
        this.get_user_status = get_user_status;
    }

    public User getSend_user() {
        return send_user;
    }

    public void setSend_user(User send_user) {
        this.send_user = send_user;
    }

    public User getGet_user() {
        return get_user;
    }

    public void setGet_user(User get_user) {
        this.get_user = get_user;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
