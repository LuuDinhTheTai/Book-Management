package com.me.book_management.dto.response.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SalesExportRow {

    private Long bookId;
    private String bookName;
    private Long totalSold;
    private BigDecimal totalRevenue; // tổng tiền = sum(unitPrice * quantity)
}
