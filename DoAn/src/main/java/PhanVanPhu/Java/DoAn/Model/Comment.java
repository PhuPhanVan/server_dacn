package PhanVanPhu.Java.DoAn.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo comment

    // Thêm mối quan hệ với Video
    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    @JsonBackReference
    private Video video;

    // Thêm mối quan hệ với Users
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // Thêm trường này để lưu thông tin người dùng
}
