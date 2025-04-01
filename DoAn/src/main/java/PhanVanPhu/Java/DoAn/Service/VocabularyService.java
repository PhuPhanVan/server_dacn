package PhanVanPhu.Java.DoAn.Service;
import PhanVanPhu.Java.DoAn.Model.Topic;
import PhanVanPhu.Java.DoAn.Model.Vocabulary;
import PhanVanPhu.Java.DoAn.Repository.TopicRepository;
import PhanVanPhu.Java.DoAn.Repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository vocabularyRepository;
    private TopicRepository topicRepository;

    // Lấy danh sách tất cả từ vựng
    public List<Vocabulary> getAllVocabularies() {
        return vocabularyRepository.findAll();
    }

    // Lấy thông tin từ vựng theo ID
    public Optional<Vocabulary> getVocabularyById(Long id) {
        return vocabularyRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật từ vựng
    public Vocabulary saveVocabulary(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }
    public boolean existsByWordAndDefinition(String word, String definition) {
        return vocabularyRepository.existsByWordAndDefinition(word, definition);
    }

    // Xóa từ vựng theo ID
    public void deleteVocabulary(Long id) {
        vocabularyRepository.deleteById(id);
    }

    // Lấy thông tin từ vựng theo từ
    public Vocabulary getVocabularyByWord(String word) {
        return vocabularyRepository.findByWord(word);
    }

    //////////////


    public List<Vocabulary> getVocabularyByTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found!"));
        return vocabularyRepository.findByTopic(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    // moi them

    public List<Vocabulary> getVocabulariesByTopicId(Long topicId) {
        return vocabularyRepository.findByTopicId(topicId);
    }

}