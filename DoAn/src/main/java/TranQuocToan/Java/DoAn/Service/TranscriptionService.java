package TranQuocToan.Java.DoAn.Service;

import TranQuocToan.Java.DoAn.Model.Transcription;
import TranQuocToan.Java.DoAn.Model.Video;
import TranQuocToan.Java.DoAn.Repository.ITranscriptionRepository;
import TranQuocToan.Java.DoAn.Repository.IVideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TranscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(TranscriptionService.class);

    @Autowired
    private ITranscriptionRepository transcriptionRepository;

    @Autowired
    private IVideoRepository videoRepository;

//    /**
//     * Lưu danh sách phiên âm vào cơ sở dữ liệu.
//     *
//     * @param transcriptions Danh sách các phiên âm dưới dạng mảng chuỗi.
//     * @param video Video liên quan.
//     */
    @Transactional

    public void saveTranscriptions(List<String[]> transcriptions, Video video) {
        List<Transcription> transcriptionList = new ArrayList<>();
        for (String[] entry : transcriptions) {
            if (entry.length == 3) {
                Transcription transcription = new Transcription();
                transcription.setStartTime(entry[0]);
                transcription.setEndTime(entry[1]);
                transcription.setText(entry[2]);
                transcription.setVideo(video); // Liên kết với Video
                transcriptionList.add(transcription);
            } else {
                logger.warn("Invalid entry in transcription: " + Arrays.toString(entry));
            }
        }
        transcriptionRepository.saveAll(transcriptionList); // Lưu theo lô
        logger.info("Saved {} transcriptions for video ID: {}", transcriptionList.size(), video.getId());
    }

    /**
     * Đọc tệp TSV và lưu các phiên âm vào cơ sở dữ liệu.
     *
     * @param filePath Đường dẫn đến tệp TSV.
     * @param id ID của video liên quan.
     */
    public void readTsvFile(String filePath, Long id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Bỏ qua dòng tiêu đề
            br.readLine();
            List<String[]> transcriptions = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                transcriptions.add(parts);
            }
            // Tìm video theo ID
            Video video = videoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Video not found with ID: " + id));
            saveTranscriptions(transcriptions, video);
        } catch (IOException e) {
            logger.error("Error reading TSV file: " + filePath, e);
        } catch (IllegalArgumentException e) {
            logger.error("Error finding video: " + e.getMessage());
        }
    }
}