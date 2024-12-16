package com.directa24.main.challenge.service.searchClient;

import java.io.Serializable;
import java.util.Set;

public interface ISearchService<T extends Serializable> {
    Set<T> searchAllMovies();
}
