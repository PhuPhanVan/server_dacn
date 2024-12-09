package TranQuocToan.Java.DoAn.Repository;

import TranQuocToan.Java.DoAn.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IVideoRepository extends JpaRepository<Video,Long> {
    List<Video> findAll(); // Phương thức để lấy tất cả video
    // Phương thức tìm video theo videoId
    // Tìm video theo videoId
    Optional<Video> findById(Long Id);



}
