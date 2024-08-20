package me.tharindu.couchbase_demo_project.services;

import me.tharindu.couchbase_demo_project.models.Airline;

import java.util.List;

public interface AirlineService {

    Airline getAirlineById(String id);

    Airline createAirline(Airline airline);

    Airline updateAirline(String id, Airline airline);

    void deleteAirline(String id);

    List<Airline> listAirlines(int limit, int offset);

    List<Airline> listAirlinesByCountry(String country, int limit, int offset);

    List<Airline> listAirlinesByDestinationAirport(String destinationAirport, int limit, int offset);

}
