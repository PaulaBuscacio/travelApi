package net.buscacio.travelApi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.buscacio.travelApi.dto.DestinationRecord;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "destinations")
@Entity(name = "Destination")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    @Column(unique = true)
    private String place;

    private String description;

    private String image;
    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Testimony> testimonies;


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;



    public Destination(DestinationRecord destinationRecord) {
        this.place = destinationRecord.place();
        this.description = destinationRecord.description();
        this.image = destinationRecord.image();
        this.creationDate = destinationRecord.creationDate();
        this.lastUpdateDate = destinationRecord.lastupdateDate();

    }

    public void updateDestination(DestinationRecord destinationRecord){
        setPlace(destinationRecord.place());
        setDescription(destinationRecord.description());
        setLastUpdateDate(LocalDateTime.now());
        setImage(destinationRecord.image());
    }
}
