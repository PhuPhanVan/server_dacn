package PhanVanPhu.Java.DoAn.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Room {
    @Id
    @GeneratedValue // Thêm annotation này để tự động sinh ID
    private Long id; // Thêm trường ID để lưu trữ khóa chính
    private String displayName;
    private String uid;

    // Getters và Setters


}