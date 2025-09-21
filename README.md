# Everything Exchange

A local-first home inventory and sales Android application built with modern Android development practices.

## 🏗️ Architecture

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite) for local storage
- **Navigation**: Navigation Compose
- **Dependency Injection**: Manual DI with Repositories
- **Async Operations**: Kotlin Coroutines and Flow

## 📱 Features

### Core Functionality
- ✅ **Local Account Management**: Create and manage user profiles locally (no cloud dependency)
- ✅ **Home Inventory CRUD**: Add, view, edit, and delete inventory items with photo support
- ✅ **Listings Management**: Create listings from inventory items for sale
- ✅ **Mock Discovery**: Browse nearby items with distance simulation
- ✅ **Offer Workflow**: Make, receive, accept, and decline offers
- ✅ **Export Capabilities**: Export inventory to CSV and PDF formats

### Technical Features
- 📱 Material 3 Design System
- 🗄️ Local SQLite database with Room
- 📸 Photo capture and gallery selection
- 📊 Data export (CSV/PDF)
- 🔄 Reactive UI with StateFlow
- 🧪 Unit tests for core functionality

## 🛠️ Building the App

### Prerequisites
1. **Android Studio** (recommended) or Android SDK Command Line Tools
2. **Java 17** or higher
3. **Gradle** (included via wrapper)

### Quick Start
1. Clone the repository:
   ```bash
   git clone https://github.com/avinahome/Everything-Exchange.git
   cd Everything-Exchange
   ```

2. Build using the included script:
   ```bash
   ./build.sh
   ```

3. Or build manually with Gradle:
   ```bash
   ./gradlew assembleDebug
   ```

### APK Location
After successful build:
- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release.apk`

## 📦 Installation

### For Developers
1. Enable "Unknown Sources" in Android Settings > Security
2. Transfer the APK to your device
3. Tap the APK file to install

### For End Users
1. Download the APK from releases
2. Enable installation from unknown sources if prompted
3. Install and launch the app

## 🎯 Usage Guide

### First Launch
1. Open the app and go to the "Profile" tab
2. Create your local account with username, email, and location
3. Start adding items to your inventory

### Managing Inventory
1. Go to "Inventory" tab
2. Tap the "+" button to add new items
3. Fill in item details (name, description, category, price)
4. Use Export buttons to save your inventory as CSV or PDF

### Discovering Items
1. Visit the "Discover" tab to see mock nearby items
2. Browse available items with distance information
3. Make offers on items you're interested in

### Managing Offers
1. Check the "Offers" tab for sent and received offers
2. Accept or decline offers you've received
3. Monitor status of offers you've sent

## 🗂️ Project Structure

```
app/src/main/java/com/everythingexchange/app/
├── data/
│   ├── database/          # Room entities, DAOs, and database
│   └── repository/        # Data access layer
├── presentation/
│   ├── navigation/        # Navigation setup
│   ├── screens/           # Compose UI screens
│   ├── theme/             # Material 3 theming
│   └── viewmodel/         # MVVM ViewModels
├── utils/                 # Utility classes (photos, export)
└── MainActivity.kt        # Main entry point
```

## 🧪 Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## 🚀 Development Roadmap

### Completed ✅
- Basic app structure and navigation
- Local user account system
- Inventory CRUD operations
- Mock discovery and offers
- Export functionality foundation
- Material 3 UI implementation

### Future Enhancements 🔄
- Photo capture and image handling
- Enhanced search and filtering
- Real-time offer notifications
- Backup and restore functionality
- Advanced export options
- Performance optimizations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 💡 Technical Notes

### Database Schema
- **Users**: Local account information
- **InventoryItems**: User's items with metadata
- **Listings**: Items for sale with pricing
- **Offers**: Bid system for transactions

### Local-First Design
This app operates entirely offline with local SQLite storage. No internet connection or external services are required for core functionality.

### Mock Features
The discovery system currently shows mock data to demonstrate the interface. In a production version, this would integrate with a real discovery service or local network sharing.

---

*Built with ❤️ using modern Android development practices*
