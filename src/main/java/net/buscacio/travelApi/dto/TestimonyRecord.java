package net.buscacio.travelApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.buscacio.travelApi.entity.Destination;
import net.buscacio.travelApi.entity.Testimony;
import net.buscacio.travelApi.entity.User;


import java.time.LocalDateTime;


public record TestimonyRecord (

                               String testimony,
                               String image,
                              // User user,
                               Destination destination,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               LocalDateTime creationDate,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               LocalDateTime lastupdateDate){
    public TestimonyRecord(Testimony testimony) {
        this( testimony.getTestimony(), testimony.getImage(), testimony.getDestination(), testimony.getCreationDate(), testimony.getLastUpdateDate());
    }


}
