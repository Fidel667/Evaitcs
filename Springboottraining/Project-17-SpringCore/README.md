# Project 17: Spring Core - "BuildMyRecipeBook"

## 🎯 Objective
Build a **Recipe Book** console application using raw Spring Framework (no Spring Boot).
Learn IoC, Dependency Injection, Bean Lifecycle, and Configuration from the ground up.
Understanding these fundamentals makes Spring Boot "magic" transparent in interviews!

## 📚 Topics Covered (from Study Material)
- Inversion of Control (IoC) — the framework manages object creation
- Dependency Injection (Constructor, Setter, Field)
- Spring IoC Container (ApplicationContext, BeanFactory)
- XML Configuration → Java-Based Configuration migration
- Bean Lifecycle and Scopes (singleton, prototype, @PostConstruct, @PreDestroy)

## 📁 Project Structure
```
Project-17-SpringCore/
├── pom.xml
└── src/main/java/com/evaitcs/springcore/
    ├── model/
    │   ├── Recipe.java
    │   └── Ingredient.java
    ├── repository/
    │   ├── RecipeRepository.java          ← Interface
    │   └── InMemoryRecipeRepository.java  ← Implementation
    ├── service/
    │   ├── RecipeService.java             ← Constructor injection
    │   └── RecipeFormatter.java           ← Bean with lifecycle hooks
    ├── config/
    │   └── AppConfig.java                 ← Java-based @Configuration
    └── RecipeBookApp.java                 ← Main entry point
src/main/resources/
    └── applicationContext.xml             ← XML config (then migrate to Java)
```

## 🚀 How to Run
```bash
mvn compile exec:java -Dexec.mainClass="com.evaitcs.springcore.RecipeBookApp"
```

