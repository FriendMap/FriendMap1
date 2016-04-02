package com.friend_map.components;


import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.FriendStatus;

public class CommandStatusResult {

    public String result;

    public String status = "OK";

    public CommandStatusResult(String result) {
        this.result = result;
    }

    public CommandStatusResult() {
    }

    public CommandStatusResult(CommandStatus result) {
        this.result = result.toString();
    }

    public CommandStatusResult(FriendStatus status) {
        this.result = status.toString();
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setResult(CommandStatus result) {
        this.result = result.toString();
    }
}
