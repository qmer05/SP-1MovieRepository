package app.services;

import app.dtos.DirectorDTO;
import app.dtos.MovieDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MovieService {

    private final ObjectMapper objectMapper;

    public MovieService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MovieDTO> getDanishMoviesFromLastFiveYears(String jsonFieldName, String uri) {
        List<MovieDTO> movies = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        try {
            do {
                HttpClient client = HttpClient.newHttpClient();

                StringBuilder builder = new StringBuilder()
                        .append(uri)
                        .append("&api_key=")
                        .append(System.getenv("api_key"))
                        .append("&page=")
                        .append(currentPage);

                // Create a request
                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(new URI(builder.toString()))
                        .GET()
                        .build();

                // Send the request and get the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode jsonNode = rootNode.get(jsonFieldName);

                totalPages = rootNode.get("total_pages").asInt();
                currentPage++;

                // System.out.println((int) ((currentPage / (double) totalPages) * 100) + "%");

                if (response.statusCode() == 200) {
                    for (JsonNode node : jsonNode) {
                        MovieDTO movieDTO = objectMapper.treeToValue(node, MovieDTO.class);
                        if (movieDTO.getReleaseDate() != null && movieDTO.getReleaseDate().isAfter(LocalDate.now().minusYears(5))) {
                            movies.add(movieDTO);
                        }
                    }
                } else {
                    System.out.println("GET request failed. Status code: " + response.statusCode());
                }
            } while (currentPage <= totalPages);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getDTOFromURI(Class<T> dtoClass, String uri) {
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            StringBuilder builder = new StringBuilder()
                    .append(uri)
                    .append("?api_key=")
                    .append(System.getenv("api_key"));

            // Create a request
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(builder.toString()))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and return the DTO if successful
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), dtoClass);
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DirectorDTO getDirectorDTOFromURI(Class<DirectorDTO> dtoClass, String jsonFieldName, String uri) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            StringBuilder builder = new StringBuilder()
                    .append(uri)
                    .append("?api_key=")
                    .append(System.getenv("api_key"));

            // Create a request
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(builder.toString()))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode jsonNode = rootNode.get(jsonFieldName);

            if (response.statusCode() == 200) {
                List<DirectorDTO> directorDTOS = objectMapper.readValue(jsonNode.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, dtoClass));
                List<DirectorDTO> directorDTOs = directorDTOS.stream()
                        .filter(director -> "Director".equals(director.getJob()))
                        .toList();

                if (!directorDTOs.isEmpty()) {
                    return directorDTOs.get(0);
                }

            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> getDTOsFromURI(Class<T> dtoClass, String jsonFieldName, String uri) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            StringBuilder builder = new StringBuilder()
                    .append(uri)
                    .append("?api_key=")
                    .append(System.getenv("api_key"));

            // Create a request
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(builder.toString()))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode jsonNode = rootNode.get(jsonFieldName);

            if (response.statusCode() == 200) {
                return objectMapper.readValue(jsonNode.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, dtoClass));
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
