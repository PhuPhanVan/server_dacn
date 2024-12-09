package TranQuocToan.Java.DoAn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class VideoDTO {
    private Long id;
    private String videoId;
    private String title;
    private String description;
    private String url;
    private List<TranscriptionDTO> transcriptions;

    // Constructors, Getters, Setters
}