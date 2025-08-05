package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import com.me.book_management.entity.book.Detail;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.book.DetailRepository;
import com.me.book_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookDataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final DetailRepository detailRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Starting BookDataSeeder...");
        seedBookDetails();
        seedBooks();
        linkBooksWithCategories();
        log.info("BookDataSeeder completed successfully!");
    }

    private void seedBookDetails() {
        log.info("Seeding book details...");
        if (detailRepository.count() == 0) {
            List<Detail> details = List.of(
                    createDetail("978-0132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship",
                            "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2008, 8, 11, 0, 0),
                            "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees.",
                            "464", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.EBOOK),

                    createDetail("978-0201633610", "Design Patterns", "Elements of Reusable Object-Oriented Software",
                            "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Addison-Wesley",
                            LocalDateTime.of(1994, 10, 31, 0, 0),
                            "Capturing a wealth of experience about the design of object-oriented software.",
                            "416", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.EBOOK),

                    createDetail("978-0134685991", "Effective Java", "Programming Language Guide",
                            "Joshua Bloch", "Addison-Wesley", LocalDateTime.of(2017, 12, 27, 0, 0),
                            "Best practices for the Java platform.",
                            "416", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.EBOOK),

                    createDetail("978-0262033848", "Introduction to Algorithms", "Third Edition",
                            "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", "MIT Press",
                            LocalDateTime.of(2009, 7, 31, 0, 0),
                            "A comprehensive guide to algorithms and data structures.",
                            "1312", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.HARDCOVER),

                    createDetail("978-0321125217", "The Pragmatic Programmer", "Your Journey to Mastery",
                            "Andrew Hunt, David Thomas", "Addison-Wesley", LocalDateTime.of(1999, 10, 20, 0, 0),
                            "From Journeyman to Master.",
                            "352", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.PAPERBACK),

                    createDetail("978-0131103627", "The C Programming Language", "Second Edition",
                            "Brian W. Kernighan, Dennis M. Ritchie", "Prentice Hall", LocalDateTime.of(1988, 3, 22, 0, 0),
                            "The definitive guide to C programming.",
                            "272", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.PAPERBACK),

                    createDetail("978-0596007126", "Head First Design Patterns", "A Brain-Friendly Guide",
                            "Eric Freeman, Elisabeth Robson, Bert Bates, Kathy Sierra", "O'Reilly Media",
                            LocalDateTime.of(2004, 10, 25, 0, 0),
                            "A brain-friendly guide to design patterns.",
                            "694", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.EBOOK),

                    createDetail("978-0201485677", "Refactoring", "Improving the Design of Existing Code",
                            "Martin Fowler", "Addison-Wesley", LocalDateTime.of(1999, 7, 8, 0, 0),
                            "Improving the design of existing code.",
                            "448", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.PAPERBACK),

                    createDetail("978-0137081073", "The Clean Coder", "A Code of Conduct for Professional Programmers",
                            "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2011, 5, 13, 0, 0),
                            "A code of conduct for professional programmers.",
                            "256", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.EBOOK),

                    createDetail("978-0135974445", "Agile Software Development", "Principles, Patterns, and Practices",
                            "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2002, 10, 25, 0, 0),
                            "Principles, patterns, and practices of agile software development.",
                            "552", Constants.BOOK_LANGUAGE.EN, Constants.BOOK_FORMAT.PAPERBACK)
            );
            detailRepository.saveAll(details);
            log.info("Created {} book details", details.size());
        } else {
            log.info("Book details already exist, skipping...");
        }
    }

    private Detail createDetail(String isbn, String title, String subtitle, String author,
                               String publisher, LocalDateTime publishedDate, String description,
                               String pageCount, String language, String format) {
        Detail detail = new Detail();
        detail.setIsbn(isbn);
        detail.setTitle(title);
        detail.setSubtitle(subtitle);
        detail.setAuthor(author);
        detail.setPublisher(publisher);
        detail.setPublishedDate(publishedDate);
        detail.setDescription(description);
        detail.setPageCount(pageCount);
        detail.setLanguage(language);
        detail.setFormat(format);
        return detail;
    }

    private void seedBooks() {
        log.info("Seeding books...");
        if (bookRepository.count() == 0) {
            List<Detail> details = detailRepository.findAll();
            Optional<Account> adminOpt = accountRepository.findByUsername("admin");
            
            if (adminOpt.isPresent()) {
                Account admin = adminOpt.get();
                for (int i = 0; i < details.size(); i++) {
                    Detail detail = details.get(i);
                    Book book = createBook(detail.getTitle(), 29.99f + i * 5, 50 + i * 5, 
                                         Constants.BOOK_STATUS.ACTIVE, detail, admin);
                    bookRepository.save(book);
                    log.info("Created book: {}", detail.getTitle());
                }
            } else {
                log.warn("Admin account not found, skipping book creation");
            }
        } else {
            log.info("Books already exist, skipping...");
        }
    }

    private Book createBook(String name, float price, int qty, String status, Detail detail, Account account) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setQty(qty);
        book.setStatus(status);
        book.setDetail(detail);
        book.setAccount(account);
        return book;
    }

    private void linkBooksWithCategories() {
        log.info("Linking books with categories...");
        List<Book> books = bookRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        
        if (!books.isEmpty() && !categories.isEmpty()) {
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                Category category = getCategoryForBook(i, categories);
                book.getCategories().add(category);
                bookRepository.save(book);
                log.info("Linked book '{}' with category '{}'", book.getName(), category.getName());
            }
        } else {
            log.warn("No books or categories found for linking");
        }
    }

    private Category getCategoryForBook(int index, List<Category> categories) {
        return categories.get(index % categories.size());
    }
} 