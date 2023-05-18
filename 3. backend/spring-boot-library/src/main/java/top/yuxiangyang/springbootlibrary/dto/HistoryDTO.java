package top.yuxiangyang.springbootlibrary.dto;

import lombok.Data;

@Data
public class HistoryDTO {

    private Long id;
    private String userEmail;
    private String checkoutDate;
    private String returnedDate;
    private String title;
    private String author;
    private String description;
    private String img;

    public HistoryDTO() {
    }

    public HistoryDTO(Long id, String userEmail, String checkoutDate, String returnedDate,
                      String title, String author, String description, String img) {
        this.id = id;
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnedDate = returnedDate;
        this.title = title;
        this.author = author;
        this.description = description;
        this.img = img;
    }
}
