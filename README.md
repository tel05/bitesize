# 🍽️ Bite-size — Restaurant Finder CLI App

Bite-size is a Java command-line application that helps users discover nearby restaurants based on a U.S. ZIP code. It integrates the **Google Geocoding API** to retrieve real-time location data, and supports sorting results by **distance**, **rating**, or **price level**.

---

## 🚀 Features

- 🔎 Search by U.S. ZIP code
- 📍 Get latitude and longitude using Google Geocoding API
- ⚖️ Sort results by:
  - Distance (nearest first)
  - Rating (highest first)
  - Price level (cheapest first)
- 📞 View restaurant details: address, phone number, rating, and price level
- Robust input validation and graceful error handling

---

## 🧰 Technologies Used

- **Java 21**
- **Google Maps Java Client Library**
  - Geocoding API
- **Priority Queues** from [Sedgewick & Wayne’s Algorithms](https://algs4.cs.princeton.edu/)
  - `MinPQ.java` and `MaxPQ.java` (Binary Heaps)

---

## 📦 Installation

### 1. Clone the repository
```bash
git clone https://github.com/your-username/bitesizeapp.git
cd bitesizeapp
```

### 2. Add your Google Maps API Key
Set your API key as an environment variable before running the app:
```bash
export GOOGLE_API_KEY=your_actual_api_key_here
```

### 3. Compile the project
Make sure you have Java 21 and Maven or your preferred build system configured.

```bash
javac -cp path/to/google-maps-services.jar:. com/github/tel05/bitesizeapp/*.java
```

### 4. Run the app
```bash
java -cp path/to/google-maps-services.jar:. com.github.tel05.bitesizeapp.Main
```

---

## 🏗️ Project Structure

```
📦 bitesizeapp/
 ┣ 📄 Main.java              // CLI logic + user flow
 ┣ 📄 Geocoding.java         // Gets coordinates from ZIP code
 ┣ 📄 NearbyRestaurants.java // (planned) integration with Places API
 ┣ 📄 Restaurant.java        // Stores restaurant attributes (name, address, rating, etc.)
 ┣ 📄 RestaurantData.java    // Mock data (optional, for testing)
 ┣ 📄 ZipCodeValidator.java  // Validates ZIP code format
 ┣ 📄 MinPQ.java             // Min-priority queue (distance, price)
 ┣ 📄 MaxPQ.java             // Max-priority queue (rating)
```

---

## 🧪 Sample Flow

```
---------------------- Bite-size -------------------------
 The quick and easy way to discover restaurants near you
----------------------------------------------------------
Enter your zip code: 60647
Latitude: 41.9243, Longitude: -87.7012
```

---

## 📌 Notes

- This app currently runs as a CLI but is designed to be portable to a GUI or mobile interface.
- API responses are rate-limited by Google. Be sure to handle your quota accordingly.

---

## 📈 Future Enhancements

- 🍽️ Integrate Google Places API for dynamic restaurant discovery (in progress 🚧)
- 🧾 Filter by cuisine/ Include open hours
- 🛑 Caching API results to reduce repeated requests
- 📱 Convert to an Android app using Kotlin + Maps SDK

---

## 🤝 Acknowledgements

- [Google Maps Java Client](https://github.com/googlemaps/google-maps-services-java)
- [Sedgewick & Wayne’s Algorithms](https://algs4.cs.princeton.edu/home/)

---

## 👩‍💻 Author

**Tania Lopez**  
Aspiring Software Engineer | CS Grad Student  
