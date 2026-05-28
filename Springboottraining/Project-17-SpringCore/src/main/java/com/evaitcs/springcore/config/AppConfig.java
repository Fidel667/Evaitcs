package com.evaitcs.springcore.config;

import com.evaitcs.springcore.repository.InMemoryRecipeRepository;
import com.evaitcs.springcore.repository.RecipeRepository;
import com.evaitcs.springcore.service.RecipeFormatter;
import com.evaitcs.springcore.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ============================================================================
 * CLASS: AppConfig
 * TOPIC: Java-Based Configuration — The modern way to configure Spring
 * ============================================================================
 *
 * MIGRATION PATH (what you'll do in this project):
 *   Step 1: Start with XML config (applicationContext.xml) — old-school
 *   Step 2: Migrate to THIS Java-based config — modern, type-safe
 *   Step 3: Use @ComponentScan to let Spring auto-detect beans — least code
 *
 * @Configuration tells Spring: "This class contains bean definitions."
 * @Bean methods replace <bean> elements in XML.
 * @ComponentScan tells Spring: "Scan these packages for @Component/@Service/@Repository."
 *
 * INTERVIEW TIP:
 * "I prefer Java-based configuration because it's type-safe, refactorable,
 *  and keeps configuration close to the code. XML config is legacy but
 *  I understand it for maintaining older applications."
 * ============================================================================
 */

// =========================================================================
// OPTION A: Explicit @Bean definitions (uncomment this, comment out Option B)
// =========================================================================
// @Configuration
// public class AppConfig {
//
//     @Bean
//     public RecipeRepository recipeRepository() {
//         return new InMemoryRecipeRepository();
//     }
//
//     @Bean
//     public RecipeService recipeService(RecipeRepository recipeRepository) {
//         return new RecipeService(recipeRepository);
//     }
//
//     @Bean
//     public RecipeFormatter recipeFormatter() {
//         return new RecipeFormatter();
//     }
// }

// =========================================================================
// OPTION B: Component scanning (preferred — Spring auto-detects beans)
// =========================================================================
@Configuration
@ComponentScan(basePackages = "com.evaitcs.springcore")
public class AppConfig {

    // TODO 1: Understand why this class is nearly empty when using @ComponentScan!
    //
    // With @ComponentScan, Spring automatically finds classes annotated with:
    //   @Component, @Service, @Repository, @Controller
    // in the specified packages and registers them as beans.
    //
    // The @Service on RecipeService and @Repository on InMemoryRecipeRepository
    // are enough — Spring creates the beans and wires them together automatically!

    // TODO 2 (BONUS): Add a @Bean method for a custom configuration value
    // Example: a default category filter
    //
    // @Bean
    // public String defaultCategory() {
    //     return "All";
    // }
}

