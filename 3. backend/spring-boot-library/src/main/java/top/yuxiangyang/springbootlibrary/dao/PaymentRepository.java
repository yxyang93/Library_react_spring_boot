package top.yuxiangyang.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yuxiangyang.springbootlibrary.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByUserEmail(String userEmail);
}
