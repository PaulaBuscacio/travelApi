package net.buscacio.travelApi.controller;

import jakarta.persistence.NonUniqueResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import net.buscacio.travelApi.dto.DestinationRecord;
import net.buscacio.travelApi.dto.DestinationResponseRecord;
import net.buscacio.travelApi.entity.Destination;
import net.buscacio.travelApi.exception.ResourceException;
import net.buscacio.travelApi.service.DestinationService;
import net.buscacio.travelApi.service.UserService;
import net.buscacio.travelApi.service.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/destinations")
@Validated
public class DestinationController {

    @Autowired
    DestinationService destinationService;
    @Autowired
    UserService userService;
    @Autowired
    UploadFileUtils uploadFileUtils;

    @Value("${images.destination}")
    String PATH;



    @PostMapping
    public  ResponseEntity<DestinationResponseRecord> createDestination(@RequestParam("file") MultipartFile file, @Valid @RequestParam(value = "place") @NotBlank(message = "place can not be empty") String place, @RequestParam("description") String description, UriComponentsBuilder uriComponentsBuilder) {
        String fileName = null;
        try {
        fileName = uploadFileUtils.uploadFile(file, PATH);
       }catch (IOException e) {
            e.getMessage();
       }
        if(Objects.equals(destinationService.findByPlace(place).getPlace(), place))  throw new NonUniqueResultException();

        DestinationRecord destinationRecord = new DestinationRecord(place, description, fileName, LocalDateTime.now(), LocalDateTime.now());
        var destination = new Destination(destinationRecord);
        destinationService.saveDestination(destination);
        var uri = uriComponentsBuilder.path("destinations/{destinationId}").buildAndExpand(destination.getId()).toUri();

        return ResponseEntity.created(uri).body(new DestinationResponseRecord(destination));

    }

    @GetMapping
    public ResponseEntity<Page<DestinationResponseRecord>> getDestinations(@PageableDefault(size=3,sort={"creationDate"}) Pageable pageable){
        Page<DestinationResponseRecord> page = destinationService.findAll(pageable).map(DestinationResponseRecord::new);
        return new ResponseEntity(page, HttpStatus.OK);

    }

    @GetMapping("/{destinationId}")
    public ResponseEntity<DestinationResponseRecord> getDestinationById(@PathVariable UUID destinationId) {
        var destination = destinationService.findById(destinationId);
        return ResponseEntity.ok(new DestinationResponseRecord(destination));
    }

    @PutMapping("/{destinationId}")
    public ResponseEntity<DestinationResponseRecord> updateDestination(@PathVariable UUID destinationId, @RequestParam(value = "place", required = false) String place, @RequestParam(value = "description", required = false) String description, @RequestParam(value = "file", required = false) MultipartFile file) {

        var destination = destinationService.findById(destinationId);
        DestinationResponseRecord response = new DestinationResponseRecord(destination);
        if(place == null) {
            place = response.place();
        }
        if(description==null) {
            description = response.description();
        }
        String fileName = null;
        try {

            if(file == null){
                fileName = response.image();
            }else{
                fileName = uploadFileUtils.uploadFile(file, PATH);
            }
        } catch (IOException e){
           e.getMessage();
        }
        DestinationRecord destinationRecord = new DestinationRecord(place, description, fileName, response.creationDate(), LocalDateTime.now());

        destination.updateDestination(destinationRecord);
        destinationService.saveDestination(destination);
        return ResponseEntity.ok(new DestinationResponseRecord(destination));
    }

    @DeleteMapping("/{destinationId}")
    public ResponseEntity deleteDestination(@PathVariable UUID destinationId) {
       destinationService.findById(destinationId);
       destinationService.deleteDestination(destinationId);
       return  ResponseEntity.noContent().build();
    }





}
