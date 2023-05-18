package top.yuxiangyang.springbootlibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import top.yuxiangyang.springbootlibrary.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);

    @Query(value = "SELECT * FROM book WHERE id IN (:book_ids)", nativeQuery = true)
    List<Book> findBooksByBookIds(@Param("book_ids") List<Long> bookIds);
}
