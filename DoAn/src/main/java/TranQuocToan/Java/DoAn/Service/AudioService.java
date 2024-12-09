package TranQuocToan.Java.DoAn.Service;

import TranQuocToan.Java.DoAn.Model.Transcription;
import TranQuocToan.Java.DoAn.Model.Video;
import TranQuocToan.Java.DoAn.Repository.IVideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AudioService {

    @Autowired
    private IVideoRepository videoRepository;

    @Autowired
    private TranscriptionService transcriptionService;

    private static final Logger logger = LoggerFactory.getLogger(AudioService.class);
    private static final String AUDIO_DIRECTORY = "D:\\Java\\NHOM15B\\sourcecode\\server\\DoAn\\src\\main\\resources\\audio-files";

    // Method to download and process audio for all videos
    @Async
    public void downloadAllAudio() throws IOException, InterruptedException {
        List<Video> videos = videoRepository.findAll();
        for (Video video : videos) {
            if (!video.isAudioDownloaded()) {
                try {
                    downloadAudioAndTranscribe(video); // Combined method for clarity
                } catch (IOException | InterruptedException e) {
                    logger.error("Failed to download or transcribe audio for video ID: " + video.getVideoId(), e);
                }
            }
        }
    }

    // Combined method to download audio and transcribe
    public void downloadAudioAndTranscribe(Video video) throws IOException, InterruptedException {
        String audioFilePath = downloadAudio(video);
        if (audioFilePath != null) {
            transcribeAudio(audioFilePath);
            // Đường dẫn file phiên âm sẽ là audioFilePath nhưng với phần mở rộng là .tsv
            String transcriptionFilePath = audioFilePath.replace(".mp3", ".tsv");

            // Gọi phương thức đọc file và lưu vào cơ sở dữ liệu
            transcriptionService.readTsvFile(transcriptionFilePath, video.getId());
        }
    }



    // Download audio using yt-dlp
    public String downloadAudio(Video video) throws IOException, InterruptedException {
        String videoUrl = video.getUrl();
        String audioFileNameTemplate = AUDIO_DIRECTORY + File.separator + video.getVideoId() + ".mp3"; // Use specific ID for naming
        File audioDir = new File(AUDIO_DIRECTORY);

        if (!audioDir.exists()) {
            Files.createDirectories(audioDir.toPath());
        }

        // Check if yt-dlp is installed
        ProcessBuilder checkYtDlp = new ProcessBuilder("yt-dlp", "--version");
        Process process = checkYtDlp.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("yt-dlp is not installed or not found in the system path.");
        }

        // Download audio
        ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp", "-x", "--audio-format", "mp3", "-o", audioFileNameTemplate, videoUrl
        );
        processBuilder.directory(audioDir);
        process = processBuilder.start();
        exitCode = process.waitFor();

        if (exitCode == 0) {
            video.setAudioDownloaded(true);
            videoRepository.save(video);
            return audioFileNameTemplate; // Return the full path of the downloaded file
        } else {
            throw new IOException("Failed to download audio with yt-dlp.");
        }
    }

    // Transcribe the downloaded audio
//
    @Transactional
    public String transcribeAudio(String audioFilePath) throws IOException, InterruptedException {
        logger.info("Transcribing audio file: " + audioFilePath);
        File audioFile = new File(audioFilePath);

        if (!audioFile.exists() || !audioFile.canRead()) {
            logger.error("Audio file not found or cannot be read: " + audioFilePath);
            return null;
        }

        String pythonScriptPath = "D:\\Java\\NHOM15B\\sourcecode\\whisper\\whisper_script.py";
        String pythonExePath = "C:\\Program Files\\Python311\\python.exe";

        // Ensure Python executable and script exist
        if (!new File(pythonExePath).exists()) {
            logger.error("Python executable not found at path: " + pythonExePath);
            return null;
        }
        if (!new File(pythonScriptPath).exists()) {
            logger.error("Python script not found at path: " + pythonScriptPath);
            return null;
        }

        // Run the transcription process
        ProcessBuilder processBuilder = new ProcessBuilder(pythonExePath, pythonScriptPath, audioFilePath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder transcription = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transcription.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            logger.error("Error occurred during audio transcription: " + transcription.toString());
            return null;
        }

        // Lưu phiên âm vào file .tsv
        String transcriptionFilePath = audioFilePath.replace(".mp3", ".tsv");
        saveTranscriptionToFile(transcription.toString(), transcriptionFilePath);

        return transcription.toString();
    }

    // Save transcription to the database
    public void saveTranscriptionToDatabase(Video video, String transcriptionText) {
        Transcription transcription = new Transcription();
        transcription.setText(transcriptionText);
        transcription.setVideo(video);
        video.getTranscriptions().add(transcription); // Thêm phiên âm vào danh sách
        videoRepository.save(video); // Lưu video với danh sách phiên âm
    }

    private void saveTranscriptionToFile(String transcription, String transcriptionFilePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(transcriptionFilePath))) {
            writer.write("Start Time\tEnd Time\tText\n"); // Ghi tiêu đề
            writer.write(transcription); // Ghi nội dung
        } catch (IOException e) {
            logger.error("Error saving transcription to file: " + transcriptionFilePath, e);
        }
    }
}