package com.me.book_management.seeder;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Detail;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.book.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(3)
public class BookDataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final DetailRepository detailRepository;
    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) {
        seedBookDetails();
        seedBooks();
    }

    private void seedBookDetails() {
        if (detailRepository.count() == 0) {
            System.out.println("BookDataSeeder: Creating book details...");
            List<Detail> details = List.of(
                createDetail("978-0132350884", "Clean Code", "A Handbook of Agile Software Craftsmanship", 
                    "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2008, 8, 11, 0, 0), 
                    "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees.", 
                    "464", "English", "Paperback"),
                
                createDetail("978-0201633610", "Design Patterns", "Elements of Reusable Object-Oriented Software", 
                    "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Addison-Wesley", 
                    LocalDateTime.of(1994, 10, 31, 0, 0), 
                    "Capturing a wealth of experience about the design of object-oriented software.", 
                    "416", "English", "Hardcover"),
                
                createDetail("978-0134685991", "Effective Java", "Programming Language Guide", 
                    "Joshua Bloch", "Addison-Wesley", LocalDateTime.of(2017, 12, 27, 0, 0), 
                    "The definitive guide to Java platform best practices.", 
                    "416", "English", "Paperback"),
                
                createDetail("978-0596007126", "Head First Java", "Your Brain on Java", 
                    "Kathy Sierra, Bert Bates", "O'Reilly Media", LocalDateTime.of(2005, 2, 9, 0, 0), 
                    "Learning a complex language like Java is no easy task especially when it's an object-oriented programming language.", 
                    "720", "English", "Paperback"),
                
                createDetail("978-0132778046", "Core Java Volume I", "Fundamentals", 
                    "Cay S. Horstmann", "Prentice Hall", LocalDateTime.of(2012, 3, 24, 0, 0), 
                    "The #1 Guide for Serious Programmers: Fully Updated for Java SE 7.", 
                    "1008", "English", "Paperback"),
                
                createDetail("978-1617292545", "Spring in Action", "Covers Spring 4", 
                    "Craig Walls", "Manning Publications", LocalDateTime.of(2014, 11, 30, 0, 0), 
                    "Spring in Action, Fourth Edition is a hands-on guide to the Spring Framework.", 
                    "624", "English", "Paperback"),
                
                createDetail("978-1491950357", "Programming Hive", "Data Warehouse and Query Language for Hadoop", 
                    "Edward Capriolo, Dean Wampler, Jason Rutherglen", "O'Reilly Media", 
                    LocalDateTime.of(2012, 9, 25, 0, 0), 
                    "Programming Hive introduces Hive, an essential tool in the Hadoop ecosystem.", 
                    "350", "English", "Paperback"),
                
                createDetail("978-1449355739", "Learning Python", "Powerful Object-Oriented Programming", 
                    "Mark Lutz", "O'Reilly Media", LocalDateTime.of(2013, 6, 26, 0, 0), 
                    "Get a comprehensive, in-depth introduction to the core Python language.", 
                    "1600", "English", "Paperback"),
                
                createDetail("978-0596517748", "JavaScript: The Good Parts", "Unearthing the Excellence in JavaScript", 
                    "Douglas Crockford", "O'Reilly Media", LocalDateTime.of(2008, 5, 1, 0, 0), 
                    "Most programming languages contain good and bad parts, but JavaScript has more than its share of the bad.", 
                    "176", "English", "Paperback"),
                
                createDetail("978-0262033848", "Introduction to Algorithms", "A Creative Approach", 
                    "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", 
                    "MIT Press", LocalDateTime.of(2009, 7, 31, 0, 0), 
                    "A comprehensive update of the leading algorithms text, with new material on matchings in bipartite graphs.", 
                    "1312", "English", "Hardcover"),
                
                createDetail("978-0131103627", "The C Programming Language", "ANSI C Version", 
                    "Brian W. Kernighan, Dennis M. Ritchie", "Prentice Hall", LocalDateTime.of(1988, 3, 22, 0, 0), 
                    "The definitive reference guide to C programming language.", 
                    "272", "English", "Paperback"),
                
                createDetail("978-0201485677", "Refactoring", "Improving the Design of Existing Code", 
                    "Martin Fowler", "Addison-Wesley", LocalDateTime.of(1999, 7, 8, 0, 0), 
                    "Refactoring is the process of changing a software system in such a way that it does not alter the external behavior of the code.", 
                    "448", "English", "Paperback"),
                
                createDetail("978-0134757599", "Clean Architecture", "A Craftsman's Guide to Software Structure and Design", 
                    "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2017, 9, 20, 0, 0), 
                    "Building upon the success of best-sellers Clean Code and The Clean Coder, legendary software craftsman Robert C. Martin shows how to bring greater professionalism and discipline to application architecture and design.", 
                    "432", "English", "Paperback"),
                
                createDetail("978-0137081073", "The Clean Coder", "A Code of Conduct for Professional Programmers", 
                    "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2011, 5, 13, 0, 0), 
                    "Programmers who endure and succeed amidst swirling uncertainty and nonstop pressure share a common attribute: They care deeply about the practice of creating software.", 
                    "256", "English", "Paperback"),
                
                createDetail("978-0201634983", "Extreme Programming Explained", "Embrace Change", 
                    "Kent Beck", "Addison-Wesley", LocalDateTime.of(1999, 10, 20, 0, 0), 
                    "Software development projects can be fun, productive, and even daring. Yet they can consistently deliver value to a business and remain under control.", 
                    "224", "English", "Paperback"),
                
                createDetail("978-0135974445", "Agile Software Development", "Principles, Patterns, and Practices", 
                    "Robert C. Martin", "Prentice Hall", LocalDateTime.of(2002, 10, 25, 0, 0), 
                    "Written by a software developer for software developers, this book is a unique collection of the latest software development methods.", 
                    "552", "English", "Paperback"),
                
                createDetail("978-0201633610", "Patterns of Enterprise Application Architecture", "Enterprise Application Architecture Patterns", 
                    "Martin Fowler", "Addison-Wesley", LocalDateTime.of(2002, 11, 15, 0, 0), 
                    "The practice of enterprise application development has benefited from the emergence of many new enabling technologies.", 
                    "560", "English", "Hardcover"),
                
                createDetail("978-0132350884", "Domain-Driven Design", "Tackling Complexity in the Heart of Software", 
                    "Eric Evans", "Addison-Wesley", LocalDateTime.of(2003, 8, 30, 0, 0), 
                    "Domain-Driven Design fills that need. This is not a book about specific technologies. It offers readers a systematic approach to domain-driven design.", 
                    "560", "English", "Hardcover"),
                
                createDetail("978-0201633610", "Test Driven Development", "By Example", 
                    "Kent Beck", "Addison-Wesley", LocalDateTime.of(2002, 11, 18, 0, 0), 
                    "Clean code that works--now. This is the seeming contradiction that lies behind much of the pain of programming.", 
                    "240", "English", "Paperback"),
                
                createDetail("978-0132350884", "Working Effectively with Legacy Code", "A Guide to Working with Legacy Code", 
                    "Michael Feathers", "Prentice Hall", LocalDateTime.of(2004, 9, 17, 0, 0), 
                    "Get more out of your legacy systems: more performance, functionality, reliability, and manageability.", 
                    "464", "English", "Paperback"),
                
                createDetail("978-0201633610", "Continuous Integration", "Improving Software Quality and Reducing Risk", 
                    "Paul M. Duvall, Steve Matyas, Andrew Glover", "Addison-Wesley", LocalDateTime.of(2007, 7, 6, 0, 0), 
                    "For any software developer who has spent days in 'integration hell,' cobbling together myriad software components, Continuous Integration is the answer.", 
                    "288", "English", "Paperback"),
                
                createDetail("978-0137081073", "Continuous Delivery", "Reliable Software Releases through Build, Test, and Deployment Automation", 
                    "Jez Humble, David Farley", "Addison-Wesley", LocalDateTime.of(2010, 7, 27, 0, 0), 
                    "Getting software released to users is often a painful, risky, and time-consuming process.", 
                    "512", "English", "Paperback"),
                
                createDetail("978-0201634983", "The DevOps Handbook", "How to Create World-Class Agility, Reliability, and Security in Technology Organizations", 
                    "Gene Kim, Jez Humble, Patrick Debois, John Willis", "IT Revolution Press", LocalDateTime.of(2016, 10, 5, 0, 0), 
                    "More than ever, the effective management of technology is critical for business competitiveness.", 
                    "480", "English", "Hardcover"),
                
                createDetail("978-0135974445", "Site Reliability Engineering", "How Google Runs Production Systems", 
                    "Betsy Beyer, Chris Jones, Jennifer Petoff, Niall Richard Murphy", "O'Reilly Media", LocalDateTime.of(2016, 4, 16, 0, 0), 
                    "The overwhelming majority of a software system's lifespan is spent in use, not in design or implementation.", 
                    "552", "English", "Paperback"),
                
                createDetail("978-0201633610", "The Phoenix Project", "A Novel About IT, DevOps, and Helping Your Business Win", 
                    "Gene Kim, Kevin Behr, George Spafford", "IT Revolution Press", LocalDateTime.of(2013, 1, 10, 0, 0), 
                    "Bill, an IT manager at Parts Unlimited, has been tasked with taking on a project critical to the future of the business.", 
                    "432", "English", "Paperback"),
                
                createDetail("978-0132350884", "The Unicorn Project", "A Novel About Developers, Digital Disruption, and Thriving in the Age of Data", 
                    "Gene Kim", "IT Revolution Press", LocalDateTime.of(2019, 11, 26, 0, 0), 
                    "The Unicorn Project is a novel about developers, digital disruption, and thriving in the age of data.", 
                    "352", "English", "Hardcover"),
                
                createDetail("978-0201634983", "Accelerate", "The Science of Lean Software and DevOps: Building and Scaling High Performing Technology Organizations", 
                    "Nicole Forsgren, Jez Humble, Gene Kim", "IT Revolution Press", LocalDateTime.of(2018, 3, 27, 0, 0), 
                    "Accelerate is the definitive guide to measuring and improving software delivery performance.", 
                    "288", "English", "Hardcover"),
                
                createDetail("978-0137081073", "The Goal", "A Process of Ongoing Improvement", 
                    "Eliyahu M. Goldratt, Jeff Cox", "North River Press", LocalDateTime.of(1984, 6, 1, 0, 0), 
                    "The Goal is a business novel that introduces the Theory of Constraints.", 
                    "384", "English", "Paperback"),
                
                createDetail("978-0135974445", "The Lean Startup", "How Today's Entrepreneurs Use Continuous Innovation to Create Radically Successful Businesses", 
                    "Eric Ries", "Crown Business", LocalDateTime.of(2011, 9, 13, 0, 0), 
                    "Most startups fail. But many of those failures are preventable.", 
                    "336", "English", "Hardcover"),
                
                createDetail("978-0201633610", "The Art of Computer Programming", "Volume 1: Fundamental Algorithms", 
                    "Donald E. Knuth", "Addison-Wesley", LocalDateTime.of(1997, 11, 10, 0, 0), 
                    "The Art of Computer Programming is Donald Knuth's comprehensive monograph on algorithms.", 
                    "672", "English", "Hardcover"),
                
                createDetail("978-0132350884", "Structure and Interpretation of Computer Programs", "SICP", 
                    "Harold Abelson, Gerald Jay Sussman, Julie Sussman", "MIT Press", LocalDateTime.of(1996, 7, 25, 0, 0), 
                    "Structure and Interpretation of Computer Programs has had a dramatic impact on computer science curricula over the past decade.", 
                    "657", "English", "Hardcover"),
                
                createDetail("978-0201634983", "Code Complete", "A Practical Handbook of Software Construction", 
                    "Steve McConnell", "Microsoft Press", LocalDateTime.of(2004, 6, 9, 0, 0), 
                    "Widely considered one of the best practical guides to programming, Steve McConnell's original CODE COMPLETE has been helping developers write better software for more than a decade.", 
                    "960", "English", "Paperback"),
                
                createDetail("978-0137081073", "The Pragmatic Programmer", "Your Journey to Mastery", 
                    "Andrew Hunt, David Thomas", "Addison-Wesley", LocalDateTime.of(1999, 10, 20, 0, 0), 
                    "The Pragmatic Programmer is one of those rare tech books you'll read, re-read, and read again over the years.", 
                    "352", "English", "Paperback"),
                
                createDetail("978-0135974445", "Designing Data-Intensive Applications", "The Big Ideas Behind Reliable, Scalable, and Maintainable Systems", 
                    "Martin Kleppmann", "O'Reilly Media", LocalDateTime.of(2017, 3, 16, 0, 0), 
                    "Data is at the center of many challenges in system design today.", 
                    "616", "English", "Paperback"),
                
                createDetail("978-0201633610", "Database Design for Mere Mortals", "A Hands-On Guide to Relational Database Design", 
                    "Michael J. Hernandez", "Addison-Wesley", LocalDateTime.of(2013, 3, 22, 0, 0), 
                    "Database Design for Mere Mortals has earned worldwide respect as the clearest, simplest tutorial on the relational database design process.", 
                    "672", "English", "Paperback"),
                
                createDetail("978-0132350884", "High Performance MySQL", "Optimization, Backups, and Replication", 
                    "Baron Schwartz, Peter Zaitsev, Vadim Tkachenko", "O'Reilly Media", LocalDateTime.of(2012, 3, 5, 0, 0), 
                    "Learn advanced techniques in depth. This book is an essential resource for MySQL administrators and developers.", 
                    "828", "English", "Paperback"),
                
                createDetail("978-0201634983", "Redis in Action", "Real-time Data with Redis", 
                    "Josiah L. Carlson", "Manning Publications", LocalDateTime.of(2013, 6, 3, 0, 0), 
                    "Redis is an in-memory database that offers extremely fast performance and flexibility.", 
                    "400", "English", "Paperback"),
                
                createDetail("978-0137081073", "MongoDB in Action", "Covers MongoDB version 3.0", 
                    "Kyle Banker", "Manning Publications", LocalDateTime.of(2016, 1, 15, 0, 0), 
                    "MongoDB in Action is a comprehensive guide to MongoDB for application developers.", 
                    "496", "English", "Paperback"),
                
                createDetail("978-0135974445", "Elasticsearch: The Definitive Guide", "A Distributed Real-Time Search and Analytics Engine", 
                    "Zachary Tong", "O'Reilly Media", LocalDateTime.of(2015, 2, 17, 0, 0), 
                    "Whether you need full-text search or real-time analytics of structured data, this book shows you how to develop with Elasticsearch.", 
                    "724", "English", "Paperback"),
                
                createDetail("978-0201633610", "Kafka: The Definitive Guide", "Real-Time Data and Stream Processing at Scale", 
                    "Neha Narkhede, Gwen Shapira, Todd Palino", "O'Reilly Media", LocalDateTime.of(2017, 8, 28, 0, 0), 
                    "Every enterprise application creates data, whether it's log messages, metrics, user activity, or outgoing messages.", 
                    "322", "English", "Paperback"),
                
                createDetail("978-0132350884", "Docker in Action", "Jeff Nickoloff", 
                    "Jeff Nickoloff", "Manning Publications", LocalDateTime.of(2016, 2, 1, 0, 0), 
                    "Docker in Action teaches you everything you need to know to use Docker effectively.", 
                    "384", "English", "Paperback"),
                
                createDetail("978-0201634983", "Kubernetes in Action", "Marko Lukša", 
                    "Marko Lukša", "Manning Publications", LocalDateTime.of(2018, 1, 15, 0, 0), 
                    "Kubernetes in Action is a comprehensive guide to developing and running applications in a Kubernetes environment.", 
                    "672", "English", "Paperback"),
                
                createDetail("978-0137081073", "Terraform: Up & Running", "Writing Infrastructure as Code", 
                    "Yevgeniy Brikman", "O'Reilly Media", LocalDateTime.of(2019, 6, 25, 0, 0), 
                    "Terraform has become a key player in the DevOps world for defining, launching, and managing infrastructure as code across service providers.", 
                    "350", "English", "Paperback"),
                
                createDetail("978-0135974445", "Ansible: Up and Running", "Automating Configuration Management and Deployment the Easy Way", 
                    "Lorin Hochstein, René Moser", "O'Reilly Media", LocalDateTime.of(2016, 10, 25, 0, 0), 
                    "Ansible is a simple, but powerful, server and configuration management tool.", 
                    "398", "English", "Paperback"),
                
                createDetail("978-0201633610", "The Linux Programming Interface", "A Linux and UNIX System Programming Handbook", 
                    "Michael Kerrisk", "No Starch Press", LocalDateTime.of(2010, 10, 28, 0, 0), 
                    "The Linux Programming Interface is the definitive guide to the Linux and UNIX programming interface.", 
                    "1552", "English", "Hardcover"),
                
                createDetail("978-0132350884", "Advanced Programming in the UNIX Environment", "APUE", 
                    "W. Richard Stevens, Stephen A. Rago", "Addison-Wesley", LocalDateTime.of(2013, 6, 2, 0, 0), 
                    "For more than twenty years, serious C programmers have relied on one book for practical, in-depth knowledge of the programming interfaces that drive the UNIX and Linux kernels.", 
                    "1024", "English", "Hardcover"),
                
                createDetail("978-0201634983", "Computer Networks", "A Systems Approach", 
                    "Larry L. Peterson, Bruce S. Davie", "Morgan Kaufmann", LocalDateTime.of(2011, 3, 15, 0, 0), 
                    "Computer Networks: A Systems Approach, Sixth Edition, explores the key principles of computer networking.", 
                    "920", "English", "Hardcover"),
                
                createDetail("978-0137081073", "Computer Organization and Design", "The Hardware/Software Interface", 
                    "David A. Patterson, John L. Hennessy", "Morgan Kaufmann", LocalDateTime.of(2013, 10, 29, 0, 0), 
                    "Computer Organization and Design: The Hardware/Software Interface presents the interaction between hardware and software at a variety of levels.", 
                    "800", "English", "Hardcover"),
                
                createDetail("978-0135974445", "Operating System Concepts", "Abraham Silberschatz, Peter B. Galvin, Greg Gagne", 
                    "Abraham Silberschatz, Peter B. Galvin, Greg Gagne", "Wiley", LocalDateTime.of(2018, 12, 7, 0, 0), 
                    "Operating System Concepts, now in its ninth edition, continues to provide a solid theoretical foundation for understanding operating systems.", 
                    "1040", "English", "Hardcover"),
                
                createDetail("978-0201633610", "Modern Operating Systems", "Andrew S. Tanenbaum, Herbert Bos", 
                    "Andrew S. Tanenbaum, Herbert Bos", "Pearson", LocalDateTime.of(2014, 3, 21, 0, 0), 
                    "Modern Operating Systems, Fourth Edition, is intended for introductory courses in operating systems in computer science, computer engineering, and electrical engineering programs.", 
                    "1136", "English", "Hardcover")
            );
            
            detailRepository.saveAll(details);
            System.out.println("BookDataSeeder: Successfully created " + details.size() + " book details");
        } else {
            System.out.println("BookDataSeeder: Book details already exist in database, skipping");
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
        if (bookRepository.count() == 0) {
            List<Detail> details = detailRepository.findAll();
            List<Account> accounts = accountRepository.findAll();
            
            System.out.println("BookDataSeeder: Found " + details.size() + " details and " + accounts.size() + " accounts");
            
            if (!details.isEmpty() && !accounts.isEmpty()) {
                Account defaultAccount = accounts.get(0);
                System.out.println("BookDataSeeder: Using account: " + defaultAccount.getUsername());
                
                // Create books with varied prices and quantities
                List<Book> books = List.of(
                    createBook("Clean Code", 45.99f, 10, "AVAILABLE", details.get(0), defaultAccount),
                    createBook("Design Patterns", 52.50f, 8, "AVAILABLE", details.get(1), defaultAccount),
                    createBook("Effective Java", 39.99f, 15, "AVAILABLE", details.get(2), defaultAccount),
                    createBook("Head First Java", 34.99f, 12, "AVAILABLE", details.get(3), defaultAccount),
                    createBook("Core Java Volume I", 49.99f, 6, "AVAILABLE", details.get(4), defaultAccount),
                    createBook("Spring in Action", 44.99f, 9, "AVAILABLE", details.get(5), defaultAccount),
                    createBook("Programming Hive", 29.99f, 5, "AVAILABLE", details.get(6), defaultAccount),
                    createBook("Learning Python", 59.99f, 7, "AVAILABLE", details.get(7), defaultAccount),
                    createBook("JavaScript: The Good Parts", 24.99f, 11, "AVAILABLE", details.get(8), defaultAccount),
                    createBook("Introduction to Algorithms", 89.99f, 4, "AVAILABLE", details.get(9), defaultAccount),
                    createBook("The C Programming Language", 35.99f, 8, "AVAILABLE", details.get(10), defaultAccount),
                    createBook("Refactoring", 42.50f, 6, "AVAILABLE", details.get(11), defaultAccount),
                    createBook("Clean Architecture", 48.99f, 9, "AVAILABLE", details.get(12), defaultAccount),
                    createBook("The Clean Coder", 38.99f, 7, "AVAILABLE", details.get(13), defaultAccount),
                    createBook("Extreme Programming Explained", 32.99f, 5, "AVAILABLE", details.get(14), defaultAccount),
                    createBook("Agile Software Development", 55.99f, 4, "AVAILABLE", details.get(15), defaultAccount),
                    createBook("Patterns of Enterprise Application Architecture", 65.99f, 3, "AVAILABLE", details.get(16), defaultAccount),
                    createBook("Domain-Driven Design", 58.99f, 6, "AVAILABLE", details.get(17), defaultAccount),
                    createBook("Test Driven Development", 36.99f, 8, "AVAILABLE", details.get(18), defaultAccount),
                    createBook("Working Effectively with Legacy Code", 44.99f, 5, "AVAILABLE", details.get(19), defaultAccount),
                    createBook("Continuous Integration", 39.99f, 7, "AVAILABLE", details.get(20), defaultAccount),
                    createBook("Continuous Delivery", 52.99f, 4, "AVAILABLE", details.get(21), defaultAccount),
                    createBook("The DevOps Handbook", 69.99f, 3, "AVAILABLE", details.get(22), defaultAccount),
                    createBook("Site Reliability Engineering", 74.99f, 2, "AVAILABLE", details.get(23), defaultAccount),
                    createBook("The Phoenix Project", 42.99f, 6, "AVAILABLE", details.get(24), defaultAccount),
                    createBook("The Unicorn Project", 49.99f, 4, "AVAILABLE", details.get(25), defaultAccount),
                    createBook("Accelerate", 54.99f, 5, "AVAILABLE", details.get(26), defaultAccount),
                    createBook("The Goal", 29.99f, 9, "AVAILABLE", details.get(27), defaultAccount),
                    createBook("The Lean Startup", 34.99f, 8, "AVAILABLE", details.get(28), defaultAccount),
                    createBook("The Art of Computer Programming", 99.99f, 2, "AVAILABLE", details.get(29), defaultAccount),
                    createBook("Structure and Interpretation of Computer Programs", 79.99f, 3, "AVAILABLE", details.get(30), defaultAccount),
                    createBook("Code Complete", 69.99f, 4, "AVAILABLE", details.get(31), defaultAccount),
                    createBook("The Pragmatic Programmer", 44.99f, 7, "AVAILABLE", details.get(32), defaultAccount),
                    createBook("Designing Data-Intensive Applications", 64.99f, 5, "AVAILABLE", details.get(33), defaultAccount),
                    createBook("Database Design for Mere Mortals", 49.99f, 6, "AVAILABLE", details.get(34), defaultAccount),
                    createBook("High Performance MySQL", 59.99f, 4, "AVAILABLE", details.get(35), defaultAccount),
                    createBook("Redis in Action", 39.99f, 8, "AVAILABLE", details.get(36), defaultAccount),
                    createBook("MongoDB in Action", 44.99f, 6, "AVAILABLE", details.get(37), defaultAccount),
                    createBook("Elasticsearch: The Definitive Guide", 54.99f, 3, "AVAILABLE", details.get(38), defaultAccount),
                    createBook("Kafka: The Definitive Guide", 49.99f, 5, "AVAILABLE", details.get(39), defaultAccount),
                    createBook("Docker in Action", 42.99f, 7, "AVAILABLE", details.get(40), defaultAccount),
                    createBook("Kubernetes in Action", 64.99f, 4, "AVAILABLE", details.get(41), defaultAccount),
                    createBook("Terraform: Up & Running", 39.99f, 6, "AVAILABLE", details.get(42), defaultAccount),
                    createBook("Ansible: Up and Running", 36.99f, 8, "AVAILABLE", details.get(43), defaultAccount),
                    createBook("The Linux Programming Interface", 89.99f, 2, "AVAILABLE", details.get(44), defaultAccount),
                    createBook("Advanced Programming in the UNIX Environment", 74.99f, 3, "AVAILABLE", details.get(45), defaultAccount),
                    createBook("Computer Networks", 69.99f, 4, "AVAILABLE", details.get(46), defaultAccount),
                    createBook("Computer Organization and Design", 79.99f, 3, "AVAILABLE", details.get(47), defaultAccount),
                    createBook("Operating System Concepts", 84.99f, 2, "AVAILABLE", details.get(48), defaultAccount),
                    createBook("Modern Operating Systems", 89.99f, 2, "AVAILABLE", details.get(49), defaultAccount)
                );
                
                bookRepository.saveAll(books);
                System.out.println("BookDataSeeder: Successfully created " + books.size() + " books");
            } else {
                System.out.println("BookDataSeeder: Cannot create books - missing details or accounts");
                if (details.isEmpty()) {
                    System.out.println("BookDataSeeder: No details found in database");
                }
                if (accounts.isEmpty()) {
                    System.out.println("BookDataSeeder: No accounts found in database");
                }
            }
        } else {
            System.out.println("BookDataSeeder: Books already exist in database, skipping");
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
} 