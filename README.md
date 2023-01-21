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

## Screenshots
<p align="center">
<img width="220" alt="splash_screen" src="https://user-images.githubusercontent.com/89274213/213871458-730cab1d-a39e-41c2-b570-2af9f5ad34f3.png">
<img width="220" alt="home" src="https://user-images.githubusercontent.com/89274213/213871449-4e588e3d-8e0d-411a-a328-e5675f8000f6.png">
<img width="220" alt="map" src="https://user-images.githubusercontent.com/89274213/213871454-7199a955-2443-4c5c-a75e-f3479f9ca7ba.png">
<img width="220" alt="category_list" src="https://user-images.githubusercontent.com/89274213/213871444-0eff1c44-3e96-4689-b24d-7b4f4cd0e1e8.png">
<img width="220" alt="dialog_windorw" src="https://user-images.githubusercontent.com/89274213/213871447-30619d9e-2127-4a32-9c4a-be9f3983d3ec.png">
<img width="220" alt="photo_screen" src="https://user-images.githubusercontent.com/89274213/213871456-87e3efed-2686-485f-a2d7-989c4bfc2134.png">
<img width="220" alt="result_ml" src="https://user-images.githubusercontent.com/89274213/213871457-829841b0-451c-4a39-a404-203b0ef83e85.png">
<img width="220" alt="detail_screen" src="https://user-images.githubusercontent.com/89274213/213871446-dbed575e-d5b4-4bfc-833a-dd28ec478caa.png">
<img width="220" alt="history_detail" src="https://user-images.githubusercontent.com/89274213/213871448-60be8013-fda5-4040-a3f0-1990a08c08f4.png">
</p>

## Localization Support for Latin Kyrgyz
<p align="center">
<img width="220" alt="localization" src="https://user-images.githubusercontent.com/89274213/213871453-de0f15a0-f583-47d6-9307-8e9bc0a0e002.png">
<img width="220" alt="localization_2" src="https://user-images.githubusercontent.com/89274213/213871452-1ee2b957-ec0d-40e4-b8f5-49efdbe68c13.png">
</p>

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
