package com.github.tel05.bitesizeapp;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BiteSizeTest {
    // Declare instances of Restaurant objects for testing various cases
    // 1 and 2 have same distance
    private Restaurant restaurant1;
    private Restaurant restaurant1Copy;
    private Restaurant restaurant2;
    // 3 and 4 have same price level
    private Restaurant restaurant3;
    private Restaurant restaurant4;
    // 5 and 6 have same rating
    private Restaurant restaurant5;
    private Restaurant restaurant6;
    // A HashMap to store restaurant objects by their IDs
    private HashMap<String, Restaurant> restaurantMap;

    // The setup method runs before each test to initialize the test data
    @BeforeEach
    void setUp() {
        restaurantMap = new HashMap<String,Restaurant>();
        // Initialize restaurant instances for testing
        restaurant1 = new Restaurant("1", "Longman & Eagle", "2657 N Kedzie Ave, Chicago, IL 60647", "(773)276-7110",
                0.1f, 5.0f, 4);
        restaurant1Copy = new Restaurant("1", "Longman & Eagle", "2657 N Kedzie Ave, Chicago, IL 60647", "(773)276-7110",
                0.1f, 5.0f, 4);
        restaurant2 = new Restaurant("2","Cafe Con Leche","2714 N Milwaukee Ave, Chicago, IL 60647","(773)289-4274",.1f,4.8f,2);

        restaurant3 = new Restaurant("5","La Boulangerie & Co Logan","3129 W Logan Blvd, Chicago, IL 60647","(773)666-5880",
                0.7f,3.5f,3 );
        restaurant4 = new Restaurant("6", "Chiya Chai", "2770 N Milwaukee Ave, Chicago, IL 60647", "(773)360-7541",
                0.6f, 4.9f, 3);

        restaurant5 = new Restaurant("10","Chiqueolatte Chicago", "2643 N Milwaukee Ave, Chicago, IL 60647", "(312)846-6839", 0.2f, 4.0f, 4);
        restaurant6 =  new Restaurant("9", "Same Day Cafe","2651 N Kedzie Blvd, Chicago, IL 60647", "(773)342-7040", 0.3f, 4.0f, 2 );
        // Add the restaurant instances to the HashMap, using their ID as the key
        restaurantMap.put(restaurant1.getId(), restaurant1);
        restaurantMap.put(restaurant2.getId(), restaurant2);
        restaurantMap.put(restaurant3.getId(), restaurant3);
        restaurantMap.put(restaurant4.getId(), restaurant4);
        restaurantMap.put(restaurant5.getId(), restaurant5);
        restaurantMap.put(restaurant6.getId(), restaurant6);
    }

    // Test the 'equals' method when both Restaurant objects have the same attributes
    @Test
    void testEquals_SameAttributes() {
        assertEquals(restaurant1, restaurant1Copy, "Restaurants with the same attributes should be equal");
    }
    // Test the 'equals' method when Restaurant objects have different attributes
    @Test
    void testEquals_DifferentAttributes() {
        assertNotEquals(restaurant1, restaurant3, "Restaurants with different attributes should not be equal");
    }
    // Test the 'hashCode' method when Restaurant objects have the same attributes
    @Test
    void testHashCode_SameAttributes() {
        assertEquals(restaurant1.hashCode(), restaurant1Copy.hashCode(), "Equal restaurants should have the same hash code");
    }
    // Test the 'hashCode' method when Restaurant objects have different attributes
    @Test
    void testHashCode_DifferentAttributes() {
        assertNotEquals(restaurant1.hashCode(), restaurant3.hashCode(),
                "Different restaurants should ideally have different hash codes, but this is not strictly required");
    }
    // Test sorting restaurants by distance using a Min Priority Queue
    @Test
    void testDistanceMinPQ() {
        // Comparator to compare restaurants by distance, with tiebreaker by ID
        Comparator<String> distanceComparator = Comparator.comparing((String id)-> restaurantMap.get(id).getDistance())
                .thenComparing(id -> restaurantMap.get(id).getId());
        // Create a MinPQ for sorting restaurant IDs based on distance
        MinPQ<String> distanceMinPQ = new MinPQ<>(distanceComparator);
        distanceMinPQ.insert(restaurant1.getId());
        distanceMinPQ.insert(restaurant2.getId());
        distanceMinPQ.insert(restaurant3.getId());

        // Collect the sorted restaurant IDs in a list
        List<String> sortedByDistance = new ArrayList<>();
        while (!distanceMinPQ.isEmpty()) {
            sortedByDistance.add(distanceMinPQ.delMin());
        }
        // Assert the expected order based on distance, with tiebreaker by ID
        // rest1:( id = 1 dist = 0.1), rest2 : (id = 2 dist = 0.1), rest3: (id=3 dist=0.6)
        assertEquals(restaurant1.getId(), sortedByDistance.get(0),"Expected restaurant1 as the closest by distance with tiebreaker by ID. rest1:(id=1 dist=0.1)");
        assertEquals(restaurant2.getId(), sortedByDistance.get(1),"Expected restaurant2 as the 2nd closest by distance with tiebreaker by ID. rest2:(id=2 dist=0.1)");
        assertEquals(restaurant3.getId(), sortedByDistance.get(2),"Expected restaurant3 as the 3rd closest by distance. No tiebreaker. rest3:(id=3 dist=0.6)");
    }

    // Test sorting restaurants by price level using a Min Priority Queue
    @Test
    void testPriceLevelMinPQ() {
        // Comparator to compare restaurants by price level, with tiebreaker by ID
        Comparator<String> priceComparator = Comparator.comparing((String id) -> restaurantMap.get(id).getPriceLevel())
                        .thenComparing(id -> restaurantMap.get(id).getId());
        // Create a MinPQ for sorting restaurant IDs based on price level
        MinPQ<String> priceMinPQ = new MinPQ<>(priceComparator);
        priceMinPQ.insert(restaurant3.getId());
        priceMinPQ.insert(restaurant4.getId());
        priceMinPQ.insert(restaurant5.getId());

        // Collect the sorted restaurant IDs in a list
        List<String> sortedByPrice = new ArrayList<>();
        while (!priceMinPQ.isEmpty()) {
            sortedByPrice.add(priceMinPQ.delMin());
        }

        // Assert the expected order based on price level, with tiebreaker by ID
        // rest3:(id= 5 priceLevel=3), rest4:(id=6 priceLevel = 3), rest5:(id=10 priceLevel=4)
        assertEquals(restaurant3.getId(), sortedByPrice.get(0),"Expected restaurant3 as lowest price level with tiebreaker by ID. rest3:(id=5 priceLevel=3)");
        assertEquals(restaurant4.getId(), sortedByPrice.get(1),"Expected restaurant4 as 2nd lowest price level with tiebreaker by ID. rest4:(id=6 priceLevel=3)");
        assertEquals(restaurant5.getId(), sortedByPrice.get(2),"Expected restaurant5 as 3rd lowest price level. No tiebreaker. rest5:(id=10 priceLevel=4)");
    }

    // Test sorting restaurants by rating using a Max Priority Queue
    @Test
    void testRatingMaxPQ() {
        // Comparator to compare restaurants by rating, with tiebreaker by ID
        Comparator<String> ratingComparator = Comparator.comparing((String id) -> restaurantMap.get(id).getRating())
                        .thenComparing(id -> restaurantMap.get(id).getId());
        // Create a MaxPQ for sorting restaurant IDs based on rating
        MaxPQ<String> ratingMaxPQ = new MaxPQ<>(ratingComparator);
        ratingMaxPQ.insert(restaurant6.getId());
        ratingMaxPQ.insert(restaurant5.getId());
        ratingMaxPQ.insert(restaurant3.getId());

        // Collect the sorted restaurant IDs in a list
        List<String> sortedByRating = new ArrayList<>();
        while (!ratingMaxPQ.isEmpty()) {
            sortedByRating.add(ratingMaxPQ.delMax());
        }

        // Assert the expected order based on rating, with tiebreaker by ID
        // rest5:(id=10 rating=4), rest6:(id=9 rating= 4), rest3:(id=5 rating=3.5)
        assertEquals(restaurant6.getId(), sortedByRating.get(0),"Expected restaurant6 as highest rated with tiebreaker by ID. rest6:(id=9 rating= 4)");
        assertEquals(restaurant5.getId(), sortedByRating.get(1),"Expected restaurant5 as 2nd highest rated with tiebreaker by ID. rest5:(id=10 rating=4)");
        assertEquals(restaurant3.getId(), sortedByRating.get(2),"Expected restaurant3 as 3rd highest rated. No tiebreaker. rest3:(id=5 rating=3.5)");
    }

}

