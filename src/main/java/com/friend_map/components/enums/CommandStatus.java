package com.friend_map.components.enums;

public enum CommandStatus {

    OK,
    ERROR,
    USERNAME_EXIST,
    ACCESS_DENIED,
    BAD_REQUEST,
    NULL,
    SUCCESS,
    UPDATE,
    ANONYM,

    OTHER_FORMAT,
    EMPTY,

    CURRENT_USER,
    FRIEND,
    SUBSCRIBER,
    FOLLOW,
    REQUEST,
    IGNORE,
    IGNORED;

    CommandStatus() {}
}
