package PhanVanPhu.Java.DoAn.Repository;

import PhanVanPhu.Java.DoAn.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideoId(Long videoId); // Lấy tất cả comment theo video ID
}
