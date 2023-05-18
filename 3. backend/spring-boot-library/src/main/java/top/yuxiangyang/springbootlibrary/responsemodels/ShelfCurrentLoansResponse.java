package top.yuxiangyang.springbootlibrary.responsemodels;

import lombok.Data;
import top.yuxiangyang.springbootlibrary.entity.Book;

@Data
public class ShelfCurrentLoansResponse {

    private Book book;
    private int daysLeft;

    public  ShelfCurrentLoansResponse(Book book, int daysLeft) {
        this.book = book;
        this.daysLeft = daysLeft;
    }
}
