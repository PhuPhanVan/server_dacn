//package PhanVanPhu.Java.DoAn.Controller;
//
//import PhanVanPhu.Java.DoAn.Service.SpeechService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api")
//public class SpeechController {
//
//    @Autowired
//    private SpeechService speechService;
//
//    @PostMapping("/speech-compare")
//    public ResponseEntity<Double> compareSpeech(
//            @RequestParam("audio") MultipartFile audioFile,
//            @RequestParam("scriptText") String scriptText
//    ) {
//        if (audioFile.isEmpty()) {
//            return ResponseEntity.badRequest().body(0.0);
//        }
//
//        try {
//            // Gọi service để nhận dạng giọng nói
//            String recognizedText = speechService.recognizeSpeech(audioFile);
//
//            // So sánh văn bản
//            double similarity = compareTexts(scriptText, recognizedText);
//
//            return ResponseEntity.ok(similarity);
//        } catch (Exception e) {
//            // Log lỗi chi tiết
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(0.0);  // Trả lại 0.0 khi có lỗi
//        }
//    }
//
//    @GetMapping("/health-check")
//    public ResponseEntity<String> healthCheck() {
//        return ResponseEntity.ok("Server is up and running");
//    }
//
//    private double compareTexts(String correctText, String recognizedText) {
//        // Tách chuỗi thành các từ
//        String[] correctWords = correctText.split("\\s+");
//        String[] recognizedWords = recognizedText.split("\\s+");
//
//        int totalWords = recognizedWords.length;
//        int matchingWords = 0;
//
//        // So sánh từng từ
//        for (String recognizedWord : recognizedWords) {
//            for (String correctWord : correctWords) {
//                // Tính Levenshtein distance giữa hai từ
//                int distance = StringUtils.getLevenshteinDistance(correctWord, recognizedWord);
//                if (distance <= 1) {
//                    matchingWords++;
//                    break;
//                }
//            }
//        }
//
//        // Tránh chia cho 0
//        return totalWords > 0 ? (double) matchingWords / totalWords * 100 : 0.0;
//    }
//}
