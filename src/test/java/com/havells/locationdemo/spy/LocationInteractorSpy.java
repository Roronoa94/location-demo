package com.havells.locationdemo.spy;

import com.havells.locationdemo.models.Location;
import com.havells.locationdemo.services.interfaces.LocationService;
import java.util.List;

public class LocationInteractorSpy implements LocationService {

    public Long inputId;
    public Location input;
    public Location response;
    public List<Location> locationList;
    public RuntimeException exception;

    public void reset() {
        this.input = null;
        this.response = null;
        this.exception = null;
        this.locationList = null;
        this.inputId = null;
    }

    @Override
    public Location addLocation(Location location) {
        this.input = location;
        if (exception != null) {
            throw exception;
        }
        return response;
    }

    @Override
    public Location getLocationById(Long locationId) {
        this.inputId = locationId;
        if (exception != null) {
            throw exception;
        }
        return response;
    }

    @Override
    public List<Location> getLocationByUserId(Long userId) {
        this.inputId = userId;
        if (exception != null) {
            throw exception;
        }
        return locationList;
    }

    @Override
    public List<Location> getLocations() {
        if (exception != null) {
            throw exception;
        }
        return locationList;
    }

}
