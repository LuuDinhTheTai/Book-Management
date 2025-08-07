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

    public static class PERMISSION {

        public static final String CREATE_BOOK = ACTION.CREATE + RESOURCE.BOOK;
        public static final String READ_BOOK = ACTION.READ + RESOURCE.BOOK;
        public static final String UPDATE_BOOK = ACTION.UPDATE + RESOURCE.BOOK;
        public static final String DELETE_BOOK = ACTION.DELETE + RESOURCE.BOOK;
        public static final String CREATE_COMMENT = ACTION.CREATE + RESOURCE.COMMENT;
        public static final String READ_COMMENT = ACTION.READ + RESOURCE.COMMENT;
        public static final String UPDATE_COMMENT = ACTION.UPDATE + RESOURCE.COMMENT;
        public static final String DELETE_COMMENT = ACTION.DELETE + RESOURCE.COMMENT;
        public static final String CREATE_ACCOUNT = ACTION.CREATE + RESOURCE.ACCOUNT;
        public static final String READ_ACCOUNT = ACTION.READ + RESOURCE.ACCOUNT;
        public static final String UPDATE_ACCOUNT = ACTION.UPDATE + RESOURCE.ACCOUNT;
        public static final String DELETE_ACCOUNT = ACTION.DELETE + RESOURCE.ACCOUNT;
        public static final String CREATE_CART = ACTION.CREATE + RESOURCE.CART;
        public static final String READ_CART = ACTION.READ + RESOURCE.CART;
        public static final String UPDATE_CART = ACTION.UPDATE + RESOURCE.CART;
        public static final String DELETE_CART = ACTION.DELETE + RESOURCE.CART;
        public static final String CREATE_ROLE = ACTION.CREATE + RESOURCE.ROLE;
        public static final String READ_ROLE = ACTION.READ + RESOURCE.ROLE;
        public static final String UPDATE_ROLE = ACTION.UPDATE + RESOURCE.ROLE;
        public static final String DELETE_ROLE = ACTION.DELETE + RESOURCE.ROLE;
        public static final String CREATE_CATEGORY = ACTION.CREATE + RESOURCE.CATEGORY;
        public static final String READ_CATEGORY = ACTION.READ + RESOURCE.CATEGORY;
        public static final String UPDATE_CATEGORY = ACTION.UPDATE + RESOURCE.CATEGORY;
        public static final String DELETE_CATEGORY = ACTION.DELETE + RESOURCE.CATEGORY;

        public static List<String> list() {
            return List.of(CREATE_BOOK, READ_BOOK, UPDATE_BOOK, DELETE_BOOK, 
                          CREATE_COMMENT, READ_COMMENT, UPDATE_COMMENT, DELETE_COMMENT, 
                          CREATE_ACCOUNT, READ_ACCOUNT, UPDATE_ACCOUNT, DELETE_ACCOUNT, 
                          CREATE_CART, READ_CART, UPDATE_CART, DELETE_CART, 
                          CREATE_ROLE, READ_ROLE, UPDATE_ROLE, DELETE_ROLE, 
                          CREATE_CATEGORY, READ_CATEGORY, UPDATE_CATEGORY, DELETE_CATEGORY);
        }
    }

    public static class ACTION {

        public static final String CREATE = "Create";
        public static final String READ = "Read";
        public static final String UPDATE = "Update";
        public static final String DELETE = "Delete";

        public static List<String> list() {
            return List.of(CREATE, READ, UPDATE, DELETE);
        }
    }

    public static class RESOURCE {

        public static final String BOOK = "Book";
        public static final String COMMENT = "Comment";
        public static final String ACCOUNT = "Account";
        public static final String CART = "Cart";
        public static final String ROLE = "Role";
        public static final String CATEGORY = "Category";

        public static List<String> list() {
            return List.of(BOOK, COMMENT, ACCOUNT, CART, ROLE, CATEGORY);
        }
    }

    public static class COOKIE {

        public static final String ACCESS_TOKEN = "token";
    }

    public static class BOOK_STATUS {

        public static final String AVAILABLE = "Available";
        public static final String SOLD_OUT = "Sold out";
        public static final String ACTIVE = "ACTIVE";

        public static List<String> list() {
            return List.of(AVAILABLE, SOLD_OUT, ACTIVE);
        }
    }

    public static class BOOK_FORMAT {

        public static final String EBOOK = "eBook";
        public static final String AUDIOBOOK = "AudioBook";
        public static final String PAPERBACK = "Paperback";
        public static final String HARDCOVER = "Hardcover";

        public static List<String> list() {
            return List.of(EBOOK, AUDIOBOOK, PAPERBACK, HARDCOVER);
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
