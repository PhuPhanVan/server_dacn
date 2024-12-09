package TranQuocToan.Java.DoAn.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Video {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoId;
    private String title;
    private String description;
    private String url;
    private boolean isAudioDownloaded; // Thêm thuộc tính để theo dõi trạng thái tải xuống audio

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Chỉ serialize danh sách này
    private List<Transcription> transcriptions = new ArrayList<>();
}
