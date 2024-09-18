# SP-1 Movie Repository

## Overview

The SP-1 Movie Repository is a Java-based application that interacts with The Movie Database (TMDb) API to fetch and manage movie data. The application uses Hibernate for ORM (Object-Relational Mapping) and Jackson for JSON processing.

## Features

- Fetch movie details from TMDb API.
- Convert JSON responses to DTOs (Data Transfer Objects).
- Store and manage movie data using Hibernate.
- Handle various entities such as movies, directors, actors, and genres.
- Retrieve movies by director or actor name.
- Get the top 10 most popular or lowest-rated movies.
- Search for movies by name.
- Create, update, and delete movie records.

## Technologies Used

- **Java**: The primary programming language.
- **Maven**: For project management and dependency management.
- **Hibernate**: For ORM and database interactions.
- **Jackson**: For JSON parsing and processing.
- **TMDb API**: For fetching movie data.

## Setup

### Prerequisites

- Java 11 or higher
- Maven
- An API key from [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api)

### Installation

1. **Clone the repository**:
    ```sh
    git clone <repository-url>
    cd sp-1-movie-repository
    ```

2. **Configure the environment**:
    - Set up the `api_key` environment variable with your TMDb API key.

3. **Build the project**:
    ```sh
    mvn clean install
    ```

4. **Run the application**:
    ```sh
    mvn exec:java -Dexec.mainClass="app.Main"
    ```

## Usage

- **Fetching Danish Movies from the Last Five Years**:
  The application can fetch Danish movies released in the last five years and store them in the database.

- **Handling Null Values**:
  The application includes checks to handle null values for various entities to prevent runtime exceptions.

- **Database Operations**:
  The application supports creating, reading, updating, and deleting movie records in the database.

## Example

Here is an example of how to fetch and store Danish movies from the last five years:

```java
List<MovieDTO> movieDTOs = movieService.getDanishMoviesFromLastFiveYears("results", "https://api.themoviedb.org/3/discover/movie?with_origin_country=DK");
for (MovieDTO m : movieDTOs) {
    DirectorDTO directorDTO = movieService.getDirectorDTOFromURI(DirectorDTO.class, "crew", "https://api.themoviedb.org/3/movie/" + m.getId() + "/credits");
    if (directorDTO != null) {
        m.setDirectorDTO(directorDTO);
    }
    List<ActorDTO> actorDTOs = movieService.getDTOsFromURI(ActorDTO.class, "cast", "https://api.themoviedb.org/3/movie/" + m.getId() + "/credits");
    if (!actorDTOs.isEmpty()) {
        m.setActorDTOs(actorDTOs);
    }
    MovieDTO movieDTO = movieService.getDTOFromURI(MovieDTO.class, "https://api.themoviedb.org/3/movie/" + m.getId());
    if (!movieDTO.getGenresDTOs().isEmpty()) {
        m.setGenresDTOs(movieDTO.getGenresDTOs());
    }
    movieDAO.createMovie(m);
}