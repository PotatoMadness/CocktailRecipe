# CocktailRecipe

칵테일 레시피 api (www.thecocktaildb.com) 를 사용한 스터디용 앱

<img src="https://private-user-images.githubusercontent.com/42571328/340207507-e38952ba-7a32-4e87-a32f-24ce4cac0242.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTg2MDc5MjYsIm5iZiI6MTcxODYwNzYyNiwicGF0aCI6Ii80MjU3MTMyOC8zNDAyMDc1MDctZTM4OTUyYmEtN2EzMi00ZTg3LWEzMmYtMjRjZTRjYWMwMjQyLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MTclMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjE3VDA3MDAyNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWQ5Yzg3NTc2MWEzNzcxYTAxN2Y5ZjEyOThmMzA0Y2ZjMGJjNTQwZmZiYmU4YjUwM2M1NTBkYjc0NjNlN2Q1OWYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.Er-ikGeKtWbOWm7Fmpz3NctmsZ4VJtS-4AdkIAk56Rg" alt="drawing" width="200" height="420" />

## Screens

* **[Home](https://github.com/PotatoMadness/CocktailRecipe/tree/master/feature/home)**
* **[Favorite](https://github.com/PotatoMadness/CocktailRecipe/tree/master/feature/favorite)**
* **[MyRecipe](https://github.com/PotatoMadness/CocktailRecipe/tree/master/feature/myrecipe)**
* **[Detail](https://github.com/PotatoMadness/CocktailRecipe/tree/master/feature/detail)**

### Language

- Kotlin

### Libraries

- AndroidX
  - Activity & Activity Compose
  - AppCompat
  - Core
  - Navigation

- Kotlin Libraries (Coroutine, Serialization)
- Compose
  - Material3
  - Navigation

- Firebase
  - Crashlytics
  - Realtime Database
    
- Dagger & Hilt
- Room
- Square (Retrofit, OkHttp)

### structure

```mermaid
flowchart TD
    A[app] --> B[feature:home]
    A[app] --> C[feature:favorite]
    A[app] --> D[feature:myrecipe]
    A[app] --> E[feature:detail]
    A --> F[core:designsystem]
    B --> G[core:data]
    C --> G[core:data]
    D --> G[core:data]
    E --> G[core:data]
    C --> H[core:ui]
    D --> H[core:ui]
```
