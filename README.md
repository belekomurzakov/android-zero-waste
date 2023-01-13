# Zero Waste (Android)
Developed as part of the EBC-VA2 - Application Development for Android 2 course, a waste management mobile app, that helps users locate the nearest waste disposal options on the map, according to specific waste types.
<br>
<br>
Keep track of your sorting history and take advantage of the cutting-edge machine learning feature that can identify objects, making the most out of your waste and contributing to a cleaner environment.

## What I Learned
- Adopted MVVM architecture with Dependency Injection using Koin for efficient design
- Utilized Retrofit for seamless communication with API server
- Implemented cutting-edge machine learning model using ML Kit
- Incorporated Room for SQLite database persistence and DataStore for small value persistence
- Built UI with Jetpack Compose, incorporating material design 2 & 3

## Main Features


## Screenshots


## Repo Structure
```
/
├─ app/src/
│  ├─ androidTest                   # UI Tests
│  ├─ test                          # Unit Tests
│  └─ main/
│     ├─ assets/                    # Machine Learning Model
│     ├─ java/omurzakov/zerowaste/  # App
│     │  ├─ architecture/
│     │  ├─ communication/
│     │  ├─ di                      # Dependency Injection
│     │  ├─ map/
│     │  ├─ ml/                     # Machine Learning
│     │  ├─ models/
│     │  ├─ navigation/
│     │  ├─ ui/
│     │  │  ├─ activities
│     │  │  ├─ elements
│     │  │  ├─ theme
│     │  │  └─ screens
│     │  │     ├─ camera            
│     │  │     ├─ category          
│     │  │     ├─ detail            
│     │  │     ├─ historydetail     
│     │  │     ├─ home              
│     │  │     └─ map               
│     │  └─ utils/                  # Bitmap, Constants, DataStoreManager              
│     └─ res/                       # XML views
└─ README.md                        # This file
```