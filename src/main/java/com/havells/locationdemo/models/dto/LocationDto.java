package com.havells.locationdemo.models.dto;

import com.havells.locationdemo.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private Long id;
    private Long userId;
    private double lattitude;
    private double longitude;
    private String timestamp;

    public static LocationDto from(Location location){
        LocationDto locationDto = new LocationDto(
                location.getId(),
                location.getUserId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getTimestamp());
        return locationDto;
    }
}
