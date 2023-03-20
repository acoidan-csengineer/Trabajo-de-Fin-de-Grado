package com.tfgtourism.tourismtfg.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tfgtourism.tourismtfg.domains.Tourist;
import com.tfgtourism.tourismtfg.domains.TravelPreferences;
import com.tfgtourism.tourismtfg.services.services.TouristService;
import com.tfgtourism.tourismtfg.validators.TouristValidator;

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
@RequestMapping(value = "/tourists", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class TouristController {
    private final TouristService service;
    private final TouristValidator validator;

    public TouristController(TouristService serviceP, TouristValidator validatorP) {
        this.service = serviceP;
        this.validator = validatorP;
    }

    @GetMapping()
    public List<Tourist> get() {
        return this.service.findAll();
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> save(@RequestBody Tourist resource) {
        Pair<HttpStatus, String> error = this.validator.validate(resource);

        // Comprobamos que el item de entrada sea correcto
        if (!Objects.equals(error, null))
            throw new ResponseStatusException(error.getFirst(), error.getSecond());

        this.service.save(resource);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tourist> modify(@RequestBody Tourist resource, @PathVariable String id) {
        Pair<HttpStatus, String> error = this.validator.validate(resource);

        // Comprobamos que el item de entrada sea correcto
        if (!Objects.equals(error, null))
            throw new ResponseStatusException(error.getFirst(), error.getSecond());

        Optional<Tourist> resourceIs = this.service.findById(id);

        if (resourceIs.isPresent()) {
            Tourist oldResource = resourceIs.get();

            if (!resource.getName().equals(oldResource.getName()) || !resource.getSurname().equals(oldResource.getSurname()) || !resource.getEmail().equals(oldResource.getEmail()) || !resource.getDNI().equals(oldResource.getDNI())) {
                oldResource.setName(resource.getName());
                oldResource.setSurname(resource.getSurname());
                oldResource.setEmail(resource.getEmail());
                oldResource.setDNI(resource.getDNI());
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

    @PostMapping(value = "/{id}/travels-preferences", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TravelPreferences> addTravelPreferences(@RequestBody TravelPreferences travelPreferences, @PathVariable String id) {
        Optional<Tourist> resourceIs = this.service.findById(id);

        if (resourceIs.isPresent()) {
            Tourist oldResource = resourceIs.get();

            oldResource.pushTravelPreferences(travelPreferences);
            
            this.service.save(oldResource);
            return new ResponseEntity<>(travelPreferences, HttpStatus.CREATED);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/travels-preferences/{idT}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TravelPreferences> modifyTravelPreferences(@RequestBody TravelPreferences resource, @PathVariable String id, @PathVariable int idT) {
        Optional<Tourist> resourceIs = this.service.findById(id);
        if (resourceIs.isPresent()) {
            Tourist oldResource = resourceIs.get();

            TravelPreferences oldTravelPreferences = oldResource.getTravelsPreferences().get(idT);

            if (!oldTravelPreferences.getDestinationGeolocation().equals(resource.getDestinationGeolocation()) || !oldTravelPreferences.getBudget().equals(resource.getBudget()) || !oldTravelPreferences.getTransport().equals(resource.getTransport()) || !oldTravelPreferences.getActivities().equals(resource.getActivities()) || !oldTravelPreferences.getTransport().equals(resource.getTransport()) || !oldTravelPreferences.getReturnDatetime().equals(resource.getReturnDatetime()) || !oldTravelPreferences.getDepartureDatetime().equals(resource.getDepartureDatetime()) || !oldTravelPreferences.getTourismStartTime().equals(resource.getTourismStartTime()) || !oldTravelPreferences.getTourismEndTime().equals(resource.getTourismEndTime())) {
                oldResource.getTravelsPreferences().set(idT, resource);
                this.service.save(oldResource);

                return new ResponseEntity<>(resource, HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.validator.NOT_CHANGES);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}/travels-preferences/{idT}")
    public ResponseEntity<ArrayList<TravelPreferences>> deleteTravelPreferences(@PathVariable String id, @PathVariable int idT) {
        Optional<Tourist> resourceIs = this.service.findById(id);

        if (resourceIs.isPresent()) {
            Tourist oldResource = resourceIs.get();
            oldResource.getTravelsPreferences().remove(idT-1);
            ArrayList<TravelPreferences> touristTravelsPreferences = oldResource.getTravelsPreferences();

            for (int i=idT-1; i<oldResource.getTravelsPreferences().size(); i++) {
                touristTravelsPreferences.get(i).setId(String.valueOf(i+1));
            }

            oldResource.setTravelsPreferences(touristTravelsPreferences);
            this.service.save(oldResource);
            return new ResponseEntity<>(touristTravelsPreferences, HttpStatus.OK);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Tourist> deleteById(@PathVariable String id) {
        Optional<Tourist> entity = this.service.findById(id);
        if (entity.isPresent()) {
            this.service.delete(entity.get());
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tourist> getById(@PathVariable String id) {
        Optional<Tourist> entity = this.service.findById(id);
        if (entity.isPresent()) {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
