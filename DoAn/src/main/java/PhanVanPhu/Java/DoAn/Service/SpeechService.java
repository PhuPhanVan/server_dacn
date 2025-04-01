//package PhanVanPhu.Java.DoAn.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.util.Objects;
//
//
//@Service
//public class SpeechService {
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public SpeechService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//
//    public String recognizeSpeech(MultipartFile audioFile) throws Exception {
//        // Tạo thư mục uploads nếu chưa tồn tại
//        File uploadDir = new File("uploads");
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        // Lưu file âm thanh tạm thời
//        File tempFile = new File(uploadDir, Objects.requireNonNull(audioFile.getOriginalFilename()));
//        try {
//            audioFile.transferTo(tempFile);
//        } catch (IOException e) {
//            throw new Exception("Error saving audio file: " + e.getMessage(), e);
//        }
//
//        // Đường dẫn script Python và Python executable
//        String pythonExePath = "C:\\Program Files\\Python311\\python.exe";
//        String pythonScriptPath = "D:\\path\\to\\speech_audio.py";
//
//        // Kiểm tra file script Python
//        if (!new File(pythonScriptPath).exists()) {
//            throw new FileNotFoundException("Python script not found: " + pythonScriptPath);
//        }
//
//        // Sử dụng ProcessBuilder để chạy script Python
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                pythonExePath, pythonScriptPath, tempFile.getAbsolutePath()
//        );
//        processBuilder.redirectErrorStream(true); // Gộp lỗi và đầu ra
//
//        StringBuilder output = new StringBuilder();
//        try {
//            Process process = processBuilder.start();
//
//            // Đọc đầu ra từ script Python
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line).append("\n");
//                }
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new Exception("Python script exited with error. Exit code: " + exitCode);
//            }
//        } catch (Exception e) {
//            throw new Exception("Error during speech recognition: " + e.getMessage(), e);
//        } finally {
//            // Xóa file âm thanh tạm thời
//            deleteTempFile(tempFile);
//        }
//
//        // Trả về kết quả từ script Python
//        return output.toString().trim();
//    }
//
//    // Hàm xóa file tạm thời
//    private void deleteTempFile(File file) {
//        if (file.exists() && !file.delete()) {
//            System.err.println("Failed to delete temp file: " + file.getAbsolutePath());
//        }
//    }
//}
