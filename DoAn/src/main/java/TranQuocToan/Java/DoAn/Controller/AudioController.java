package TranQuocToan.Java.DoAn.Controller;

import TranQuocToan.Java.DoAn.Service.AudioService;
import TranQuocToan.Java.DoAn.Service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/audio-files")
public class AudioController {

    @Autowired
    private YouTubeService youTubeService;



    private final AudioService audioService;

    public AudioController(AudioService audioService) {
        this.audioService = audioService;
    }

    @PostMapping("/download-all")
    public ResponseEntity<String> downloadAllAudio() {
        try {
            audioService.downloadAllAudio(); // Gọi phương thức tải audio cho tất cả video
            return ResponseEntity.ok("All audio files downloaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error downloading audio: " + e.getMessage());
        }
    }



//    @PostMapping("/transcribe")
//    public Map<String, Object> transcribeFromYoutube(@RequestParam("url") String videoUrl) throws Exception {
//        // 1. Tải video từ YouTube
//        String videoPath = youTubeService.downloadVideo(videoUrl, "/tmp");
//
//        // 2. Trích xuất audio từ video
//        String audioPath = "/tmp/audio.mp3";
//        ffMpegService.extractAudioFromYoutube(videoPath, audioPath);
//
//        // 3. Gửi audio tới Whisper và nhận kết quả
//        String transcriptionJson = whisperService.transcribeAudio(audioPath);
//
//        // 4. Xóa file tạm
//        new File(videoPath).delete();
//        new File(audioPath).delete();
//
//        // 5. Trả về JSON kết quả
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(transcriptionJson, Map.class);
//    }
}
