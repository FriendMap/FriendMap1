package com.friend_map.components.exceptions;


import com.friend_map.components.enums.CommandStatus;

public class AccessDeniedException extends Exception {

    public AccessDeniedException(){
        super(CommandStatus.ACCESS_DENIED.toString() + " please log in as Admin or Editor");
    }
}
