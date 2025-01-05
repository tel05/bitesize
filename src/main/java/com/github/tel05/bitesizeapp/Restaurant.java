package com.github.tel05.bitesizeapp;

/**
 * This class represents a restaurant with key details such as ID, name, address, phone number, distance from a user, rating, and price level.
 * The class includes validation for these details and provides getter methods to retrieve them. It also implements 'equals()', 'hashCode()', and 'toString()' methods
 * for proper comparison, hashing, and display of restaurant information.
 */
public class Restaurant {
    // Instance variables to store restaurant details.
    private final String id;
    private final String name;
    private final String address;
    private final String phoneNumber;
    private final float distance;
    private final float rating;
    private final int priceLevel;

    /**
     * Constructor to initialize a Restaurant object with necessary details.
     * Performs validation on input values to ensure correctness.
     *
     * @param id    the unique identifier for the restaurant
     * @param name  the name of the restaurant
     * @param address the address of the restaurant
     * @param phoneNumber the phone number of the restaurant
     * @param distance the distance from the user to the restaurant (in miles)
     * @param rating the rating of the restaurant (1.0 - 5.0)
     * @param priceLevel the price level of the restaurant (0 - 4)
     * @throws IllegalArgumentException if any input value is invalid
     */
    public Restaurant(String id, String name, String address,
                      String phoneNumber, float distance, float rating, int priceLevel){

        // check if inputs are valid (no null or empty)
        if(id == null || id.isEmpty()) throw new IllegalArgumentException("Restaurant ID cannot be null or empty.");
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Restaurant name cannot be null or empty.");
        if(address == null || address.isEmpty()) throw new IllegalArgumentException("Address cannot be null or empty.");
        if(phoneNumber == null || phoneNumber.isEmpty()) throw new IllegalArgumentException("Phone number cannot be null or empty.");
        // In Google API distances are positive range, ratings are between 1.0 and 5.0 and
        /* price level scale:
            0 Free
            1 Inexpensive
            2 Moderate
            3 Expensive
            4 Very Expensive
        */
        // Validate that the distance is non-negative
        if(distance < 0) throw new IllegalArgumentException("Distance cannot be a negative miles.");
        // Validate that the rating is within the range of 1.0 to 5.0
        if(rating < 1.0f || rating > 5.0f) throw new IllegalArgumentException("Rating has to be within range: 1.0 - 5.0");
        // Validate that the price level is between 0 and 4
        if(priceLevel < 0 || priceLevel > 4) throw new IllegalArgumentException("Price level has to be within range: 0 - 4");

        // Initialize the instance variables
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.distance = distance;
        this.rating = rating;
        this.priceLevel = priceLevel;
    }
    // Getter methods for each property
    public String getId(){ return this.id;}
    public String getName(){ return this.name;}
    public String getAddress() { return this.address;}
    public String getPhoneNum(){ return this.phoneNumber;}
    public float getDistance(){ return this.distance;}
    public float getRating(){ return this.rating;}
    public int getPriceLevel() {return this.priceLevel;}

    /**
     * Compares this restaurant to another to determine equality.
     * Two restaurants are considered equal if they have the same id, name, address, phone number,
     * distance, rating, and price level.
     *
     * @param obj the object to compare with
     * @return true if both objects are equal, false otherwise
     */
    @Override
    public boolean equals (Object obj){
        // Check if the given object is of the same type and compare the details
        if(!(obj instanceof Restaurant other)) return false;
        return other.id.equals(id)
                && other.name.equals(name)
                && other.address.equals(address)
                && other.phoneNumber.equals(phoneNumber)
                && Float.compare(other.distance, distance) == 0
                && Float.compare(other.rating, rating) == 0
                && other.priceLevel == priceLevel;
    }

    /**
     * Generates a hash code for this Restaurant object based on its details.
     * The hash code is used for efficient storage and retrieval in hash-based collections.
     *
     * @return a hash code for this Restaurant object
     */
    @Override
    public int hashCode(){
        int result = 17; // Initialize with a prime number
        result= 37 * result + id.hashCode(); // Hash the ID
        result= 37 * result + name.hashCode(); // Hash the name
        result= 37 * result + phoneNumber.hashCode(); // Hash the phone number
        result= 37 * result + Float.hashCode(distance); // Hash the distance
        result= 37 * result + Float.hashCode(rating); // Hash the rating
        result= 37 * result + priceLevel; // Hash the price level
        return result;
    }

    /**
     * Returns a string representation of this Restaurant object.
     * The string includes the name, address, phone number, distance, rating, and price level.
     *
     * @return a string representation of the restaurant's details
     */
    @Override
    public String toString(){
        return String.format("Name: %s\n" +
                        "Address: %s\n" +
                        "Phone number: %s\n" +
                        "Distance: %.2f mi\n" +
                        "Rating: %.1f\n" +
                        "Price level: %d\n",
                getName(),getAddress(),getPhoneNum(),getDistance(),getRating(),getPriceLevel());
    }
}