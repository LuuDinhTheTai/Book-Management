package com.me.book_management.constant;

import java.util.List;

public class Constants {

    public static class CLASSNAME {

        public static final String BOOK = "Book";
        public static final String COMMENT = "Comment";
        public static final String ACCOUNT = "Account";
        public static final String CART = "Cart";
        public static final String ROLE = "Role";
        public static final String CATEGORY = "Category";
        public static final String ADDRESS = "Address";

        public static List<String> list() {
            return List.of(BOOK, COMMENT, ACCOUNT, CART, ROLE, CATEGORY, ADDRESS);
        }
    }

    public static class ROLE {

        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
        
        public static List<String> list() {
            return List.of(ADMIN, USER);
        }
    }

    public static class COOKIE {

        public static final String ACCESS_TOKEN = "token";
    }

    public static class BOOK_STATUS {

        public static final String AVAILABLE = "Available";
        public static final String SOLD_OUT = "Sold out";

        public static List<String> list() {
            return List.of(AVAILABLE, SOLD_OUT);
        }
    }

    public static class BOOK_FORMAT {

        public static final String EBOOK = "eBook";

        public static List<String> list() {
            return List.of(EBOOK);
        }
    }

    public static class BOOK_LANGUAGE {

        public static final String EN = "English";
        public static final String VI = "Vietnamese";

        public static List<String> list() {
            return List.of(EN, VI);
        }
    }

    public static class CART_STATUS {

        public static final String PENDING = "Pending";
        public static final String PROCESSING = "Processing";
        public static final String COMPLETED = "Completed";
    }

    public static class PAYMENT_METHOD {

        public static final String CASH = "Cash";

        public static List<String> list() {
            return List.of(CASH);
        }
    }

    public static class SHIPPING_METHOD {

        public static final String COD = "COD";

        public static List<String> list() {
            return List.of(COD);
        }
    }

    public static class PATH {

        public static final String Book = "book/";
        public static final String Cover = "cover/";
    }
}
