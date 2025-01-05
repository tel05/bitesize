package com.github.tel05.bitesizeapp;

/**
 * This class contains a method that generates an array of 'Restaurant' objects.
 * The data represents a sample of restaurants and their details, which are meant to mimic data
 * that could be retrieved from the Google Places API. The data is unordered and is used for testing
 * and demonstration purposes.
 */
public class RestaurantData {

    // Method to generate and return an array of Restaurant objects.
    // This array simulates restaurant data typically fetched from an API like Google Places.
    // Each Restaurant object is initialized with an ID, name, address, phone number, distance
    // from user (in miles), rating (between 1.0 and 5.0), and price level (0 - 4).
    public static Restaurant[] getData(){
        // Create an array of Restaurant objects.
        Restaurant[] array = new Restaurant[10];
        // Populate the array with Restaurant objects containing sample data.
        // Each entry corresponds to a restaurant with specific attributes.
        array[0] = new Restaurant(
                "1", "Longman & Eagle", "2657 N Kedzie Ave, Chicago, IL 60647", "(773)276-7110",
                0.1f, 5.0f, 4);
        array[1] = new Restaurant("2", "Cafe Con Leche","2714 N Milwaukee Ave, Chicago, IL 60647","(773)289-4274",1.0f,4.8f,2);
        array[2] = new Restaurant("3","Ryuu Asian BBQ Sushi and Thai","2766 N Milwaukee Ave, Chicago, IL 60647","(773)661-1919",0.9f,4.5f,4);
        array[3] = new Restaurant("4", "The Old Plank", "2700 N Milwaukee Ave, Chicago, IL 60647"," (773)661-2190",0.8f,3.9f,1 );
        array[4] = new Restaurant("5","La Boulangerie & Co Logan","3129 W Logan Blvd, Chicago, IL 60647","(773)666-5880", 0.7f,3.5f,3 );
        array[5] = new Restaurant("6", "Chiya Chai", " 2770 N Milwaukee Ave, Chicago, IL 60647","(773)360-7541", 0.6f,4.9f,3 );
        array[6] = new Restaurant("7", "Puebla Restaurant & Taqueria", "2658 N Milwaukee Ave, Chicago, IL 60647","(773)227-5499", 0.5f,1.9f,1);
        array[7] = new Restaurant("8","Bahn Mi Spot", "2631 N Kedzie Ave, Chicago, IL 60647", "(872)228-1688",0.4f, 2.0f, 1);
        array[8] = new Restaurant("9", "Same Day Cafe","2651 N Kedzie Blvd, Chicago, IL 60647", "(773)342-7040", 0.3f, 2.9f, 2 );
        array[9] = new Restaurant("10", "Chiqueolatte Chicago", "2643 N Milwaukee Ave, Chicago, IL 60647", "(312)846-6839", 0.2f, 4.0f, 3);

        // Return the populated array of Restaurant objects.
        return array;
    }



}

