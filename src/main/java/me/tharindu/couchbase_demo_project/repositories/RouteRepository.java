package me.tharindu.couchbase_demo_project.repositories;

import me.tharindu.couchbase_demo_project.models.Route;

import java.util.List;

public interface RouteRepository {

    Route findById(String id);

    Route save(Route route);

    Route update(String id, Route route);

    void delete(String id);

    List<Route> findAll(int limit, int offset);

}
