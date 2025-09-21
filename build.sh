#!/bin/bash

# Everything Exchange Android App Build Script
# This script builds the Android APK for the Everything Exchange app

echo "Building Everything Exchange Android App..."

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "Warning: ANDROID_HOME not set. Please install Android SDK."
    echo "You can download Android Studio from: https://developer.android.com/studio"
    echo "Or install command line tools from: https://developer.android.com/studio/command-line"
fi

# Create gradle wrapper if it doesn't exist
if [ ! -f "gradlew" ]; then
    echo "Creating Gradle wrapper..."
    gradle wrapper
fi

# Make gradlew executable
chmod +x gradlew

# Clean and build the project
echo "Cleaning project..."
./gradlew clean

echo "Building debug APK..."
./gradlew assembleDebug

echo "Building release APK..."
./gradlew assembleRelease

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "Debug APK: app/build/outputs/apk/debug/app-debug.apk"
    echo "Release APK: app/build/outputs/apk/release/app-release.apk"
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi