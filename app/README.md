# Store App

This is an Android application that fetches product data from the Fake Store API and displays it in a user-friendly manner. The app demonstrates API integration, a clean user interface with a RecyclerView, a detailed product screen, search functionality, error handling, and more.

## Features

1. **API Integration:**
    - Utilizes the Fake Store API to fetch product data.
    - Endpoint: [https://fakestoreapi.com/products](https://fakestoreapi.com/products)

2. **User Interface:**
    - Displays fetched product data in a RecyclerView.
    - Each item in the RecyclerView shows the product name, price, description, and an image.

3. **Details Screen:**
    - Shows more information about a selected product when clicked.
    - Displays product name, price, description, image, and other relevant information.

4. **Search Functionality:**
    - Allows users to search for products by name.
    - Filters products in the RecyclerView dynamically as per category the user chooses.

5. **Error Handling:**
    - Implements appropriate error handling for API calls and network issues.
    - Displays a meaningful error message to the user in case of failure.

6. **Loading Indicator:**
    - Displays a loading indicator while fetching data from the API.
    - The indicator is visible until the data is completely loaded.

7. **Code Quality:**
    - Written using clean, readable, and well-structured code.
    - Follows the MVVM (Model-View-ViewModel) architecture pattern.
    - Uses appropriate design patterns and best practices for Android development.

8. **Additional Features (Optional):**
    - Adds a feature to sort products by category.

## Installation and Setup

1. **Open the project in Android Studio:**
    - Open Android Studio.
    - Select "File > Open...".
    - Navigate to the unzipped folder and select the `store-app` folder.

2. **Build and run the project:**
    - Ensure you have an Android emulator set up or a physical device connected.
    - Click the "Run" button in Android Studio.

## Usage

- Upon launching the app, the main screen displays a list of products fetched from the Fake Store API.
- Tap on any product to view its details.
- Use the search bar at the top to filter products by name.
- Use the category filter to filter products dynamically as per selected category.

## Dependencies

- [Retrofit](https://square.github.io/retrofit/) - For API calls.
- [Glide](https://github.com/bumptech/glide) - For loading images.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - For observing data changes.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - For managing UI-related data.
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) - For displaying lists of data.

## Notes

- Make sure you have a stable internet connection while using the app as it fetches data from an external API.
- The app follows the MVVM architecture pattern for better code maintenance and testability.
