package com.me.book_management.constant;

import java.util.List;

public class Constants {

    public static class ROLE {

        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    public static class PERMISSION {

        public static  final String BRIDGE = "_";

        public static final String CREATE_BOOK = ACTION.CREATE + BRIDGE + RESOURCE.BOOK;
        public static final String READ_BOOK = ACTION.READ + BRIDGE + RESOURCE.BOOK;
        public static final String UPDATE_BOOK = ACTION.UPDATE + BRIDGE + RESOURCE.BOOK;
        public static final String DELETE_BOOK = ACTION.DELETE + BRIDGE + RESOURCE.BOOK;
        public static final String CREATE_COMMENT = ACTION.CREATE + BRIDGE + RESOURCE.COMMENT;
        public static final String READ_COMMENT = ACTION.READ + BRIDGE + RESOURCE.COMMENT;
        public static final String UPDATE_COMMENT = ACTION.UPDATE + BRIDGE + RESOURCE.COMMENT;
        public static final String DELETE_COMMENT = ACTION.DELETE + BRIDGE + RESOURCE.COMMENT;
        public static final String CREATE_ACCOUNT = ACTION.CREATE + BRIDGE + RESOURCE.ACCOUNT;
        public static final String READ_ACCOUNT = ACTION.READ + BRIDGE + RESOURCE.ACCOUNT;
        public static final String UPDATE_ACCOUNT = ACTION.UPDATE + BRIDGE + RESOURCE.ACCOUNT;
        public static final String DELETE_ACCOUNT = ACTION.DELETE + BRIDGE + RESOURCE.ACCOUNT;
        public static final String CREATE_CART = ACTION.CREATE + BRIDGE + RESOURCE.CART;
        public static final String READ_CART = ACTION.READ + BRIDGE + RESOURCE.CART;
        public static final String UPDATE_CART = ACTION.UPDATE + BRIDGE + RESOURCE.CART;
        public static final String DELETE_CART = ACTION.DELETE + BRIDGE + RESOURCE.CART;
        public static final String CREATE_ROLE = ACTION.CREATE + BRIDGE + RESOURCE.ROLE;
        public static final String READ_ROLE = ACTION.READ + BRIDGE + RESOURCE.ROLE;
        public static final String UPDATE_ROLE = ACTION.UPDATE + BRIDGE + RESOURCE.ROLE;
        public static final String DELETE_ROLE = ACTION.DELETE + BRIDGE + RESOURCE.ROLE;
        public static final String CREATE_CATEGORY = ACTION.CREATE + BRIDGE + RESOURCE.CATEGORY;
        public static final String READ_CATEGORY = ACTION.READ + BRIDGE + RESOURCE.CATEGORY;
        public static final String UPDATE_CATEGORY = ACTION.UPDATE + BRIDGE + RESOURCE.CATEGORY;
        public static final String DELETE_CATEGORY = ACTION.DELETE + BRIDGE + RESOURCE.CATEGORY;
        public static final String CREATE_ADDRESS = ACTION.CREATE + BRIDGE + RESOURCE.ADDRESS;
        public static final String READ_ADDRESS = ACTION.READ + BRIDGE + RESOURCE.ADDRESS;
        public static final String UPDATE_ADDRESS = ACTION.UPDATE + BRIDGE + RESOURCE.ADDRESS;
        public static final String DELETE_ADDRESS = ACTION.DELETE + BRIDGE + RESOURCE.ADDRESS;
    }

    public static class ACTION {

        public static final String CREATE = "CREATE";
        public static final String READ = "READ";
        public static final String UPDATE = "UPDATE";
        public static final String DELETE = "DELETE";
    }

    public static class RESOURCE {

        public static final String BOOK = "BOOK";
        public static final String COMMENT = "COMMENT";
        public static final String ACCOUNT = "ACCOUNT";
        public static final String CART = "CART";
        public static final String ROLE = "ROLE";
        public static final String CATEGORY = "CATEGORY";
        public static final String ADDRESS = "ADDRESS";
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
        public static final String AUDIOBOOK = "AudioBook";

        public static List<String> list() {
            return List.of(EBOOK, AUDIOBOOK);
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
        public static final String COMPLETED = "Completed";
    }

    public static class PAYMENT_METHOD {

        public static final String CASH = "Cash";
    }

    public static class SHIPPING_METHOD {

        public static final String COD = "COD";
        public static final String TRANSFER = "Transfer";
    }
}
