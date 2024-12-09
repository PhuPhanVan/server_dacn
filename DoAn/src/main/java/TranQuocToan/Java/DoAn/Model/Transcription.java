package TranQuocToan.Java.DoAn.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transcription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startTime;
    private String endTime;
    private String text;

    @ManyToOne
    @JoinColumn(name = "video_id")
    @JsonBackReference // Bỏ qua khi serialize video
    private Video video; // Video liên quan
}
