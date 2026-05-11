# Car Rental Android Application

A professional, feature-rich **Car Rental Management System** built for the Android platform. This application provides a seamless experience for users to browse, search, and manage car rentals with a robust backend powered by **Firebase**.

## 🚀 Features

### User Management
*   **Secure Authentication**: User registration and login powered by Firebase Authentication.
*   **Password Recovery**: Integrated "Forget Password" functionality for user convenience.
*   **Profile Management**: "My Account" section for users to view and manage their personal details.

### Car Exploration
*   **Dynamic Car Listing**: Browse available vehicles through a responsive `RecyclerView` interface.
*   **Detailed View**: Comprehensive details for each car, including specifications and images.
*   **Advanced Search**: Quickly find specific vehicles using the dedicated search feature.

### Technical Excellence
*   **Real-time Database**: Leverages Firebase Firestore and Realtime Database for synchronized data management.
*   **Efficient Networking**: Uses Volley for fast and reliable API communication.
*   **Smooth Image Loading**: Integrated Glide library for optimized image fetching and caching.
*   **Modern UI**: Built with Material Design components and support for Edge-to-Edge display.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Java |
| **Platform** | Android (Min SDK: 28, Target SDK: 34) |
| **Backend** | Firebase (Auth, Firestore, Realtime DB, Analytics) |
| **Networking** | Volley |
| **Image Loading** | Glide |
| **UI Components** | Material Design, RecyclerView, ConstraintLayout |
| **Build System** | Gradle (Kotlin DSL) |

---

## 📦 Installation & Setup

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/Mohammad2466/CarRentalFinalProject.git
    ```

2.  **Open in Android Studio**
    *   Launch Android Studio and select **Open**.
    *   Navigate to the cloned directory and select the root folder.

3.  **Firebase Configuration**
    *   Create a new project in the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android App to your Firebase project with the package name `com.example.carrentalfinalproject`.
    *   Download the `google-services.json` file and place it in the `app/` directory.

4.  **Build and Run**
    *   Sync the project with Gradle files.
    *   Run the application on an emulator or a physical Android device.

---

## 📂 Project Structure

```text
app/
├── src/main/java/com/example/carrentalfinalproject/
│   ├── BaseActivity.java      # Base activity for shared logic
│   ├── MainActivity.java      # Main entry point and car listing
│   ├── Login.java             # User login logic
│   ├── Register.java          # User registration logic
│   ├── Details.java           # Car details view
│   ├── Search.java            # Search functionality
│   └── ...
├── src/main/res/layout/       # XML layout definitions
└── build.gradle.kts           # Dependency management
```

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request or open an issue for any improvements or bug fixes.

## 📄 License

This project is part of a Final Mobile Project. Please contact the repository owner for licensing details.
