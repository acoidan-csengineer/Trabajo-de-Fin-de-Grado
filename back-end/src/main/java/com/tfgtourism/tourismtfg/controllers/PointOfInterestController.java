package com.tfgtourism.tourismtfg.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tfgtourism.tourismtfg.domains.PointOfInterest;
import com.tfgtourism.tourismtfg.services.services.PointOfInterestService;
import com.tfgtourism.tourismtfg.validators.PointOfInterestValidator;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "/pois", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class PointOfInterestController {
    private final PointOfInterestService service;
    private final PointOfInterestValidator validator;

    public PointOfInterestController(PointOfInterestService serviceP, PointOfInterestValidator validatorP) {
        this.service = serviceP;
        this.validator = validatorP;
    }

    @GetMapping()
    public List<PointOfInterest> get() {
        return this.service.findAll();
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PointOfInterest> save(@RequestBody PointOfInterest resource) {
        Pair<HttpStatus, String> error = this.validator.validate(resource);

        // Comprobamos que el item de entrada sea correcto
        if (!Objects.equals(error, null))
            throw new ResponseStatusException(error.getFirst(), error.getSecond());


        this.service.save(resource);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PointOfInterest> modify(@RequestBody PointOfInterest resource, @PathVariable String id) {
        Pair<HttpStatus, String> error = this.validator.validate(resource);

        // Comprobamos que el item de entrada sea correcto
        if (!Objects.equals(error, null))
            throw new ResponseStatusException(error.getFirst(), error.getSecond());
        
        Optional<PointOfInterest> resourceIs = this.service.findById(id);

        if (resourceIs.isPresent()) {
            PointOfInterest oldResource = resourceIs.get();

            if (!Objects.equals(resource.getName(), oldResource.getName()) || !Objects.equals(resource.getTown(), oldResource.getTown()) || !Objects.equals(resource.getDescription(), oldResource.getDescription()) || !Objects.equals(resource.getPostalCode(), oldResource.getPostalCode()) || !Objects.equals(resource.getAverageCost(), oldResource.getAverageCost()) || !Objects.equals(resource.getStayHourNumber(), oldResource.getStayHourNumber()) || !Objects.equals(resource.getApplications(), oldResource.getApplications()) || !Objects.equals(resource.getCategories(), oldResource.getCategories())  || !Objects.equals(resource.getHourly(), oldResource.getHourly()) || !Objects.equals(resource.getGeolocationPoint(), oldResource.getGeolocationPoint()) || !Objects.equals(resource.getGeolocationLineString(), oldResource.getGeolocationLineString()) || !Objects.equals(resource.getGeolocationPolygon(), oldResource.getGeolocationPolygon()) || !Objects.equals(resource.getOpeningDays(), oldResource.getOpeningDays())) {
                oldResource.setName(resource.getName());
                oldResource.setTown(resource.getTown());
                oldResource.setDescription(resource.getDescription());
                oldResource.setPostalCode(resource.getPostalCode());
                oldResource.setAverageCost(resource.getAverageCost());
                oldResource.setApplications(resource.getApplications());
                oldResource.setCategories(resource.getCategories());
                oldResource.setStayHourNumber(resource.getStayHourNumber());
                oldResource.setOpeningDays(resource.getOpeningDays());
                oldResource.setHourly(resource.getHourly());
                oldResource.setGeolocationPoint(resource.getGeolocationPoint());
                oldResource.setGeolocationLineString(resource.getGeolocationLineString());
                oldResource.setGeolocationPolygon(resource.getGeolocationPolygon());
                this.service.save(oldResource);
                return new ResponseEntity<>(oldResource, HttpStatus.CREATED);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.validator.NOT_CHANGES);
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PointOfInterest> deleteById(@PathVariable String id) {
        Optional<PointOfInterest> entity = this.service.findById(id);
        if (entity.isPresent()) {
            this.service.delete(entity.get());
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<PointOfInterest> deleteAll() {
        this.service.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PointOfInterest> getById(@PathVariable String id) {
        Optional<PointOfInterest> entity = this.service.findById(id);
        if (entity.isPresent()) {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}