package me.tharindu.couchbase_demo_project.repositories;

import com.couchbase.client.java.query.QueryScanConsistency;
import me.tharindu.couchbase_demo_project.models.Airline;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AirlineRepository {

    Airline findById(String id);

    Airline save(Airline airline);

    Airline update(String id, Airline airline);

    void delete(String id);

    List<Airline> findAll(int limit, int offset);

    List<Airline> findByCountry(String country, int limit, int offset);

    List<Airline> findByDestinationAirport(String destinationAirport, int limit, int offset);

}
