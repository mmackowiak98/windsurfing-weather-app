package com.weather.windsurfingweather.repo;

import com.weather.windsurfingweather.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

    boolean existsByLocationNameIgnoreCase(String locationName);

}

