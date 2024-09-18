package app.daos;

import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import jakarta.persistence.*;

import java.util.List;

public class MovieDAO {

    private EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void deleteMovieById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie found = em.find(Movie.class, id);
            if (found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            em.remove(found);
            em.getTransaction().commit();
        }
    }

    public MovieDTO updateMovie(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // check if movie exist, if not throw exception
            Movie foundMovie = em.find(Movie.class, movieDTO.getId());
            if (foundMovie == null) {
                throw new EntityNotFoundException();
            }

            // Update movie with new DTO values
            Movie movie = new Movie(movieDTO);
            if (movie.getTitle() != null) {
                movie.setTitle(movieDTO.getTitle());
            }
            movie.setLanguage(movieDTO.getLanguage());
            movie.setRating(movieDTO.getRating());
            movie.setReleaseDate(movieDTO.getReleaseDate());

            // Handle Genre - check if it exists and update it if necessary
            List<GenreDTO> genreDTOs = movieDTO.getGenresDTOs();
            for (GenreDTO g : genreDTOs) {
                Genre foundGenre = em.find(Genre.class, g.getId());
                if (foundGenre != null) {
                    movie.addGenre(foundGenre);
                } else {
                    Genre newGenre = new Genre(g);
                    em.persist(newGenre);
                    movie.addGenre(newGenre);
                }
            }
            // Handle Actors - check if it exists and update it if necessary
            List<ActorDTO> actorDTOs = movieDTO.getActorDTOs();
            for (ActorDTO a : actorDTOs) {
                Actor foundActor = em.find(Actor.class, a.getId());
                if (foundActor != null) {
                    movie.addActor(foundActor);
                } else {
                    Actor newActor = new Actor(a);
                    em.persist(newActor);
                    movie.addActor(newActor);
                }
            }
            // Handle Director - check if it exists and update it if necessary
            DirectorDTO directorDTO = movieDTO.getDirectorDTO();
            Director foundDirector = em.find(Director.class, directorDTO.getId());
            if (foundDirector != null) {
                movie.setDirector(foundDirector);
            } else {
                Director newDirector = new Director(directorDTO);
                em.persist(newDirector);
                movie.setDirector(newDirector);
            }
            em.merge(movie);
            em.getTransaction().commit();
            return new MovieDTO(movie);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update the movie.", e);
        }
    }

    public void createMovie(MovieDTO movieDTO) {

        // create Movie Entity from MovieDTO
        Movie movie = new Movie(movieDTO);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            //Check if movie exists in db
            Movie foundMovie = em.find(Movie.class, movie.getId());
            if (foundMovie != null) {
                throw new EntityExistsException("Movie already exists in database");
            }

            if (movieDTO.getGenresDTOs() != null) {
                // Add genres to movie
                for (GenreDTO g : movieDTO.getGenresDTOs()) {
                    Genre genre = new Genre(g);
                    movie.addGenre(genre);

                    // check if genres exist in db, if not then persist
                    Genre foundGenre = em.find(Genre.class, genre.getId());
                    if (foundGenre == null) {
                        em.persist(genre);
                    }
                }
            }

            if (movieDTO.getDirectorDTO() != null) {
                // Create Director entity from DTO
                Director director = new Director(movieDTO.getDirectorDTO());
                // Add Director to movie
                movie.setDirector(director);
                // Check if Director exist in db, if not then persist
                Director directorFound = em.find(Director.class, director.getId());
                if (directorFound == null) {
                    em.persist(director);
                }
            }

            // persist actors and add actors to movie
            if (movieDTO.getActorDTOs() != null) {
                for (ActorDTO a : movieDTO.getActorDTOs()) {
                    Actor actor = new Actor(a);
                    movie.addActor(actor);
                    // check if actors exists, if not then persist
                    Actor foundActor = em.find(Actor.class, actor.getId());
                    if (foundActor == null) {
                        em.persist(actor);
                    }
                }
            }

            // persist movie
            em.persist(movie);
            em.getTransaction().commit();
        }
    }

    public List<MovieDTO> getAllMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            List<MovieDTO> found = em.createQuery("SELECT new app.dtos.MovieDTO(m) FROM Movie m", MovieDTO.class).getResultList();
            if (found.isEmpty()) {
                throw new NoResultException();
            }
            return found;
        }
    }

    public MovieDTO getMovieById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie found = em.find(Movie.class, id);
            if (found == null) {
                throw new EntityNotFoundException();
            }
            return new MovieDTO(found);
        }
    }

}
