package me.tharindu.couchbase_demo_project.services;

import lombok.AllArgsConstructor;
import me.tharindu.couchbase_demo_project.models.Airline;
import me.tharindu.couchbase_demo_project.repositories.AirlineRepository;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final CouchbaseTemplate couchbaseTemplate;

    @Override
    public Airline getAirlineById(String id) {
        return airlineRepository.findById(id);
    }

    @Override
    public Airline createAirline(Airline airline) {
        return airlineRepository.save(airline);
    }

    @Override
    public Airline updateAirline(String id, Airline airline) {
        return airlineRepository.update(id, airline);
    }

    @Override
    public void deleteAirline(String id) {
        airlineRepository.delete(id);
    }

    @Override
    public List<Airline> listAirlines(int limit, int offset) {
        return airlineRepository.findAll(limit, offset);
    }

    @Override
    public List<Airline> listAirlinesByCountry(String country, int limit, int offset) {
        return airlineRepository.findByCountry(country, limit, offset);
    }

    @Override
    public List<Airline> listAirlinesByDestinationAirport(String destinationAirport, int limit, int offset) {
        return airlineRepository.findByDestinationAirport(destinationAirport, limit, offset);
    }

}
