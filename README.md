# Fixed Solutions Task
This repo for Fixed Solutions Android Task using www.themoviedb.org

## <b>Task Description: </b> <br />
:point_right: Need to show list of movies returned from api. <br />
:point_right: Sort this list of movies from newest to oldest movies. <br />
:point_right: Cache the movies list and every movie details to show it from Room DB in case no internet connection. <br />


| Home Screen                    | Movie Details Screen              | 
|:------------------------------:|:---------------------------------:|
|![](https://github.com/Mina-Mikhail/Fixed-Solutions-Task/blob/master/Shots/movies.jpg) | ![](https://github.com/Mina-Mikhail/Fixed-Solutions-Task/blob/master/Shots/movie_details.jpg) |

## <b>Project Architecture: </b> <br />
I have used MVVM to build my project.<br />


## <b>Built With: </b> <br />
:point_right: [Glide](https://github.com/bumptech/glide) - For image loading. <br />
:point_right: [Retrofit2](https://github.com/square/retrofit) - For network calls. <br />
:point_right: [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation. <br />
:point_right: [RXJava](https://github.com/ReactiveX/RxJava) - To handle network calls alongside with  Retrofit2. <br />
:point_right: [Rounded ImageView](https://github.com/vinc3m1/RoundedImageView) - To display images. <br />
:point_right: [Uber autoDispose](https://github.com/uber/AutoDispose) - Automatic binding+disposal of RxJava 2 streams. <br />
:point_right: [Room](https://developer.android.com/jetpack/androidx/releases/room) - Persistence library provides an abstraction layer over SQLite. <br />
:point_right: [Dagger](https://github.com/google/dagger) - A fast dependency injector for Java and Android. <br />
:point_right: [Android SpinKit](https://github.com/ybq/Android-SpinKit) - Android loading animations. <br />
:point_right: [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Support library that allows you to bind UI components in your layouts to data sources in your app. <br />
:point_right: [PhotoView](https://github.com/chrisbanes/PhotoView) - For zooming image views. <br />
:point_right: [Butterknife](https://github.com/JakeWharton/butterknife) - For bind views. <br />
:point_right: [SimpleRatingBar](https://github.com/ome450901/SimpleRatingBar) - For Rating Bar. <br />
:point_right: [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - For navigating between fragments. <br />
:point_right: [Safe Args](https://developer.android.com/jetpack/androidx/releases/navigation#safe_args) - For sending arguments safely between fragments. <br />


## <b>To Do in Future: </b> <br />
- [x]  Use Android Navigation Component Library.<br />
- [ ]  Use AndroidX.<br />
- [ ]  Infinite Scrolling.<br />
- [ ]  Apply some Unit Tests.<br />
- [ ]  Kotlin Version (will be in a separate branch).<br />

## License

    Copyright (C) 2020 Mina Mikhail

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
