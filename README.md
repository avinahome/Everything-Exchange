# Everything Exchange

A comprehensive home inventory and sales Android application built with modern Android development practices.

## Features

### Core Functionality
- **Local User Authentication**: Secure user registration and login with encrypted password storage
- **Inventory Management**: Full CRUD operations for home inventory items
- **Photo Integration**: Camera integration for item photos with proper file provider support
- **Item Listings**: Create and manage sales listings from inventory items
- **Discovery Feature**: Browse and search available items from other users
- **Offer System**: Make and manage offers on items
- **Export Capabilities**: Export inventory data to CSV and PDF formats

### Technical Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3 Design
- **Architecture**: MVVM (Model-View-ViewModel) with Repository pattern
- **Database**: Room Database for local data persistence
- **Navigation**: Jetpack Navigation Compose
- **Concurrency**: Kotlin Coroutines and StateFlow
- **Image Loading**: Coil for efficient image loading and caching
- **Camera**: CameraX for photo capture functionality
- **Permissions**: Accompanist Permissions for runtime permission handling
- **Export**: OpenCSV for CSV export, Android PdfDocument for PDF generation

## Project Structure

```
app/src/main/java/com/everythingexchange/app/
├── data/
│   ├── entities/        # Room database entities
│   ├── dao/            # Data Access Objects
│   └── database/       # Database configuration and converters
├── repository/         # Repository pattern implementations
├── viewmodel/          # ViewModels for UI state management
├── ui/
│   ├── screens/        # Compose UI screens
│   ├── components/     # Reusable UI components
│   ├── navigation/     # Navigation setup
│   └── theme/          # Material 3 theming
└── utils/              # Utility classes (encryption, export, camera)
```

## Database Schema

### Entities
- **User**: User accounts with encrypted passwords
- **InventoryItem**: Home inventory items with photos and metadata
- **Listing**: Sales listings linked to inventory items
- **Offer**: Offers made on listings with status tracking

### Relationships
- Users can have multiple inventory items
- Inventory items can have associated listings
- Listings can receive multiple offers
- All relationships maintain referential integrity with foreign keys

## Security Features
- SHA-256 password hashing
- Local data storage with Room database
- Proper file provider configuration for photo sharing
- Runtime permission handling for camera and storage access

## Export Features
- **CSV Export**: Complete inventory data export with sharing capabilities
- **PDF Export**: Formatted inventory reports using Android's built-in PDF generation
- **File Sharing**: Integration with Android's share intent system

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 24 (Android 7.0) or higher
- Gradle 7.6+

### Building the App
1. Clone the repository
2. Open in Android Studio
3. Let Gradle sync complete
4. Build and run on device or emulator

### Installation
The app generates a signed APK that can be installed on any Android device running API 24+.

## Testing
- Unit tests for utility functions (password hashing, data validation)
- Instrumented tests for database operations
- UI tests for critical user flows

## Permissions Required
- **CAMERA**: For taking photos of inventory items
- **WRITE_EXTERNAL_STORAGE**: For saving export files (Android < 10)
- **READ_EXTERNAL_STORAGE**: For accessing saved photos and files

## Future Enhancements
- Cloud synchronization
- Real-time messaging for offers
- Advanced search and filtering
- User profiles and ratings
- Push notifications
- Barcode scanning for easy item entry

## License
This project is licensed under the MIT License - see the LICENSE file for details.
