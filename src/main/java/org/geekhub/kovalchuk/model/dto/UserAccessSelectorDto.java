package org.geekhub.kovalchuk.model.dto;

public class UserAccessSelectorDto {
    long userId;
    String username;
    String email;
    boolean isAdmin;
    boolean isBlocked;

    public UserAccessSelectorDto(long userId, String username, String email, boolean isAdmin, boolean isBlocked) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
