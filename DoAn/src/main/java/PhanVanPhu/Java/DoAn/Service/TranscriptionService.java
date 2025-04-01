package PhanVanPhu.Java.DoAn.Service;

import PhanVanPhu.Java.DoAn.Model.Transcription;
import PhanVanPhu.Java.DoAn.Model.Video;
import PhanVanPhu.Java.DoAn.Repository.ITranscriptionRepository;
import PhanVanPhu.Java.DoAn.Repository.IVideoRepository;
import PhanVanPhu.Java.DoAn.utils.ISO8601Utils; // Import lớp ISO8601Utils
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

    @Transactional
    public void saveTranscriptions(List<String[]> transcriptions, Video video) {
        List<Transcription> transcriptionList = new ArrayList<>();
        for (String[] entry : transcriptions) {
            if (entry.length == 3) {
                String startTime = entry[0];
                String endTime = entry[1];
                String text = entry[2];

                // Chuyển đổi định dạng thời gian sang giây
                long startSeconds = ISO8601Utils.timeStringToSeconds(startTime);
                long endSeconds = ISO8601Utils.timeStringToSeconds(endTime);

                if (startSeconds < endSeconds) {
                    Transcription transcription = new Transcription();
                    transcription.setStartTime(startTime);
                    transcription.setEndTime(endTime);
                    transcription.setText(text);
                    transcription.setVideo(video);
                    transcriptionList.add(transcription);
                } else {
                    logger.warn("Start time must be less than end time for entry: " + Arrays.toString(entry));
                }
            } else {
                logger.warn("Invalid entry in transcription: " + Arrays.toString(entry));
            }
        }
        transcriptionRepository.saveAll(transcriptionList);
        logger.info("Saved {} transcriptions for video ID: {}", transcriptionList.size(), video.getId());
    }

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
            Video video = videoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Video not found with ID: " + id));
            saveTranscriptions(transcriptions, video);
        } catch (IOException e) {
            logger.error("Error reading TSV file: " + filePath, e);
        } catch (IllegalArgumentException e) {
            logger.error("Error finding video: " + e.getMessage());
        }
    }
}