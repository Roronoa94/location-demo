package com.havells.locationdemo.controllers;

import com.havells.locationdemo.models.Location;
import com.havells.locationdemo.models.dto.LocationDto;
import com.havells.locationdemo.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebMvc
@RestController
@RequestMapping("/api/v1/location")
public class LocationController extends BaseController {

    private LocationService service;

    @Autowired
    public LocationController(LocationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto) {
        Location location = service.addLocation(Location.from(locationDto));
        return new ResponseEntity<>(locationDto.from(location), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getLocations() {
        List<Location> locations = service.getLocations();
        return new ResponseEntity<>(convertList(locations), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable final Long id) {
        Location location = service.getLocationById(id);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<LocationDto>> getLocationsByUserId(@PathVariable final Long id) {
        List<Location> locations = service.getLocationByUserId(id);
        return new ResponseEntity<>(convertList(locations), HttpStatus.OK);
    }

    private List<LocationDto> convertList(List<Location> locations) {
        return locations.stream().map((location) -> LocationDto.from(location)).collect(Collectors.toList());
    }

}
