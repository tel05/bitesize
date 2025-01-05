package com.github.tel05.bitesizeapp;

import java.util.*;

import static com.github.tel05.bitesizeapp.RestaurantData.getData;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String API_KEY = System.getenv("GOOGLE_API_KEY");



    /**
     * Prompts the user to input the number of results they want to view (1-10)
     * Ensures input is an integer and within the valid range.
     * @return the number of results specified by the user.
     */
    public static int numOfResults() {
        // ask for number of results range 1 - 10

        int listLen = 0;
        do {
            System.out.println("----------------------------------------------------------");
            System.out.print("Number of results (max 10): ");
            // Check if input is a valid integer.
            if (scanner.hasNextInt()) {
                listLen = scanner.nextInt();
                // Validate range
                if (listLen < 1 || listLen > 10) {
                    System.out.println("Not a valid option. Enter a number between 1 and 10.");
                }
            } else {
                System.out.println("Not a valid option. Enter a number between 1 and 10.");
                scanner.next(); // Clear invalid input
            }
        }
        while (listLen < 1 || listLen > 10);
        return listLen;
    }

    /**
     * Prompts the user to enter a zip code.
     * @return the zip code entered by the user.
     */
    public static String zipcode() {
        System.out.print("Enter your zip code: ");
        return scanner.next();
    }

    /**
     * Displays sorting options to the user and retrieves their choice.
     * Options include sorting by distance, rating, or price level.
     *
     * @return The chosen sorting option as an integer (1 for distance, 2 for rating, 3 for price level).
     */
    public static int filter() {
        int filter = 0;
        do {
            System.out.println("----------------------------------------------------------");
            //print choices
            System.out.println("Sort by:");
            System.out.println("1. Distance");
            System.out.println("2. Rating");
            System.out.println("3. Price level");

            // Check if input is a valid integer
            if (scanner.hasNextInt()) {
                filter = scanner.nextInt();
                // Validate range
                if (filter < 1 || filter > 3) {
                    System.out.println("Not a valid option. Enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Not a valid option. Enter a number between 1 and 3.");
                scanner.next(); // Clear invalid input
            }
        }
        while (filter < 1 || filter > 3);
        return filter;

    }

    /**
     * Displays the sorted restaurant results based on the chosen filter (distance, rating, or price level).
     * @param filter Sorting filter chosen by the user.
     * @param distancePQ List of restaurants sorted by distance.
     * @param ratingPQ List of restaurants sorted by rating.
     * @param pricePQ List of restaurants sorted by price level.
     * @param numOptions Number of results to display.
     * @param restaurantMap Map of restaurant IDs to Restaurant objects.
     */
    public static void displayResults(int filter, List<String> distancePQ, List<String> ratingPQ, List<String> pricePQ, int numOptions, Map<String, Restaurant> restaurantMap) {
        List<String> sortedList = filter == 1 ? distancePQ : filter == 2 ? ratingPQ : pricePQ;
        for (int i = 1; i <= numOptions; i++) {
            String id = sortedList.get(i - 1);
            Restaurant restaurant = restaurantMap.get(id);
            System.out.printf("%d. %s \n", i, restaurant.getName());
            switch (filter) {
                case 1 -> System.out.printf("Distance: %.2f mi\n", restaurant.getDistance());
                case 2 -> System.out.printf("Rating: %.1f\n", restaurant.getRating());
                case 3 -> System.out.printf("Price: %d\n", restaurant.getPriceLevel());
            }
            System.out.println();
        }
    }

    /**
     * Prompts the user to make a choice for viewing more details or start new search or new sorting option.
     * @param numOptions The number of displayed options.
     * @return The user's choice.
     */
    public static int isValidChoice(int numOptions) {
        int input;
        do {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= 0 && input <= numOptions + 1) {
                    return input;
                } else {
                    System.out.printf("Not a valid option. Enter a number between 0 and %d\n", numOptions + 1);
                }
            }else{
                System.out.println("Not a valid option. Please enter a valid number.\n");
            }
        } while (true);
    }

    /**
     * Prints the app title banner.
     */
    public static void printTitle(){
        System.out.println("---------------------- Bite-size ------------------------- ");
        System.out.println(" The quick and easy way to discover restaurants near you");
        System.out.println("----------------------------------------------------------");
    }

    public static void main(String[] args) {

        boolean exit = false;
        GeoApiContext context = new GeoApiContext.Builder()
                                .apiKey(API_KEY)
                                .build();

        while (!exit) {
            printTitle();
            String zipcode = zipcode();
            if(zipcode.equalsIgnoreCase("exit")){
                exit = true;
                continue;
            }
            // check validity of zip code else reprompt 
            if(!ZipCodeValidator.isValidZipCode(zipcode)){
                System.out.println("Invalid zipcode format.\n");
                continue;  
            }

            // request coordinates with valid zip code.
            Geocoding geocoder = new Geocoding(context);
            double[] latLong = geocoder.getLatLongByZipCode(zipcode);
            if (latLong != null) {
                System.out.println("Latitude: " + latLong[0] + ", Longitude: " + latLong[1]);
            } else {
                System.out.println("Could not find coordinates for zipcode " + zipcode);
            }



            
                    
            
            
            int numOptions = numOfResults();

            // Retrieve restaurant data based on zip code and populate the restaurant map.
            Restaurant[] restaurantsList = getData();
            HashMap<String, Restaurant> restaurantMap = new HashMap<>();
            for (Restaurant restaurant : restaurantsList) {
                restaurantMap.put(restaurant.getId(), restaurant);
            }

            // create MinPQ for distance (max 10 items) ----------------------------------------------------------------------------------------
            // Priority Queues for sorting by distance, price level, and rating
            Comparator<String> distanceComparator = Comparator.comparing((String id)-> restaurantMap.get(id).getDistance())
                    .thenComparing(id -> restaurantMap.get(id).getId());
            MinPQ<String> distanceMinPQ = new MinPQ<>(distanceComparator);


            for (String id : restaurantMap.keySet()) {
                distanceMinPQ.insert(id);
            }

            // Build List subset of nearest restaurants to build priceLevel and rating PQs
            List<String> distancePQ = new ArrayList<>();
            for (int i = 0; i < numOptions && !distanceMinPQ.isEmpty(); i++) {
                distancePQ.add(distanceMinPQ.delMin());
            }

            // MinPQ for price level ----------------------------------------------------------------------------------------
            Comparator<String> priceComparator = Comparator.comparing((String id) -> restaurantMap.get(id).getPriceLevel())
                    .thenComparing(id -> restaurantMap.get(id).getId());
            MinPQ<String> priceMinPQ = new MinPQ<>(priceComparator);
            for (String id : distancePQ) {
                priceMinPQ.insert(id);
            }

            List<String> pricePQ = new ArrayList<>();
            while (!priceMinPQ.isEmpty()) {
                Restaurant rest = restaurantMap.get(priceMinPQ.delMin());
                pricePQ.add(rest.getId());
            }

            // MaxPQ for rating ----------------------------------------------------------------------------------------
            Comparator<String> ratingComparator = Comparator.comparing((String id) -> restaurantMap.get(id).getRating())
                    .thenComparing(id -> restaurantMap.get(id).getId());
            MaxPQ<String> ratingMaxPQ = new MaxPQ<>(ratingComparator);
            for (String id : distancePQ) {
                ratingMaxPQ.insert(id);
            }

            List<String> ratingPQ = new ArrayList<>();

            while (!ratingMaxPQ.isEmpty()) {
                Restaurant rest = restaurantMap.get(ratingMaxPQ.delMax());
                ratingPQ.add(rest.getId());
            }


            String[] choices = new String[]{"empty", "distance in miles (low to high)", "rating (high to low) \nrange: 1-5", "price level (low to high) \n0:inexpensive to 4:expensive "};
            int restaurantNum;
            // Print restaurants by sorting option
            while (true) {
                // Ask user for sorting option
                int filter = filter();
                while(true) {
                    System.out.println();
                    System.out.println("----------------------------------------------------------");
                    System.out.printf("Sorted by %s\n", choices[filter]);
                    System.out.println();
                    displayResults(filter, distancePQ, ratingPQ, pricePQ, numOptions, restaurantMap);

                    System.out.println();
                    System.out.println("Enter number of the restaurant to view details.");
                    System.out.println("OR");
                    System.out.println("Enter '0' to start a new search.");
                    System.out.println("OR");
                    System.out.printf("Enter %d to select new sorting option.\n", numOptions + 1);
                    System.out.print("Option: ");
                    restaurantNum = isValidChoice(numOptions);


                    if (restaurantNum == numOptions + 1 || restaurantNum == 0)  break;
                    while (true) {
                        int input;
                        // Display restaurant details depending on filter
                        System.out.println("----------------------------------------------------------");
                        // Distance
                        if (filter == 1) {
                            System.out.println(restaurantMap.get(distancePQ.get(restaurantNum - 1)));
                        }
                        // Rating
                        else if (filter == 2) {
                            System.out.println(restaurantMap.get(ratingPQ.get(restaurantNum - 1)));
                        }
                        // Price level
                        else if (filter == 3) {
                            System.out.println(restaurantMap.get(pricePQ.get(restaurantNum - 1)));
                        }
                        System.out.println("Press '0' to go back to restaurant list: ");
                        if (scanner.hasNextInt()) {
                            input = scanner.nextInt();
                            if (input == 0) break;
                        } else {
                            System.out.println("Not a valid option. Press '0' to go back to restaurant list.\n");
                        }

                    }
                }
                if(restaurantNum == 0) break;

            }
        }
        scanner.close();
        context.shutdown();
    }
}