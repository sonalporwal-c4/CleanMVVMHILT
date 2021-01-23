
# Star war character finder application

I have used clean architecture for this project which might look complicated but this allows us to keep the amount of boilerplate code to a minimum and also demonstrate the approach in a simpler form. The codebase is easy to maintain, extend and testable using the clean architecture. I have followed the SOLID principles of code style as well.

## Languages and libraries used

* Kotlin
* Android Support Libraries
* Design pattern - MVVM
* View Binding
* Dagger hilt - Dependency Injection
* Navigation, Navigation safe arguments - Jetpack
* Coroutines
* Retrofit
* OkHttp
* Gson
* Junit
* Mockito
* Espresso

## Requirements

On app start, the user lands on the character search screen.
The user can search for characters from the Star Wars universe. The result of the search should display a character list.
When tapping on a character, details are displayed as given in the specification.

## Clean Architecture + MVVM + Hilt

The architecture of the project follows the principles of Clean Architecture. The focus has been to write easy-to-read Kotlin code.

![Image](clean_architecture_hilt.png?raw=true "Architecture diagram")

The architecture layers are as follows:

### Presentation

Presentation layer's responsibility is to handle the presentation of the User Interface. This layer has no dependency on the Android Framework, it is a pure Kotlin module.
It has been implemented with MVVM design pattern. View Model is used for fetching the data from source in the form of live data which is observed by the view.

In MVVM architecture, the [ViewModel] acts as the point at which the view and the data layer interacts with the interface in order to implements the business logic. 

### Domain

Domain layer consists of the business logic of the application, the useCase and model classes. Domain module is completely independent entity, it is not depend on either of the data or presentation layer.
UseCase uses coroutines to fetch the data from the repository.

### Data

Data layer is dependent on Domain and consists of repository implementation, cache to store data, NetworkApi and DTO. I have implemented dagger hilt to provide the dependency injection.

![Image](home_screen.png?raw=true "Home Screen")
![Image](search_character.png?raw=true "Search Screen")
![Image](character_details_screen.png?raw=true "Character Details Screen")