package net.buscacio.travelApi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.buscacio.travelApi.dto.TestimonyRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "testimonies")
@Entity(name = "Testimony")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Testimony {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String testimony;

    private String image;


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Destination destination;


    public Testimony(TestimonyRecord testimonyRecord) {
        this.testimony = testimonyRecord.testimony();
        this.image = testimonyRecord.image();
       // this.user = testimonyRecord.user();
        this.destination = testimonyRecord.destination();
        this.creationDate = testimonyRecord.creationDate();
        this.lastUpdateDate = testimonyRecord.lastupdateDate();

    }

    public void updateTestimony(TestimonyRecord testimonyRecord){
        setTestimony(testimonyRecord.testimony());
        setLastUpdateDate(LocalDateTime.now());
        setImage(testimonyRecord.image());
    }
}
