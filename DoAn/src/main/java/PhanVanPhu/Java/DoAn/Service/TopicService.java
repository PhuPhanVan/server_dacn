package PhanVanPhu.Java.DoAn.Service;

import PhanVanPhu.Java.DoAn.Model.Topic;
import PhanVanPhu.Java.DoAn.Repository.TopicRepository;
import PhanVanPhu.Java.DoAn.Repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;
    // Lấy danh sách tất cả các topic
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Lấy thông tin topic theo ID
    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    // Thêm hoặc cập nhật topic
    public Topic saveTopic(Topic topic) {   // Kiểm tra xem có Topic nào đã tồn tại với tên giống như tên của Topic mới không
        if (topicRepository.existsByName(topic.getName())) {
            throw new IllegalArgumentException("Topic with this name already exists.");
        }

        // Nếu không có trùng lặp, lưu topic mới
        return topicRepository.save(topic);
    }

    // Xóa topic theo ID
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

}