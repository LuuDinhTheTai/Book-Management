package com.me.book_management.dto.request.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuyRequest {

    private Long bookId;
    private Integer qty = 1;
    private Long addressId;
    private String shippingMethod;
    private String paymentMethod;
}
