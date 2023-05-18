import bookModel from "./BookModel";

class ShelfCurrentLoans {
    book: bookModel;
    daysLeft: number;

    constructor(book: bookModel, daysLeft: number) {
        this.book = book;
        this.daysLeft = daysLeft;
    }
}

export default ShelfCurrentLoans;