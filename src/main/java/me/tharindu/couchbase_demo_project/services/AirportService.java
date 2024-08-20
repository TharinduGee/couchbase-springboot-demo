package me.tharindu.couchbase_demo_project.services;

import me.tharindu.couchbase_demo_project.models.Airport;

import java.util.List;

public interface AirportService {

    Airport findById(String id);

    Airport createAirport(Airport airport);

    Airport updateAirport(String id , Airport airport);

    Iterable<Airport> findAll();

    List<Airport> findByCountry(String country);

    List<Airport> findAllLuxury();

}
