package com.directa24.main.challenge.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractSearchResults implements Serializable {
    @JsonProperty("page")
    private int currentPage;

    @JsonProperty("per_page")
    private int recordsPerPage;

    @JsonProperty("total")
    private int totalRecords;

    @JsonProperty("total_pages")
    private int totalPages;
}
