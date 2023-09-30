package net.buscacio.travelApi.repository;

import net.buscacio.travelApi.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, UUID> {
    Destination findDestinationByPlace(String place);
}
