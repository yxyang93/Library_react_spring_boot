package top.yuxiangyang.springbootlibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuxiangyang.springbootlibrary.dao.BookRepository;
import top.yuxiangyang.springbootlibrary.dao.CheckoutRepository;
import top.yuxiangyang.springbootlibrary.dao.HistoryRepository;
import top.yuxiangyang.springbootlibrary.dao.PaymentRepository;
import top.yuxiangyang.springbootlibrary.entity.Book;
import top.yuxiangyang.springbootlibrary.entity.Checkout;
import top.yuxiangyang.springbootlibrary.entity.History;
import top.yuxiangyang.springbootlibrary.entity.Payment;
import top.yuxiangyang.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    private PaymentRepository paymentRepository;

    // Inject BookRepository, CheckoutRepository and HistoryRepository
    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository, PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    // Checkout a book
    public Book checkoutBook(String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out or no copies available");
        }

        List<Checkout> currentBooksCheckedOut = checkoutRepository.findBooksByUserEmail(userEmail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean bookNeedsReturned = false;
        for (Checkout checkout : currentBooksCheckedOut) {
            Date returnDate = sdf.parse(checkout.getReturnDate());
            Date currentDate = sdf.parse(LocalDate.now().toString());

            if (currentDate.after(returnDate)) {
                bookNeedsReturned = true;
                break;
            }
        }

        Payment userPayment = paymentRepository.findByUserEmail(userEmail);
        if (userPayment != null && (userPayment.getAmount() > 0 || bookNeedsReturned)) {
            throw new Exception("You have an overdue book and a fine to pay");
        }

        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setUserEmail(userEmail);
            payment.setAmount(00.00);
            paymentRepository.save(payment);
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                bookId
        );
        checkoutRepository.save(checkout);

        return book.get();
    }

    // Verify if a book is checked out
    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validateCheckout != null;
    }

    // Count the number of books checked out by a user
    public Integer currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();

        for (Checkout checkout : checkoutList) {
            bookIdList.add(checkout.getBookId());
        }

        List<Book> bookList = bookRepository.findBooksByBookIds(bookIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : bookList) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(c -> c.getBookId().equals(book.getId())).findFirst();
            if (checkout.isPresent()) {
                Date returnDate = sdf.parse(checkout.get().getReturnDate());
                Date currentDate = sdf.parse(LocalDate.now().toString());

                TimeUnit timeUnit = TimeUnit.DAYS;
                long diffInTime = timeUnit.convert(returnDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);

                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(
                        book,
                        (int) diffInTime
                ));
            }
        }

        return shelfCurrentLoansResponses;
    }

    // Return a book
    public void returnBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book doesn't exist or not checked out");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());

        // Payment related
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDate = sdf.parse(validateCheckout.getReturnDate());
        Date currentDate = sdf.parse(LocalDate.now().toString());

        TimeUnit timeUnit = TimeUnit.DAYS;
        long diffInTime = timeUnit.convert(returnDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);

        if (diffInTime < 0) {
            Payment payment = paymentRepository.findByUserEmail(userEmail);
            payment.setAmount(payment.getAmount() + Math.abs(diffInTime));
            paymentRepository.save(payment);
        }

        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get()
        );
        historyRepository.save(history);
    }

    // Renew a book
    public void renewLoan(String userEmail, Long bookId) throws Exception {

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDate = sdf.parse(validateCheckout.getReturnDate());
        Date currentDate = sdf.parse(LocalDate.now().toString());

        if (returnDate.before(currentDate)) {
            throw new Exception("Book is overdue");
        } else {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }
}
