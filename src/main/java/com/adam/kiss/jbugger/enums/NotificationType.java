package com.adam.kiss.jbugger.enums;

public enum NotificationType {
    WELCOME_NEW_USER,
    USER_UPDATED,
    USER_DELETED,
    BUG_UPDATED,
    BUG_CLOSED,
    BUG_STATUS_UPDATED,
    USER_DEACTIVATED,
    USER_MENTIONED;


    @Override
    public String toString() {
        return this.name().replace("_"," ");
    }
}
