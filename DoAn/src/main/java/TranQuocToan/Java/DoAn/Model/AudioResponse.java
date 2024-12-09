package TranQuocToan.Java.DoAn.Model;

import lombok.Data;

@Data
public class AudioResponse {
    private String message;
    private String audioFilePath;
    private String videoUrl;

    public AudioResponse() {}

    public AudioResponse(String message, String audioFilePath, String videoUrl) {
        this.message = message;
        this.audioFilePath = audioFilePath;
        this.videoUrl = videoUrl;
    }

    public AudioResponse(String message) {
        this.message = message;
    }
}