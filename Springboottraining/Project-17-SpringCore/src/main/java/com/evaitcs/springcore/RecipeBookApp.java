package com.evaitcs.springcore;

import com.evaitcs.springcore.config.AppConfig;
import com.evaitcs.springcore.model.Recipe;
import com.evaitcs.springcore.service.RecipeFormatter;
import com.evaitcs.springcore.service.RecipeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ============================================================================
 * MAIN APPLICATION: RecipeBookApp
 * TOPIC: Spring IoC Container — ApplicationContext creates and manages beans
 * ============================================================================
 *
 * TWO WAYS TO CREATE THE SPRING CONTAINER:
 *
 * 1. XML-based:
 *    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
 *
 * 2. Java-based (preferred):
 *    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
 *
 * The ApplicationContext:
 * - Creates all beans defined in config
 * - Injects dependencies
 * - Manages bean lifecycle (init → use → destroy)
 * ============================================================================
 */
public class RecipeBookApp {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       SPRING RECIPE BOOK                ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        // =====================================================================
        // STEP 1: Create the Spring IoC Container
        // =====================================================================

        // TODO 1: Create ApplicationContext using Java config
        // AnnotationConfigApplicationContext context =
        //     new AnnotationConfigApplicationContext(AppConfig.class);

        // ALTERNATIVE: XML config (try this first, then switch to Java config)
        // ClassPathXmlApplicationContext context =
        //     new ClassPathXmlApplicationContext("applicationContext.xml");

        // =====================================================================
        // STEP 2: Get beans FROM the container (not with 'new'!)
        // =====================================================================

        // TODO 2: Get the RecipeService bean from the container
        // RecipeService recipeService = context.getBean(RecipeService.class);
        // RecipeFormatter formatter = context.getBean(RecipeFormatter.class);

        // =====================================================================
        // STEP 3: Use the beans
        // =====================================================================

        // TODO 3: Add some recipes
        // recipeService.addRecipe(new Recipe("Chocolate Cake", "Dessert", 8, "Mix flour, cocoa, sugar..."));
        // recipeService.addRecipe(new Recipe("Caesar Salad", "Salad", 4, "Toss romaine, croutons..."));
        // recipeService.addRecipe(new Recipe("Pasta Carbonara", "Main Course", 2, "Cook pasta, mix eggs..."));
        // recipeService.addRecipe(new Recipe("Tiramisu", "Dessert", 6, "Layer mascarpone, espresso..."));

        // TODO 4: List all recipes
        // System.out.println("All Recipes (" + recipeService.getRecipeCount() + "):");
        // recipeService.getAllRecipes().forEach(r -> System.out.println("  " + r));

        // TODO 5: Filter by category
        // System.out.println("\nDesserts:");
        // recipeService.getRecipesByCategory("Dessert").forEach(r -> System.out.println("  " + r));

        // TODO 6: Use the formatter
        // Recipe cake = recipeService.findRecipe("Chocolate Cake").orElse(null);
        // if (cake != null) {
        //     System.out.println("\n" + formatter.format(cake.getName(), cake.getCategory(),
        //         cake.getServings(), cake.getInstructions()));
        // }

        // =====================================================================
        // STEP 4: Demonstrate Singleton scope
        // =====================================================================

        // TODO 7: Prove beans are singletons
        // RecipeService service1 = context.getBean(RecipeService.class);
        // RecipeService service2 = context.getBean(RecipeService.class);
        // System.out.println("\nSame instance? " + (service1 == service2)); // true!

        // =====================================================================
        // STEP 5: Close the container (triggers @PreDestroy)
        // =====================================================================

        // TODO 8: Close the context — watch for the @PreDestroy message!
        // context.close();

        System.out.println("\nRecipe Book App finished.");
    }
}

