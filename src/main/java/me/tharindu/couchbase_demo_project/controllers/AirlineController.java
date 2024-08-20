package me.tharindu.couchbase_demo_project.controllers;

import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tharindu.couchbase_demo_project.models.Airline;
import me.tharindu.couchbase_demo_project.services.AirlineServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airline")
@Slf4j
@AllArgsConstructor
public class AirlineController {

    private final AirlineServiceImpl airlineService;

    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String DOCUMENT_NOT_FOUND = "Document Not Found";

    @GetMapping("/{id}")
    @Operation(summary = "Get an airline by ID", description = "Get Airline by specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to retrieve a document with a specified ID. \n\n Code: [`controllers/AirlineController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirlineController.java) \n File: `AirlineController.java` \n Method: `getAirline`", tags = {
            "Airline" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline found"),
            @ApiResponse(responseCode = "404", description = "Airline not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "id", description = "Airline ID", required = true, example = "airline_10")
    public ResponseEntity<Airline> getAirline(@PathVariable(required = true) String id) {
        try {
            Airline airline = airlineService.getAirlineById(id);
            if (airline != null) {
                return new ResponseEntity<>(airline, HttpStatus.OK);
            } else {
                log.error(DOCUMENT_NOT_FOUND + ": " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DocumentNotFoundException e) {
            log.error(DOCUMENT_NOT_FOUND + ": " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    @Operation(summary = "Create an airline", description = "Create Airport with specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to create a new document with a specified ID. \n\n Code: [`controllers/AirlineController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirlineController.java) \n File: `AirlineController.java` \n Method: `createAirline`", tags = {
            "Airline" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airline created"),
            @ApiResponse(responseCode = "409", description = "Airline already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "id", description = "Airline ID", required = true, example = "airline_10")
    public ResponseEntity<Airline> createAirline(@PathVariable String id, @Valid @RequestBody Airline airline) {
        try {
            Airline newAirline = airlineService.createAirline(airline);
            return new ResponseEntity<>(newAirline, HttpStatus.CREATED);
        } catch (DocumentExistsException e) {
            log.error("Document already exists: " + id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an airline", description = "Update Airport with specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to update a document with a specified ID. \n\n Code: [`controllers/AirlineController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirlineController.java) \n File: `AirlineController.java` \n Method: `updateAirline`", tags = {
            "Airline" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline updated"),
            @ApiResponse(responseCode = "404", description = "Airline not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "id", description = "Airline ID", required = true, example = "airline_10")
    public ResponseEntity<Airline> updateAirline(@PathVariable String id, @Valid @RequestBody Airline airline) {
        try {
            Airline updatedAirline = airlineService.updateAirline(id, airline);
            if (updatedAirline != null) {
                return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DocumentNotFoundException e) {
            log.error(DOCUMENT_NOT_FOUND + ": " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an airline", description = "Delete Airport with specified ID.\n\nThis provides an example of using Key Value operations in Couchbase to delete a document with a specified ID. \n\n Code: [`controllers/AirlineController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirlineController.java) \n File: `AirlineController.java` \n Method: `deleteAirline`", tags = {
            "Airline" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Airline deleted"),
            @ApiResponse(responseCode = "404", description = "Airline not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "id", description = "Airline ID", required = true, example = "airline_10")
    public ResponseEntity<Void> deleteAirline(@PathVariable String id) {
        try {
            airlineService.deleteAirline(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DocumentNotFoundException e) {
            log.error(DOCUMENT_NOT_FOUND + ": " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    @Operation(summary = "List all airlines by country", description = "List all Airports by country.\n\nThis provides an example of using N1QL to query all documents of a specific type by a specific field. \n\n Code: `controllers/AirlineController.java` \n File: `AirlineController.java` \n Method: `listAirlinesByCountry`", tags = {
            "Airline" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airlines found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "country", description = "Country", required = false, example = "United States")
    public ResponseEntity<List<Airline>> listAirlinesByCountry(@RequestParam(required = false) String country,
                                                               @RequestParam(defaultValue = "10") int limit,
                                                               @RequestParam(defaultValue = "0") int offset) {
        try {
            List<Airline> airlines;
            if (country == null || country.isEmpty()) {
                airlines = airlineService.listAirlines(limit, offset);
            } else {
                airlines = airlineService.listAirlinesByCountry(country, limit, offset);
            }
            return new ResponseEntity<>(airlines, HttpStatus.OK);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/to-airport")
    @Operation(summary = "List all airlines by destination airport", description = "List all Airports by destination airport.\n\nThis provides an example of using N1QL to query all documents of a specific type by a specific field. \n\n Code: [`controllers/AirlineController.java`](https://github.com/couchbase-examples/java-springboot-quickstart/blob/main/src/main/java/org/couchbase/quickstart/springboot/controllers/AirlineController.java) \n File: `AirlineController.java` \n Method: `listAirlinesByDestinationAirport`", tags = {
            "Airline" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airlines found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(name = "destinationAirport", description = "Destination Airport", required = true, example = "SFO")
    public ResponseEntity<List<Airline>> listAirlinesByDestinationAirport(
            @RequestParam(required = true) String destinationAirport,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        try {
            List<Airline> airlines = airlineService.listAirlinesByDestinationAirport(destinationAirport, limit, offset);
            return new ResponseEntity<>(airlines, HttpStatus.OK);
        } catch (Exception e) {
            log.error(INTERNAL_SERVER_ERROR + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
