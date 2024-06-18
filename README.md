# CocktailRecipe

칵테일 레시피 api (www.thecocktaildb.com) 를 사용한 스터디용 앱

<img src="/assets/home_1.png" alt="drawing" width="200" height="420" />

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

### Project Structure

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

### Build Logic

```mermaid
flowchart TD
    A(cocktailApplication) --> B(:app)
    C(cocktailLibrary) --> D(cocktailFeature)
    E(cocktailCompose) --> D
    D --> F1(:feature:home)
    D --> F2(:feature:favorite)
    D --> F3(:feature:detail)
    D --> F4(:feature:myrecipe)
    G1(cocktailRoom) --> H1(:core:data)
    C --> H1
    C --> H2
    E --> H2(:core:designsystem)
    
```
