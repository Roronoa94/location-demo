package com.havells.locationdemo.services;

import com.havells.locationdemo.models.Location;
import com.havells.locationdemo.repositories.LocationRepository;
import com.havells.locationdemo.exceptions.*;
import com.havells.locationdemo.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LocationInteractor implements LocationService {

    private final LocationRepository repository;

    @Autowired
    public LocationInteractor(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Location addLocation(Location location) {
        return repository.save(location);
    }

    @Override
    public Location getLocationById(Long locationId) {
        return repository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    @Override
    public List<Location> getLocationByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Location> getLocations() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

}
