package com.evaitcs.springcore.repository;

import com.evaitcs.springcore.model.Recipe;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * ============================================================================
 * CLASS: InMemoryRecipeRepository
 * TOPIC: Spring Bean — This class is managed by the Spring IoC Container
 * ============================================================================
 *
 * @Repository tells Spring: "This is a data-access bean. Create an instance
 * and manage its lifecycle for me."
 *
 * Spring will:
 * 1. Detect this class during component scanning
 * 2. Create ONE instance (singleton scope by default)
 * 3. Inject it wherever RecipeRepository is needed
 *
 * INTERVIEW TIP:
 * "@Repository is a specialization of @Component. It marks the class as a
 *  DAO and enables Spring's exception translation for database errors."
 * ============================================================================
 */
@Repository
public class InMemoryRecipeRepository implements RecipeRepository {

    // TODO 1: Declare a private Map<String, Recipe> field for storage
    //   Initialize in constructor: new HashMap<>()

    // TODO 2: Implement all RecipeRepository methods
    //   save()        → recipes.put(recipe.getName(), recipe)
    //   findByName()  → Optional.ofNullable(recipes.get(name))
    //   findAll()     → new ArrayList<>(recipes.values())
    //   findByCategory() → filter recipes by category
    //   deleteByName() → recipes.remove(name) != null
    //   count()       → recipes.size()
}

