package top.yuxiangyang.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.yuxiangyang.springbootlibrary.entity.Checkout;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    List<Checkout> findBooksByUserEmail(String userEmail);

    @Modifying
    @Query(value = "DELETE FROM checkout WHERE book_id = :book_id", nativeQuery = true)
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
