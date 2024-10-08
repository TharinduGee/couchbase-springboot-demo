package me.tharindu.couchbase_demo_project.repositories;

import me.tharindu.couchbase_demo_project.models.Airport;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("inventory")
@Collection("airport")
public interface AirportRepository extends CouchbaseRepository<Airport, String> {

    List<Airport> findAllByCountryContainingIgnoreCase(String country);

    // Querying using N1QL
    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND type = 'LUXURY'")
    List<Airport> findAllLuxury();

    // Querying using N1QL placeholders and SpEL
    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND #{[0]} = $2 ")
    List<Airport> findAirportsByDynamicCriteria(String field, String value);

}
