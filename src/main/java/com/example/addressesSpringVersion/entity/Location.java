package com.example.addressesSpringVersion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "location")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private long locationId;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    public Location() {}

    public Location(double lat, double lon) {
        this(lat, lon, null);
    }

    public Location(double lat, double lon, Address address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }
}
