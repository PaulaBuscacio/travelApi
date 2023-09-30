package net.buscacio.travelApi.service;


import jakarta.transaction.Transactional;
import net.buscacio.travelApi.entity.Destination;
import net.buscacio.travelApi.exception.ResourceException;
import net.buscacio.travelApi.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DestinationService {

    @Autowired
    DestinationRepository destinationRepository;


    public Destination saveDestination(Destination destination) {
        return destinationRepository.save(destination);

    }

    public Page<Destination> findAll(Pageable pageable) {
        return destinationRepository.findAll(pageable);
    }

    public Destination findById(UUID destinationId) {
        return destinationRepository.findById(destinationId).orElseThrow(() ->
                new ResourceException("Destination not found!"));
    }

    public Destination findByPlace(String place) {
          return destinationRepository.findDestinationByPlace(place);

    }

    @Transactional
    public void deleteDestination(UUID destinationId) {
         findById(destinationId);
        destinationRepository.deleteById(destinationId);
    }
}
