package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Location;

import java.util.Optional;

public interface LocationService {
    Optional<Location> findLocation(Long id);
}
