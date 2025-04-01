package PhanVanPhu.Java.DoAn.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp Vocabulary đại diện cho một từ vựng.
 */
@Data                   // Tự động tạo getter, setter, toString, equals, hashCode
@NoArgsConstructor      // Tạo constructor không tham số
@AllArgsConstructor     // Tạo constructor với tất cả các tham số
@Entity
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;         // Từ vựng
    private String audio;
    private String partOfspeech;
    private String definition;
    private String example;      // Ví dụ sử dụng từ
    private String translatedMeaning; // Nghĩa đã dịch
    private String translatedDefinition; // Định nghĩa đã dịch
    private String translatedExample; // Định nghĩa đã dịch


    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic; // Chủ đề của từ vựng

}

//package TranQuocToan.Java.DoAn.Model;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;

//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * Lớp Vocabulary đại diện cho một từ vựng.
// */
//@Data                   // Tự động tạo getter, setter, toString, equals, hashCode
//@NoArgsConstructor      // Tạo constructor không tham số
//@AllArgsConstructor     // Tạo constructor với tất cả các tham số
//@Entity
//public class Vocabulary {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String word;         // Từ vựng
//    private String audio;
//    private String partOfspeech;
//    private String definition;
//    private String example;      // Ví dụ sử dụng từ
//
//
//
//
//    @ManyToOne
//    @JoinColumn(name = "topic_id")
//    private Topic topic; // Chủ đề của từ vựng
//
//}
//
