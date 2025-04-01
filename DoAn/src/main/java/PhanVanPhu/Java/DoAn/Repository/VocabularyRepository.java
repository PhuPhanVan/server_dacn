package PhanVanPhu.Java.DoAn.Repository;

import PhanVanPhu.Java.DoAn.Model.Topic;
import PhanVanPhu.Java.DoAn.Model.Vocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    // Có thể thêm phương thức tùy chỉnh nếu cần, ví dụ:
    Vocabulary findByWord(String word);
    boolean existsByWordAndDefinition(String word, String definition);
    Optional<Vocabulary> findByWordIgnoreCase(String word);

    // moi them
    List<Vocabulary> findByTopic(Topic topic);
    @Query("SELECT v FROM Vocabulary v WHERE v.topic.id = :topicId")
    List<Vocabulary> findByTopicId(@Param("topicId") Long topicId);

}