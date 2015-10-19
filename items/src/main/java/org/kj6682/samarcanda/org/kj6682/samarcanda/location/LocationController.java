package org.kj6682.samarcanda.org.kj6682.samarcanda.location;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

/**
 * Created by luigi on 30/09/15.
 */

@RestController
public class LocationController {

    @Inject
    private LocationRepository locationRepository;

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Location>> getAllLocations(){
        Iterable<Location> locations = locationRepository.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @RequestMapping(value = "/locations", method = RequestMethod.POST)
    public ResponseEntity<?> createLocation(@RequestBody Location location){

        location = locationRepository.save(location);

        //Set the location header for the newly created location
        HttpHeaders responseHeaders = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(location.getId())
                .toUri();
        responseHeaders.setLocation(uri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLocation(@PathVariable Long locationId) {
        Location p = locationRepository.findOne(locationId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value="/locations/{locationId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updateLocation(@RequestBody Location location, @PathVariable Long locationId) {
        // Save the entity
        locationRepository.save(location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/locations/{locationId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        locationRepository.delete(locationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
