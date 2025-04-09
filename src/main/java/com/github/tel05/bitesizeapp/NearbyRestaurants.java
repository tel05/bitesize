package com.github.tel05.bitesizeapp;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearbyRestaurants {

    private GeoApiContext context;

    public NearbyRestaurants(GeoApiContext context){
        this.context = context;
    }
    
    // Method to calculate distance between two coordinates (user and restaurant)
    private float calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        // Haversine formula to calculate the distance (in miles)
        final int R = 3958; // Radius of Earth in miles
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (float) (R * c); // Distance in miles
    }

    public Map<String, Restaurant> getNearbyRestaurants(double latitude, double longitude) {
        Map<String, Restaurant> restaurants = new HashMap<>();
        
        // Wrapping the code in a try-catch block
        try {
            // Create a LatLng object for the given latitude and longitude
            LatLng location = new LatLng(latitude, longitude);
    
            // Make the Places API request to get nearby places
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000) // Search within a 5km radius (change as needed)
                    .type(com.google.maps.model.PlaceType.RESTAURANT) // Only look for restaurants
                    .await(); // This makes the request and waits for the response
    
            // Parse the results and create Restaurant objects
            for (PlacesSearchResult result : response.results) {
                // Fetch additional details for each place (like phone number, rating, etc.)
                PlaceDetails placeDetails = PlacesApi.placeDetails(context, result.placeId).await();
                
                // Print the class type of priceLevel to help with debugging
                if (placeDetails.priceLevel != null) {
                    System.out.println("PriceLevel type: " + placeDetails.priceLevel.getClass().getName());
                } else {
                    System.out.println("PriceLevel is null.");
                }
    
                // Calculate distance from user (this can be done with a method you implement)
                float distance = calculateDistance(latitude, longitude, placeDetails.geometry.location.lat, placeDetails.geometry.location.lng);
    
                // Create a Restaurant object
                Restaurant restaurant = new Restaurant(
                        result.placeId,
                        result.name,
                        placeDetails.formattedAddress,
                        placeDetails.formattedPhoneNumber,
                        distance,
                        placeDetails.rating,
                        1 // For now, we're passing a placeholder for priceLevel
                );
    
                // Add the restaurant to the map
                restaurants.put(restaurant.getId(), restaurant);
            }
        } catch (InterruptedException | ApiException | IOException e) {
            // Handle exceptions here
            e.printStackTrace();
            System.out.println("Error occurred while retrieving nearby restaurants.");
        }
    
        return restaurants;
    }
    





}
