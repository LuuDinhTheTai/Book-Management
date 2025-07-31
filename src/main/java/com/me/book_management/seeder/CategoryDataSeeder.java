package com.me.book_management.seeder;

import com.me.book_management.entity.book.Category;
import com.me.book_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        System.out.println("CategoryDataSeeder: Starting category seeding...");
        seedCategories();
        System.out.println("CategoryDataSeeder: Completed category seeding.");
    }

    private void seedCategories() {
        List<CategoryData> categoriesToSeed = Arrays.asList(
            new CategoryData("Programming", "Books about programming languages, frameworks, and software development"),
            new CategoryData("Computer Science", "Books covering algorithms, data structures, and computer science fundamentals"),
            new CategoryData("Software Engineering", "Books about software design, architecture, and development methodologies"),
            new CategoryData("DevOps", "Books about continuous integration, deployment, and operations"),
            new CategoryData("Database", "Books about database design, management, and optimization"),
            new CategoryData("Web Development", "Books about web technologies, frameworks, and frontend/backend development"),
            new CategoryData("Mobile Development", "Books about mobile app development for iOS and Android"),
            new CategoryData("Cloud Computing", "Books about cloud platforms, services, and architecture"),
            new CategoryData("Cybersecurity", "Books about security, cryptography, and ethical hacking"),
            new CategoryData("Artificial Intelligence", "Books about AI, machine learning, and data science"),
            new CategoryData("Networking", "Books about computer networks, protocols, and infrastructure"),
            new CategoryData("Operating Systems", "Books about OS design, implementation, and administration"),
            new CategoryData("Business & Management", "Books about project management, leadership, and business processes"),
            new CategoryData("System Administration", "Books about server management, monitoring, and maintenance"),
            new CategoryData("Game Development", "Books about game design, graphics, and interactive media")
        );
        
        for (CategoryData categoryData : categoriesToSeed) {
            if (!categoryRepository.existsByName(categoryData.name)) {
                Category category = createCategory(categoryData);
                categoryRepository.save(category);
            }
        }
    }
    
    private Category createCategory(CategoryData categoryData) {
        Category category = new Category();
        category.setName(categoryData.name);
        category.setDescription(categoryData.description);
        return category;
    }
    
    private static class CategoryData {
        private final String name;
        private final String description;
        
        public CategoryData(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
} 