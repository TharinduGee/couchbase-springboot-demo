package me.tharindu.couchbase_demo_project.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tharindu.couchbase_demo_project.models.Airport;
import me.tharindu.couchbase_demo_project.repositories.AirportRepository;
import org.springframework.data.couchbase.core.CouchbaseOperations;
import org.springframework.data.couchbase.core.ReactiveCouchbaseOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CouchbaseOperations couchbaseOperations;
    private final ReactiveCouchbaseOperations reactiveCouchbaseOperations;

    @Override
    @Transactional
    public Airport findById(String id) {
        try{
            Thread.sleep(Duration.ofSeconds(11)); // added 11 seconds of delay to examine the transactional behavior
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        Optional<Airport> airport = airportRepository.findById(id);
        return airport.orElse(null);
    }

    @Override
    public Airport createAirport(Airport airport){
        return airportRepository.save(airport);
    }

    @Override
    @Transactional
    public Airport updateAirport(String id, Airport airport){
        Optional<Airport> existingAirport = airportRepository.findById(id);
        if(existingAirport.isEmpty()){
            return null;
        }else {
            Airport updatedAirport = Airport.builder()
                    .type(airport.getType())
                    .airportname(airport.getAirportname())
                    .city(airport.getCity())
                    .tz(airport.getTz())
                    .country(airport.getCountry())
                    .icao(airport.getIcao())
                    .geo(airport.getGeo())
                    .build();
            return airportRepository.save(updatedAirport);
        }
    }

    @Override
    public Iterable<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public List<Airport> findByCountry(String country) {
        return airportRepository.findAllByCountryContainingIgnoreCase(country);
    }

    @Override
    public List<Airport> findAllLuxury() {
        return airportRepository.findAllLuxury();
    }

    @Override
    public List<Airport> findAirportsByDynamicCriteria(String criteriaField, String criteriaValue) {
        Assert.isTrue(
                Arrays.stream(Airport.class.getDeclaredFields())
                        .anyMatch(field -> field.getName().equals(criteriaField)),
                "Field is invalid"
        );
        return airportRepository.findAirportsByDynamicCriteria(criteriaField, criteriaValue);
    }

}
