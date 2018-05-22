package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.EntityNotFoundException;
import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("public/locations/{id}")
    public Location getLocation(@PathVariable Long id) {
        return locationService.findLocation(id)
                .orElseThrow(() -> new EntityNotFoundException("Location", id));
    }

    @GetMapping("public/locations")
    public Collection<Location> getLocationByNameLike(@RequestParam String name) {
        return locationService.findLocationByNameLike(name);
    }
}
