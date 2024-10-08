# Todo List Application

![Project Logo](![image](https://github.com/user-attachments/assets/8fc2cfd9-c75d-4a09-8c59-786a5b741bc8)
) <!-- Add project logo if you have one -->

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Architecture](#architecture)
- [Dependency Injection with Dagger](#dependency-injection-with-dagger)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Todo List Application is a simple yet powerful task management tool built using Jetpack Compose. It allows users to create, edit, delete, and manage tasks efficiently. The app utilizes Dagger for dependency injection, promoting modularity and testability.

## Features

- Add new tasks with a name, due date, description, and priority level.
- Edit existing tasks.
- Delete tasks that are no longer needed.
- View tasks sorted by priority.
- User-friendly interface built with Jetpack Compose.
- Responsive design for a seamless user experience.

## Technologies Used

- **Kotlin**: Primary programming language for Android development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Dagger**: Dependency injection framework for managing app components.
- **Room**: SQLite object mapping library for local database management.
- **Coroutines**: For handling asynchronous programming.
- **ViewModel**: Architecture component to store UI-related data in a lifecycle-conscious way.

## Getting Started

To run this project locally, follow these steps:

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/todo-list-app.git
   cd todo-list-app


2. **Open the project in Android Studio**:

   - Open Android Studio.
   - Select "Open an existing project".
   - Navigate to the project folder and click "OK".

3. **Set up the required dependencies**:

   - Ensure that you are using Android Studio Arctic Fox or later.
   - The project requires Kotlin 1.5 or later, and Jetpack Compose.
   - Perform a Gradle Sync by clicking on "Sync Now" if prompted.

4. **Run the app**:

   - Select an Android device or emulator.
   - Click the "Run" button in Android Studio to launch the app.

## Architecture

This project follows the **MVVM (Model-View-ViewModel)** architecture pattern, which separates the app into three main components:

- **Model**: Manages the apps data layer and business logic (e.g., Room Database).
- **ViewModel**: Stores and manages UI-related data, fetching tasks and handling user actions.
- **View**: The UI layer, built using Jetpack Compose, that observes ViewModel state changes and displays tasks to the user.

### Folder Structure

      ```bash
      .
      ├── data
      │   ├── dao             # Room Database DAO
      │   ├── entities        # Task entity data model
      │   └── repository      # Repository that handles data operations
      ├── di                  # Dagger dependency injection modules
      ├── ui
      │   ├── screens         # Compose screens (TaskListScreen, AddTaskScreen)
      │   ├── theme           # App theme and styles
      │   └── viewmodels      # ViewModels for managing app state
      └── utils               # Utility classes and helper functions



## Dependency Injection with Dagger

This project uses **Dagger** to handle dependency injection, making the app modular, easier to test, and more maintainable. Dependency injection allows for cleaner code by decoupling dependencies from the components that use them.

## Screenshots

### Light Mode & Dark Mode Comparison

| Light Mode | Dark Mode |
|------------|-----------|
| ![Light Mode](https://github.com/user-attachments/assets/cfb02d34-562f-484d-b44a-6d7e3b4fea1a) | ![Dark Mode]([path_to_dark_mode_image](https://github.com/user-attachments/assets/eedd0d4b-9a3c-4143-8c72-0ddbd2371eac)) |
| ![Light Mode](https://github.com/user-attachments/assets/cfb02d34-562f-484d-b44a-6d7e3b4fea1a) | ![Dark Mode]([path_to_dark_mode_image](https://github.com/user-attachments/assets/eedd0d4b-9a3c-4143-8c72-0ddbd2371eac)) |
| ![Light Mode](https://github.com/user-attachments/assets/cfb02d34-562f-484d-b44a-6d7e3b4fea1a) | ![Dark Mode]([path_to_dark_mode_image](https://github.com/user-attachments/assets/eedd0d4b-9a3c-4143-8c72-0ddbd2371eac)) |
| ![Light Mode](https://github.com/user-attachments/assets/cfb02d34-562f-484d-b44a-6d7e3b4fea1a) | ![Dark Mode]([path_to_dark_mode_image](https://github.com/user-attachments/assets/eedd0d4b-9a3c-4143-8c72-0ddbd2371eac)) |


![image](https://github.com/user-attachments/assets/e6000e1c-43ac-4a0e-ac80-1aed0a58032a)

![image](https://github.com/user-attachments/assets/79826eb6-0aaf-443e-bd09-ea9c6ad01a03)
![image](https://github.com/user-attachments/assets/6a79a8f8-8b34-449e-9c37-867c791d5f30)
![image](https://github.com/user-attachments/assets/02c595ae-7a92-4dda-aaf3-b0cf6995a3b1)




### Setting Up Dagger in the Project

1. **Add Dagger Dependencies**:

   Add the following dependencies to your `build.gradle` file (app-level):

   ```gradle
   dependencies {
       implementation 'com.google.dagger:dagger:2.x'  // Replace 'x' with the latest version
       kapt 'com.google.dagger:dagger-compiler:2.x'
   }

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contact

If you have any questions or feedback, feel free to contact:

- **Author**: Uphar gaur
- **Email**: uphargaur@gmail.com 
- **GitHub**: [https://github.com/yourusername](https://github.com/uphargaur)

---

## Acknowledgements

- Thanks to Jetpack Compose and Dagger communities for their excellent documentation and support.
- Special thanks to all contributors.

---

## Future Enhancements

- Add task reminders using WorkManager.
- Implement a calendar view for easier task scheduling.
- Add notification alerts for due dates.
- Improve UI animations and transitions.

---

## Troubleshooting

If you encounter any issues:

1. Ensure that you are using the correct Android Studio version.
2. Run a Gradle sync if you face dependency issues.
3. Clear the Android Studio cache (`File -> Invalidate Caches/Restart`).
4. Check if your Android SDK is updated to the required API level.

If issues persist, feel free to open a GitHub issue or contact me at the above-mentioned contact details.
