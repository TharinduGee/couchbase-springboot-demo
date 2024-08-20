package me.tharindu.couchbase_demo_project.services;

import me.tharindu.couchbase_demo_project.models.Route;
import me.tharindu.couchbase_demo_project.repositories.RouteRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RouteServiceImpl implements RouteService{

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route getRouteById(String id) {
        return routeRepository.findById(id);
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public Route updateRoute(String id, Route route) {
        return routeRepository.update(id, route);
    }

    public void deleteRoute(String id) {
        routeRepository.delete(id);
    }

    public List<Route> listRoutes(int limit, int offset) {
        return routeRepository.findAll(limit, offset);
    }
}
