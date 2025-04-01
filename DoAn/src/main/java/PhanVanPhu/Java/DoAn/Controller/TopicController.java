package PhanVanPhu.Java.DoAn.Controller;

import PhanVanPhu.Java.DoAn.Model.Topic;
import PhanVanPhu.Java.DoAn.Model.Vocabulary;
import PhanVanPhu.Java.DoAn.Service.TopicService;
import PhanVanPhu.Java.DoAn.Service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private VocabularyService vocabularyService;

    // Lấy danh sách tất cả các topic
    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    // Lấy topic theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return topicService.getTopicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Thêm mới một topic
    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic savedTopic = topicService.saveTopic(topic);
        return ResponseEntity.ok(savedTopic);
    }

    // Cập nhật topic
    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        return topicService.getTopicById(id).map(existingTopic -> {
            existingTopic.setName(updatedTopic.getName());
            Topic savedTopic = topicService.saveTopic(existingTopic);
            return ResponseEntity.ok(savedTopic);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Xóa topic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        if (topicService.getTopicById(id).isPresent()) {
            topicService.deleteTopic(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy danh sách từ vựng theo topic ID
    @GetMapping("/{id}/vocabularies")
    public ResponseEntity<List<Vocabulary>> getVocabulariesByTopicId(@PathVariable Long id) {
        Optional<Topic> topicOptional = topicService.getTopicById(id);

        if (topicOptional.isPresent()) {
            // Lấy danh sách từ vựng qua VocabularyService
            List<Vocabulary> vocabularies = vocabularyService.getVocabulariesByTopicId(id);
            return ResponseEntity.ok(vocabularies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}