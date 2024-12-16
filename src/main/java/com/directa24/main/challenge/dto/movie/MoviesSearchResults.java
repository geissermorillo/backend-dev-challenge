package com.directa24.main.challenge.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MoviesSearchResults implements Serializable {
    private static final long serialVersionUID = 5699164016726939895L;

    @JsonProperty("page")
    private int currentPage;

    @JsonProperty("per_page")
    private int recordsPerPage;

    @JsonProperty("total")
    private int totalRecords;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("data")
    private List<MovieRecord> movies;
}
