package net.buscacio.travelApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.buscacio.travelApi.entity.Destination;

import java.time.LocalDateTime;


public record DestinationRecord(

                               String place,
                               String description,
                               String image,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               LocalDateTime creationDate,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                               LocalDateTime lastupdateDate){
    public DestinationRecord(Destination destination) {
        this( destination.getPlace(), destination.getDescription(), destination.getImage(), destination.getCreationDate(), destination.getLastUpdateDate());
    }

}
