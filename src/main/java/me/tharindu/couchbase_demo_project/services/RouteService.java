package me.tharindu.couchbase_demo_project.services;

import me.tharindu.couchbase_demo_project.models.Route;

import java.util.List;

public interface RouteService {

    Route getRouteById(String id);

    Route createRoute(Route route);

    Route updateRoute(String id, Route route);

    void deleteRoute(String id);

    List<Route> listRoutes(int limit, int offset);

}
