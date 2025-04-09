package com.github.tel05.bitesizeapp;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;


public class Geocoding {

    private GeoApiContext context;

    // Constructor that accepts the GeoApiContext
    public Geocoding(GeoApiContext context) {
        this.context = context;
    }

    // Method to get latitude and longitude by zipcode
    public double[] getLatLongByZipCode(String zipcode) {
        try {
            // Perform Geocoding API request to retrieve address info
            GeocodingResult[] results = GeocodingApi.geocode(context, zipcode).await();

            // If results are found, return the first result's latitude and longitude
            if (results != null && results.length > 0) {
                double lat = results[0].geometry.location.lat;
                double lng = results[0].geometry.location.lng;
                return new double[]{lat, lng};
            } else {
                System.out.println("No results found for zipcode: " + zipcode);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving geocoding information: " + e.getMessage());
            return null;
        }
    }
}
