package top.yuxiangyang.springbootlibrary.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "title")
    private String title;

    @Column(name = "question")
    private String question;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "response")
    private String response;

    @Column(name = "closed")
    private Boolean closed = false;

    public Message() {
    }

    public Message(String title, String question) {
        this.title = title;
        this.question = question;
    }

    public Message(Long id, String userEmail, String title, String question, String adminEmail, String response,
                   Boolean closed) {
        this.id = id;
        this.userEmail = userEmail;
        this.title = title;
        this.question = question;
        this.adminEmail = adminEmail;
        this.response = response;
        this.closed = closed;
    }

}
