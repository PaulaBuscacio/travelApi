package net.buscacio.travelApi.service;

import jakarta.transaction.Transactional;
import net.buscacio.travelApi.entity.Testimony;
import net.buscacio.travelApi.exception.ResourceException;
import net.buscacio.travelApi.repository.TestimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestimonyService {

    @Autowired
    TestimonyRepository testimonyRepository;


    public Testimony saveTestimony(Testimony testimony) {
        return testimonyRepository.save(testimony);

    }

    public Page<Testimony> findAll(Pageable pageable) {
        return testimonyRepository.findAll(pageable);
    }

    public Testimony findById(UUID depoimentId) {
        return testimonyRepository.findById(depoimentId).orElseThrow(() ->
                new ResourceException("Testimony not found!"));
    }

    @Transactional
    public void deleteDepoiment(UUID testimonyId) {
        findById(testimonyId);
        testimonyRepository.deleteById(testimonyId);
    }
}
