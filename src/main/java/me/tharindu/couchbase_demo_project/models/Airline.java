package me.tharindu.couchbase_demo_project.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Airline implements Serializable {

    @NotBlank(message = "ID is mandatory")
    private String id;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "IATA code is mandatory")
    @Pattern(regexp = "^[A-Z]{2}$", message = "IATA code must be a 2-letter uppercase code")
    private String iata;

    @NotBlank(message = "ICAO code is mandatory")
    @Pattern(regexp = "^[A-Z]{3}$", message = "ICAO code must be a 3-letter uppercase code")
    private String icao;

    @NotBlank(message = "Callsign is mandatory")
    private String callsign;

    @NotBlank(message = "Country is mandatory")
    @QueryIndexed
    @Field
    private String country;

    @CreatedBy
    private String creator;

    @LastModifiedBy
    private String lastModifier;

    @LastModifiedDate
    private Date lastUpdated;

    @CreatedDate
    private Date created;

    @Version
    private Long version;
}
