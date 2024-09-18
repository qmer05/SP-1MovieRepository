package app;

import app.config.HibernateConfig;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import app.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // setup
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("sp-1movierepository");
        MovieDAO movieDAO = new MovieDAO(emf);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MovieService movieService = new MovieService(objectMapper);


//        movieDAO.getMoviesByDirectorName("Kasper Juhl").forEach(System.out::println);
//
//        movieDAO.getMoviesByActorName("Nikolaj Lie Kaas").forEach(System.out::println);
//
//        movieDAO.getTop10MostPopularMovies().forEach(System.out::println);
//
//        movieDAO.getTop10LowestRatedMovies().forEach(System.out::println);
//
//        System.out.println(movieDAO.getTotalAverageOfAllMovies());
//
//        movieDAO.searchMovieByName("jagt").forEach(System.out::println);
//
//
//        Get Json from API 's and convert to DTOs
//        String movieId = "12093";
//        List<ActorDTO> actorDTOs = movieService.getDTOsFromURI(ActorDTO.class, "cast", "https://api.themoviedb.org/3/movie/" + movieId + "/credits");
//        DirectorDTO directorDTO = movieService.getDirectorDTOFromURI(DirectorDTO.class, "crew", "https://api.themoviedb.org/3/movie/" + movieId + "/credits");
//        MovieDTO movieDTO = movieService.getDTOFromURI(MovieDTO.class, "https://api.themoviedb.org/3/movie/" + movieId);
//        movieDTO.setDirectorDTO(directorDTO);
//        movieDTO.setActorDTOs(actorDTOs);
//
//        Get Json from API 's and convert to DTOs (all danish movies within 5 years)
//        List<MovieDTO> movieDTOs = movieService.getDanishMoviesFromLastFiveYears("results", "https://api.themoviedb.org/3/discover/movie?with_origin_country=DK");
//        for (MovieDTO m : movieDTOs) {
//            DirectorDTO directorDTO = movieService.getDirectorDTOFromURI(DirectorDTO.class, "crew", "https://api.themoviedb.org/3/movie/" + m.getId() + "/credits");
//            if (directorDTO != null) {
//                m.setDirectorDTO(directorDTO);
//            }
//            List<ActorDTO> actorDTOs = movieService.getDTOsFromURI(ActorDTO.class, "cast", "https://api.themoviedb.org/3/movie/" + m.getId() + "/credits");
//            if (!actorDTOs.isEmpty()) {
//                m.setActorDTOs(actorDTOs);
//            }
//            MovieDTO movieDTO = movieService.getDTOFromURI(MovieDTO.class, "https://api.themoviedb.org/3/movie/" + m.getId());
//            if (!movieDTO.getGenresDTOs().isEmpty()) {
//                m.setGenresDTOs(movieDTO.getGenresDTOs());
//            }
//            movieDAO.createMovie(m);
//        }
//
//        movieDAO.createMovie(movieDTO);
//
//        movieDAO.deleteMovieById(365177);
//
//        List<MovieDTO> movies = movieDAO.getAllMovies();
//        for (MovieDTO movie : movies) {
//            System.out.println("Movie ID: " + movie.getId());
//            System.out.println("Title: " + movie.getTitle());
//            System.out.println("Language: " + movie.getLanguage());
//            System.out.println("Release Date: " + movie.getReleaseDate());
//            System.out.println("Rating: " + movie.getRating());
//            System.out.println("Genres: " + movie.getGenresDTOs());
//            System.out.println("Actors: " + movie.getActorDTOs());
//            System.out.println("Director: " + movie.getDirectorDTO());
//            System.out.println("--------------------------------------------------");
//        }
//
//        System.out.println(movieDAO.getMovieById(1174003));
//
//        movieDAO.updateMovie(new MovieDTO(
//                646097,
//                "Rebel Fridge",
//                "da",
//                "A former Marine confronts corruption in a small town when local law enforcement unjustly seizes the bag of cash he needs to post his cousin's bail.",
//                "2022-08-27",
//                5.337,
//                movieDTO.getGenresDTOs(),
//                movieDTO.getActorDTOs(),
//                movieDTO.getDirectorDTO()
//        ));

    }
}



