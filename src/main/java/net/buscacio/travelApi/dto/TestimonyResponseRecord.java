package net.buscacio.travelApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.buscacio.travelApi.entity.Testimony;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestimonyResponseRecord(UUID testimonyId,

                                      String testimony,
                                      String image,
                                      String place,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                                     LocalDateTime creationDate,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                                     LocalDateTime lastupdateDate) {
    public TestimonyResponseRecord(Testimony testimony) {
        this(testimony.getId(), testimony.getTestimony(), testimony.getImage(), testimony.getDestination().getPlace(), testimony.getCreationDate(), testimony.getLastUpdateDate());
    }


}
