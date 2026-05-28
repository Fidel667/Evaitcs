package com.evaitcs.springcore.repository;

import com.evaitcs.springcore.model.Recipe;
import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * INTERFACE: RecipeRepository
 * TOPIC: Dependency Inversion — depend on abstractions, not implementations
 * ============================================================================
 *
 * The service layer will depend on THIS interface, not on the concrete
 * InMemoryRecipeRepository. This way, Spring can inject ANY implementation.
 *
 * INTERVIEW TIP:
 * "I program to interfaces so I can swap implementations. Today it's
 *  in-memory, tomorrow it could be JPA — zero service-layer changes."
 * ============================================================================
 */
public interface RecipeRepository {

    // TODO 1: Declare CRUD methods:
    //   void save(Recipe recipe);
    //   Optional<Recipe> findByName(String name);
    //   List<Recipe> findAll();
    //   List<Recipe> findByCategory(String category);
    //   boolean deleteByName(String name);
    //   long count();
}

