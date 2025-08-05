package com.me.book_management.seeder;

import com.me.book_management.entity.book.Category;
import com.me.book_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryDataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        log.info("Starting CategoryDataSeeder...");
        seedCategories();
        log.info("CategoryDataSeeder completed successfully!");
    }

    private void seedCategories() {
        log.info("Seeding categories...");
        List<CategoryData> categoriesToSeed = List.of(
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
                new CategoryData("Game Development", "Books about game design, graphics, and interactive media"),
                new CategoryData("Data Science", "Books about data analysis, statistics, and visualization"),
                new CategoryData("Machine Learning", "Books about ML algorithms, neural networks, and deep learning"),
                new CategoryData("Blockchain", "Books about cryptocurrency, smart contracts, and distributed systems"),
                new CategoryData("IoT", "Books about Internet of Things, embedded systems, and sensors"),
                new CategoryData("UI/UX Design", "Books about user interface design, user experience, and design principles")
        );
        
        for (CategoryData categoryData : categoriesToSeed) {
            if (!categoryRepository.existsByName(categoryData.name)) {
                Category category = createCategory(categoryData);
                categoryRepository.save(category);
                log.info("Created category: {}", categoryData.name);
            } else {
                log.info("Category already exists: {}", categoryData.name);
            }
        }
    }

    private Category createCategory(CategoryData data) {
        Category category = new Category();
        category.setName(data.name);
        category.setDescription(data.description);
        return category;
    }

    private static class CategoryData {
        private final String name;
        private final String description;
        
        CategoryData(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
} 