package PhanVanPhu.Java.DoAn.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpeechRecognitionService {

    // Hàm so sánh độ chính xác giữa văn bản nhận diện và văn bản mẫu
    public double calculateAccuracy(String recognizedText, String referenceText) {
        // Chuyển đổi cả hai văn bản thành mảng các từ
        Set<String> recognizedWords = new HashSet<>(Arrays.asList(recognizedText.split("\\s+")));
        Set<String> referenceWords = new HashSet<>(Arrays.asList(referenceText.split("\\s+")));

        // Tìm số lượng từ giống nhau giữa hai văn bản
        Set<String> commonWords = new HashSet<>(recognizedWords);
        commonWords.retainAll(referenceWords);

        // Tính toán độ chính xác bằng cách chia số lượng từ giống nhau cho tổng số từ trong văn bản mẫu
        double accuracy = (double) commonWords.size() / referenceWords.size();
        return accuracy;
    }

    public static void main(String[] args) {
        SpeechRecognitionService service = new SpeechRecognitionService();

        // Văn bản nhận diện từ Whisper API
        String recognizedText = "This is the text that the user spoke";

        // Văn bản mẫu mà người dùng cần đọc
        String referenceText = "This is the text that the user is supposed to speak";

        // Tính toán độ chính xác
        double accuracy = service.calculateAccuracy(recognizedText, referenceText);
        System.out.println("Accuracy: " + accuracy * 100 + "%");
    }
}