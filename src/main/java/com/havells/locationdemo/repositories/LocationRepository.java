package com.havells.locationdemo.repositories;

import com.havells.locationdemo.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findByUserId(Long userId);

}
