package net.buscacio.travelApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.buscacio.travelApi.entity.Destination;

import java.time.LocalDateTime;
import java.util.UUID;


public record DestinationResponseRecord(
                               UUID id,
                               String place,
                               String description,
                               String image,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               LocalDateTime creationDate,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               LocalDateTime lastupdateDate){
    public DestinationResponseRecord(Destination destination) {
        this( destination.getId(),destination.getPlace(), destination.getDescription(), destination.getImage(), destination.getCreationDate(), destination.getLastUpdateDate());
    }

}
