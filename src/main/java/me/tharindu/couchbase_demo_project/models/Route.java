package me.tharindu.couchbase_demo_project.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.couchbase.core.index.CompositeQueryIndex;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
@CompositeQueryIndex(fields = {"type ASC", "airlineId"}) //to specify the composite fields for indexing
public class Route {

    @Id
    @NotBlank(message = "ID is mandatory")
    private String id;

    @QueryIndexed // Query indexed to specify the field should be indexed
    @NotBlank(message = "Type is mandatory")
    @Field
    private String type;

    @NotBlank(message = "Airline is mandatory")
    @Field
    private String airline;

    @QueryIndexed
    @NotBlank(message = "Airline ID is mandatory")
    @Field
    private String airlineid;

    @NotBlank(message = "Source airport is mandatory")
    @Field
    private String sourceairport;

    @NotBlank(message = "Destination airport is mandatory")
    @Field
    private String destinationairport;

    @NotNull(message = "Stops is mandatory")
    @Field
    private int stops;

    @NotBlank(message = "Equipment is mandatory")
    @Field
    private String equipment;

    @Valid
    @Field
    private List<Schedule> schedule;

    @NotNull(message = "Distance is mandatory")
    @Field
    private double distance;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Schedule {

        @Min(0)
        @Max(6)
        @NotNull(message = "Day is mandatory")
        private int day;

        @NotBlank(message = "Flight is mandatory")
        private String flight;

        @NotBlank(message = "UTC is mandatory")
        private String utc;

    }

}
