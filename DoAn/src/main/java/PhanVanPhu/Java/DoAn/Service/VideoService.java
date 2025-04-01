package PhanVanPhu.Java.DoAn.Service;

import PhanVanPhu.Java.DoAn.Model.Video;
import PhanVanPhu.Java.DoAn.Repository.IVideoRepository;
import PhanVanPhu.Java.DoAn.Repository.IVideoRepository;
import PhanVanPhu.Java.DoAn.dto.TranscriptionDTO;
import PhanVanPhu.Java.DoAn.dto.VideoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {
    @Autowired
    private IVideoRepository videoRepository;

    public void save(Video video) {
        videoRepository.save(video);
    }

    public List<Video> findAll() {
        return videoRepository.findAll();
    }


    // Phương thức chuyển đổi từ Entity sang DTO
    public VideoDTO toDTO(Video video) {
        VideoDTO dto = new VideoDTO();
        dto.setId(video.getId());
        dto.setVideoId(video.getVideoId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setUrl(video.getUrl());
        dto.setTranscriptions(
                video.getTranscriptions().stream().map(transcription -> {
                    TranscriptionDTO tDto = new TranscriptionDTO();
                    tDto.setStartTime(transcription.getStartTime());
                    tDto.setEndTime(transcription.getEndTime());
                    tDto.setText(transcription.getText());
                    return tDto;
                }).collect(Collectors.toList())
        );
        return dto;
    }


    // Lấy dữ liệu từ database và chuyển đổi sang DTO
    public Video getVideoById(Long videoId) {
        // Sử dụng Optional để kiểm tra video có tồn tại hay không
        Optional<Video> video = videoRepository.findById(videoId);
        // Nếu không tìm thấy video, có thể ném ra exception hoặc trả về null
        return video.orElse(null); // Hoặc có thể ném exception nếu muốn
    }


}