package PhanVanPhu.Java.DoAn.Controller;
import PhanVanPhu.Java.DoAn.Model.Topic;
import PhanVanPhu.Java.DoAn.Model.Vocabulary;
import PhanVanPhu.Java.DoAn.Repository.TopicRepository;
import PhanVanPhu.Java.DoAn.Service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/vocabularies")
public class VocabularyController {

    @Autowired
    private VocabularyService vocabularyService;
    private TopicRepository topicRepository;
    // Lấy danh sách tất cả từ vựng
    @GetMapping
    public List<Vocabulary> getAllVocabularies() {
        return vocabularyService.getAllVocabularies();
    }

    // Lấy từ vựng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Vocabulary> getVocabularyById(@PathVariable Long id) {
        return vocabularyService.getVocabularyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<String> createVocabulary(@RequestBody Vocabulary vocabulary) {
        List<Vocabulary> existingVocabularies = vocabularyService.getAllVocabularies();
        boolean isDuplicate = existingVocabularies.stream()
                .anyMatch(v -> v.getWord().equalsIgnoreCase(vocabulary.getWord())
                        && v.getDefinition().equalsIgnoreCase(vocabulary.getDefinition()));

        if (isDuplicate) {
            // Thông báo lỗi với từ và nghĩa đã tồn tại
            return ResponseEntity.badRequest().body(
                    String.format("Từ '%s' với nghĩa '%s' đã tồn tại!", vocabulary.getWord(), vocabulary.getDefinition()));
        }

        Vocabulary savedVocabulary = vocabularyService.saveVocabulary(vocabulary);
        return ResponseEntity.ok("Từ vựng được thêm thành công: " + savedVocabulary.getWord());
    }


    // Cập nhật từ vựng
    @PutMapping("/{id}")
    public ResponseEntity<Vocabulary> updateVocabulary(@PathVariable Long id, @RequestBody Vocabulary updatedVocabulary) {
        return vocabularyService.getVocabularyById(id).map(existingVocabulary -> {
            existingVocabulary.setWord(updatedVocabulary.getWord());
            existingVocabulary.setDefinition(updatedVocabulary.getDefinition());
            existingVocabulary.setPartOfspeech(updatedVocabulary.getPartOfspeech());
            existingVocabulary.setExample(updatedVocabulary.getExample());
            existingVocabulary.setAudio(updatedVocabulary.getAudio());
            Vocabulary savedVocabulary = vocabularyService.saveVocabulary(existingVocabulary);
            return ResponseEntity.ok(savedVocabulary);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Xóa từ vựng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVocabulary(@PathVariable Long id) {
        if (vocabularyService.getVocabularyById(id).isPresent()) {
            vocabularyService.deleteVocabulary(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



    ///////////
    // Lấy từ vựng theo chủ đề
    @GetMapping("/topic/{topicId}")
    public List<Vocabulary> getVocabulariesByTopic(@PathVariable Long topicId) {
        return vocabularyService.getVocabularyByTopic(topicId);
    }
    // Lấy tất cả chủ đề
    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();  // Lấy tất cả các Topic
    }


    // Tạo chủ đề mới
    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic savedTopic = vocabularyService.saveTopic(topic);
        return ResponseEntity.ok(savedTopic);
    }
}