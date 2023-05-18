package top.yuxiangyang.springbootlibrary.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "amount")
    private double amount;

}
