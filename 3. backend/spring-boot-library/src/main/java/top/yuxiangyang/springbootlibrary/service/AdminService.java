package top.yuxiangyang.springbootlibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuxiangyang.springbootlibrary.dao.BookRepository;
import top.yuxiangyang.springbootlibrary.dao.CheckoutRepository;
import top.yuxiangyang.springbootlibrary.dao.HistoryRepository;
import top.yuxiangyang.springbootlibrary.dao.ReviewRepository;
import top.yuxiangyang.springbootlibrary.entity.Book;
import top.yuxiangyang.springbootlibrary.requestmodels.AddBookRequest;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;
    private HistoryRepository historyRepository;
    private CheckoutRepository checkoutRepository;

    public AdminService(BookRepository bookRepository, ReviewRepository reviewRepository,
                        HistoryRepository historyRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.historyRepository = historyRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public void increaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent()) {
            throw new Exception("Book doesn't exist");
        }

        book.get().setCopies(book.get().getCopies() + 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        bookRepository.save(book.get());
    }

    public void decreaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book doesn't exist or quantity locked");
        }

        book.get().setCopies(book.get().getCopies() - 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());
    }

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent()) {
            throw new Exception("Book doesn't exist");
        }

        bookRepository.deleteById(bookId);
        reviewRepository.deleteAllByBookId(bookId);
        historyRepository.deleteAllByBookId(bookId);
        checkoutRepository.deleteAllByBookId(bookId);
    }
}
