package me.tharindu.couchbase_demo_project.controllers;

import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.transactions.error.TransactionExpiredException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tharindu.couchbase_demo_project.models.Airport;
import me.tharindu.couchbase_demo_project.services.AirportServiceImpl;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.springframework.data.couchbase.transaction.error.TransactionSystemAmbiguousException;
import org.springframework.data.couchbase.transaction.error.TransactionSystemUnambiguousException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1/airport")
@RequiredArgsConstructor
@Slf4j
public class AirportController {

    private final AirportServiceImpl airportService;

    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String DOCUMENT_NOT_FOUND = "Document Not Found";
    private static final String DOCUMENT_EXISTS = "Document exists by same id";
    private static final String TRANSACTION_EXPIRED = "Transaction has expired.";
    private static final String INVALID_FIELD = "Invalid Field";

    @GetMapping("/{id}")
    @Operation(summary = "Get an airport by ID", description = "Get Airport by specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to retrieve a document with a specified ID. \n\n Code: [`controllers/AirportController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirportController.java) \n File: `AirportController.java` \n Method: `getAirport`", tags = {
            "Airport" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport found"),
            @ApiResponse(responseCode = "404", description = "Airport not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "id", description = "The ID of the airport to retrieve", required = true, example = "airport_1254")
    public ResponseEntity<Airport> getAirport(@PathVariable String id) {
        try {
            Airport airport = airportService.findById(id);
            if (airport != null) {
                return new ResponseEntity<>(airport, HttpStatus.OK);
            } else {
                log.error(DOCUMENT_NOT_FOUND + ": " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DocumentNotFoundException e) {
            log.error(DOCUMENT_NOT_FOUND + ": " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TransactionSystemUnambiguousException e) {
            log.error(TRANSACTION_EXPIRED + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        } catch  (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    @Operation(summary = "Create an airport", description = "Create Airport by specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to create a document with a specified ID. \n\n Code: [`controllers/AirportController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirportController.java) \n File: `AirportController.java` \n Method: `createAirport`", tags = {
            "Airport" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airport created"),
            @ApiResponse(responseCode = "409", description = "Airport already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Airport> createAirport(@RequestBody @Valid Airport airport){
        try {
            airportService.createAirport(airport);
            return new ResponseEntity<>(airport, HttpStatus.CREATED);
        } catch (DocumentExistsException ex){
            log.error(DOCUMENT_EXISTS + ": {}" , airport.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception ex) {
            log.error(INTERNAL_SERVER_ERROR + ": {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an airport", description = "Update Airport by specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to update a document with a specified ID. \n\n Code: [`controllers/AirportController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirportController.java) \n File: `AirportController.java` \n Method: `updateAirport`", tags = {
            "Airport" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport updated"),
            @ApiResponse(responseCode = "404", description = "Airport not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "id", description = "The ID of the airport to update", required = true, example = "airport_1254")
    public ResponseEntity<Airport> updateAirport(@PathVariable String id, @Valid @RequestBody Airport airport) {
        try {
            Airport updatedAirport = airportService.updateAirport(id, airport);
            if (updatedAirport != null) {
                return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DocumentNotFoundException e) {
            log.error(DOCUMENT_NOT_FOUND + ": {}" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Airport>> findByCountry(@PathVariable String country) {

        try {
            List<Airport> airports = airportService.findByCountry(country);
            if(airports != null && !airports.isEmpty()){
                return new ResponseEntity<>(airports, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(INTERNAL_SERVER_ERROR + ": {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/luxury")
    public ResponseEntity<List<Airport>> findAllLuxury() {

        try {
            List<Airport> airports = airportService.findAllLuxury();
            if(airports != null && !airports.isEmpty()){
                return new ResponseEntity<>(airports, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(INTERNAL_SERVER_ERROR + ": {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/dynamic")
    public ResponseEntity<List<Airport>> findAirportsByDynamicCriteria(@RequestParam String criteriaField, @RequestParam String criteriaValue) {

        try {
            List<Airport> airports = airportService.findAirportsByDynamicCriteria(criteriaField, criteriaValue);
            if(airports != null && !airports.isEmpty()){
                return new ResponseEntity<>(airports, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex ) {
            log.error(INVALID_FIELD+ ": {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error(INTERNAL_SERVER_ERROR + ": {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
