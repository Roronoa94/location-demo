package com.havells.locationdemo.services.interfaces;

import com.havells.locationdemo.models.Location;
import java.util.List;

public interface LocationService {

    Location addLocation(Location location);
    Location getLocationById(Long locationId);
    List<Location> getLocationByUserId(Long userId);
    List<Location> getLocations();

}
