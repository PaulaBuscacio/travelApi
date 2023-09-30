package net.buscacio.travelApi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import net.buscacio.travelApi.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRecord(UUID userId,
                         @NotBlank(message = "Name can't be blank")
                         String name,
                         @Email(message = "Email is not valid")
                         @Column(unique = true)
                         @NotBlank(message = "Email can't be blank")
                         String email,
                         LocalDateTime creationDate,
                         LocalDateTime lastUpdateDate
                         ){
    public UserRecord(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getCreationDate(), user.getLastUpdateDate());
    }
}
