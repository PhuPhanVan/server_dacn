package TranQuocToan.Java.DoAn.Controller;


import TranQuocToan.Java.DoAn.Model.Video;
import TranQuocToan.Java.DoAn.Repository.IVideoRepository;
import TranQuocToan.Java.DoAn.Service.AudioService;
import TranQuocToan.Java.DoAn.Service.TranscriptionService;
import TranQuocToan.Java.DoAn.Service.VideoService;
import TranQuocToan.Java.DoAn.dto.TranscriptionDTO;
import TranQuocToan.Java.DoAn.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@Controller
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private IVideoRepository videoRepository;

    @Autowired
    private AudioService audioService;
    @Autowired
    private TranscriptionService transcriptionService;


    @PostMapping("/addVideo")
    public ResponseEntity<String> addVideo(@RequestBody Video video) {
        try {
            // Tạo URL cho video
            String videoUrl = "https://www.youtube.com/watch?v=" + video.getVideoId();
            video.setUrl(videoUrl); // Đặt URL cho video

            // Đặt trạng thái tải xuống audio là false cho video mới
            video.setAudioDownloaded(false);

            // Lưu video vào cơ sở dữ liệu
            Video savedVideo = videoRepository.save(video);

            // Tải audio ngay sau khi lưu video
            audioService.downloadAudioAndTranscribe(savedVideo); // Gọi phương thức tải audio

            return ResponseEntity.ok("Video added and audio downloaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding video: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Long id) {
        Video video = videoService.getVideoById(id); // Lấy đối tượng Video
        if (video == null) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy video
        }

        // Chuyển đổi từ Video sang VideoDTO
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(video.getId());
        videoDTO.setVideoId(video.getVideoId());
        videoDTO.setTitle(video.getTitle());
        videoDTO.setDescription(video.getDescription());
        videoDTO.setUrl(video.getUrl());
        videoDTO.setTranscriptions(video.getTranscriptions().stream()
                .map(transcription -> {
                    TranscriptionDTO tDto = new TranscriptionDTO();
                    tDto.setStartTime(transcription.getStartTime());
                    tDto.setEndTime(transcription.getEndTime());
                    tDto.setText(transcription.getText());
                    return tDto;
                }).collect(Collectors.toList()));

        return ResponseEntity.ok(videoDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.findAll();
        return ResponseEntity.ok(videos); // Trả về danh sách video dưới dạng JSON
    }

//    @PostMapping("/save-transcription")
//    public ResponseEntity<String> saveTranscription(@RequestParam String videoId, @RequestParam String transcription) throws IOException {
//        transcriptionService.saveTranscriptionsFromFile(videoId, transcription);
//        return ResponseEntity.ok("Transcription saved successfully.");
//    }
}
