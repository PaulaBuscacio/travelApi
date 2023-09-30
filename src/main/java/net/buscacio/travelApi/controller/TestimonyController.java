package net.buscacio.travelApi.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import net.buscacio.travelApi.dto.TestimonyRecord;
import net.buscacio.travelApi.dto.TestimonyResponseRecord;
import net.buscacio.travelApi.entity.Destination;
import net.buscacio.travelApi.entity.Testimony;
import net.buscacio.travelApi.service.DestinationService;
import net.buscacio.travelApi.service.TestimonyService;
import net.buscacio.travelApi.service.UserService;
import net.buscacio.travelApi.service.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/testimonies")
@Validated
public class TestimonyController {

    @Autowired
    TestimonyService testimonyService;
    @Autowired
    UserService userService;
    @Autowired
    DestinationService destinationService;
    @Autowired
    UploadFileUtils uploadFileUtils;

    @Value("${images.testimony}")
    String PATH;



    @PostMapping("/destinations/{destinationId}")
    public  ResponseEntity<TestimonyResponseRecord> createTestimony(@RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestParam("testimony")
                                                                    @NotBlank(message = "testimony can not be empty") @Size(min=4, max=255, message= "testimony must be between 5 up to 255 characters")
                                                                    String text,  @PathVariable UUID destinationId, UriComponentsBuilder uriComponentsBuilder) {
        String fileName = null;
       try {
        fileName = uploadFileUtils.uploadFile(file, PATH);
       }catch (IOException e) {
           e.getMessage();
       }
       Destination destination = destinationService.findById(destinationId);
       TestimonyRecord testimonyRecord = new TestimonyRecord(text, fileName, destination, LocalDateTime.now(), LocalDateTime.now());
        var testimony = new Testimony(testimonyRecord);
        testimonyService.saveTestimony(testimony);
        var uri = uriComponentsBuilder.path("testimonies/{testimonyId}").buildAndExpand(testimony.getId()).toUri();

        return ResponseEntity.created(uri).body(new TestimonyResponseRecord(testimony));

    }



    @GetMapping
    public ResponseEntity<Page<TestimonyResponseRecord>> getTestimonies(@PageableDefault(size=3,sort={"creationDate"}) Pageable pageable){

        Page<TestimonyResponseRecord> page = testimonyService.findAll(pageable).map(TestimonyResponseRecord::new);
        return new ResponseEntity(page, HttpStatus.OK);

    }

    @GetMapping("/{testimonyId}")
    public ResponseEntity<TestimonyResponseRecord> getTestimonyById(@PathVariable UUID testimonyId) {
        var testimony = testimonyService.findById(testimonyId);
        return ResponseEntity.ok(new TestimonyResponseRecord(testimony));
     }

    @PutMapping("/{testimonyId}")
    public ResponseEntity<TestimonyResponseRecord> updateTestimony(@PathVariable UUID testimonyId, @RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestParam("testimony")
                                                                    @NotBlank(message = "testimony can not be empty") @Size(min=4, max=255, message= "testimony must be between 5 up to 255 characters")
                                                                    String text) {

        var testimony = testimonyService.findById(testimonyId);
        TestimonyResponseRecord response = new TestimonyResponseRecord(testimony);
        if(text==null) {
            text = response.testimony();
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
        TestimonyRecord testimonyRecord = new TestimonyRecord(text, fileName, testimony.getDestination(), response.creationDate(), LocalDateTime.now());

        testimony.updateTestimony(testimonyRecord);
        testimonyService.saveTestimony(testimony);
        return ResponseEntity.ok(new TestimonyResponseRecord(testimony));
    }

    @DeleteMapping("/{testimonyId}")
    public ResponseEntity deleteTestimony(@PathVariable UUID testimonyId) {
       testimonyService.findById(testimonyId);
       testimonyService.deleteDepoiment(testimonyId);
       return  ResponseEntity.noContent().build();
    }





}
