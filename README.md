# My Movies

An simple Android application that displays the list of trending movies, when we select one movie it shows more details
about it.

## Technologies

- <a href="https://developer.android.com/guide/navigation?hl=en" target="Navigation">
  Navigation</a> : a library from Google to help us navigation and share data between fragments
- <a href="https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=en" target="Pagination">
  Pagination</a> : a library from Google to help us pagination from backend
- <a href="https://developer.android.com/studio/test/test-in-android-studio?hl=en" target="Unity Test">
  Unity Test</a> : for testing getting trending movies
- <a href="https://developer.android.com/kotlin/coroutines?hl=en" target="Coroutines">
  Coroutines</a> : for background tasks (Api calls)
- <a href="https://developer.android.com/topic/libraries/architecture/livedata?hl=en" target="ViewModel and live data">
  ViewModel and live data</a> : for saving and updating state of ui
- <a href="https://github.com/kosi-libs/Kodein" target="Kodein">Kodein</a> : dependency injection
  framework
- <a href="https://square.github.io/retrofit/" target="Retrofit">Retrofit</a> : for connect to
  backend
- <a href="https://github.com/google/gson" target="Gson">Gson</a> : for serialization json to models
- <a href="https://square.github.io/picasso/" target="Picasso">Picasso</a> : for loading images

## Entities

- Movie

## Architecture : MVVM

- I use **MVVM** to separate program logic and user interface controls.
- The project based in 3 axis repository, viewModel and UI(activity and fragment).
- I use **Flow** functions To fetch data from backend using retrofit and emit data to LiveData for
  update state of fragment.
- The app consists of a **MainActivity** that has a navigation controller with the following fragments:
  **MainMoviesFragment**, **AllMoviesFragment** and **MovieDetailFragment** :
  - **MainMoviesFragment** has 3 sections of movies : Top, Trending and upcoming movies.
  - **AllMoviesFragment** displays all the movies from one of the previous sections with pagination of 20 items.
  - **MovieDetailFragment** to show movie details.
- I created a Unity test to retrieve trending movies.