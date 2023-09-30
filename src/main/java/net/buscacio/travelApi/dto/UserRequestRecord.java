package net.buscacio.travelApi.dto;

import net.buscacio.travelApi.entity.User;

public record UserRequestRecord(String name, String email, String password){
    public UserRequestRecord(User user){
        this(user.getName(), user.getEmail(), user.getPassword());
    }
}
