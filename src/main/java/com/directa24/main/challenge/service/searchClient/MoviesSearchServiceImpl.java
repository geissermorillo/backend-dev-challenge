package com.directa24.main.challenge.service.searchClient;

import com.directa24.main.challenge.dto.movie.MovieRecord;
import com.directa24.main.challenge.dto.movie.MoviesSearchResults;
import com.directa24.main.challenge.service.httpClient.IHttpClientService;

import java.net.http.HttpResponse;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class MoviesSearchServiceImpl implements ISearchService<MovieRecord> {

    private static final String MOVIES_API_BASE_SEARCH_URL = "https://eron-movies.wiremockapi.cloud/api/movies/search";
    private static final String PAGE_PARAM_NAME = "page";
    public static final int PAGE_NUMBER_FOR_LIGHT_REQUEST = 0;

    private final IHttpClientService<MoviesSearchResults> httpClientService;

    public MoviesSearchServiceImpl(IHttpClientService<MoviesSearchResults> httpClientService) {
        this.httpClientService = httpClientService;
    }

    @Override
    public Set<MovieRecord> searchAllMovies() {
        LinkedHashSet<MovieRecord> movies = new LinkedHashSet<>();
        // This is a light request to determine the total amount of pages for querying then all in order to get all
        // the records, we pass 0 because with this page we get pagination information without bringing data, which
        // improves the performance.
        HttpResponse<MoviesSearchResults> resultsHttpResponse = this.httpClientService
                .executeGet(this.buildSearchURLFromPageNumber(PAGE_NUMBER_FOR_LIGHT_REQUEST), MoviesSearchResults.class);

        IntStream.range(1, resultsHttpResponse.body().getTotalPages() + 1)
                .forEach(pageNumber -> {
                    HttpResponse<MoviesSearchResults> response = this.httpClientService
                            .executeGet(this.buildSearchURLFromPageNumber(pageNumber), MoviesSearchResults.class);
                    movies.addAll(response.body().getMovies());
                });

        return movies;
    }

    private String buildSearchURLFromPageNumber(int pageNumber) {
        return new StringBuffer(MOVIES_API_BASE_SEARCH_URL)
                .append("?")
                .append(PAGE_PARAM_NAME)
                .append("=")
                .append(pageNumber)
                .toString();
    }

}
