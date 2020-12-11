package com.havells.locationdemo.models;

import com.havells.locationdemo.models.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double latitude;
    private double longitude;
    private String timestamp;

    public Location(Long userId, double latitude, double longitude, String timestamp) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public static Location from(LocationDto locationDto){
        Location location = new Location(
                locationDto.getUserId(),
                locationDto.getLattitude(),
                locationDto.getLongitude(),
                locationDto.getTimestamp()
        );
        return location;
    }
}
