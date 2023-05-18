package top.yuxiangyang.springbootlibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import top.yuxiangyang.springbootlibrary.entity.History;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Page<History> findBooksByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM history WHERE book_id = :book_id", nativeQuery = true)
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
