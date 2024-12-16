package com.directa24.main.challenge;

import com.directa24.main.challenge.dto.movie.MovieRecord;
import com.directa24.main.challenge.dto.movie.MoviesSearchResults;
import com.directa24.main.challenge.service.httpClient.HttpClientServiceImpl;
import com.directa24.main.challenge.service.httpClient.IHttpClientService;
import com.directa24.main.challenge.service.searchClient.MoviesSearchServiceImpl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

   private static final IHttpClientService<MoviesSearchResults> moviesHttpClient = new HttpClientServiceImpl<>();

   public static void main(String[] args) {
      IntStream.range(0, 8)
              .forEach(threshold -> {
                  Set<String> directors = getDirectors(threshold);
                  if (directors.isEmpty()) {
                     System.out.println(String.format("\nNo directors more movies than: %s\n\t", threshold));
                  } else {
                     System.out.println(
                                 String.format("\nDirectors with more movies than: %s\n\t", threshold) +
                                         String.join("\n\t", directors));
                     }
                  }
              );
   }

   /*
    * Complete the 'getDirectors' function below.
    *
    * The function is expected to return a List<String>.
    * The function accepts int threshold as parameter.
    *
    * URL
    * https://directa24-movies.mocklab.io/api/movies/search?page=<pageNumber>
    */
   public static Set<String> getDirectors(int threshold) {
       MoviesSearchServiceImpl moviesSearchService = new MoviesSearchServiceImpl(moviesHttpClient);
       Set<MovieRecord> moviesSearchResults = moviesSearchService.searchAllMovies();

       if (moviesSearchResults.isEmpty()) {
           return new TreeSet<>();
       }

       Map<String, Integer> amountOfMoviesByDirector = new HashMap<>();

       moviesSearchResults.stream()
               .map(MovieRecord::getDirector)
               .forEach(director ->
                       amountOfMoviesByDirector.put(director,
                               amountOfMoviesByDirector.getOrDefault(director, 0) + 1)
               );

       return amountOfMoviesByDirector.entrySet().stream()
               .filter(entry -> entry.getValue() > threshold)
               .map(Map.Entry::getKey)
               .collect(Collectors.toCollection(TreeSet::new));
   }
}
