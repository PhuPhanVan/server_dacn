package PhanVanPhu.Java.DoAn.Controller;

import PhanVanPhu.Java.DoAn.Model.Comment;
import PhanVanPhu.Java.DoAn.Model.Video;
import TranQuocToan.Java.DoAn.Repository.IUserRepository;
import TranQuocToan.Java.DoAn.Repository.IVideoRepository;
import TranQuocToan.Java.DoAn.Service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class CommentController {

    private final CommentService commentService;
    private IUserRepository userRepository;
    private IVideoRepository videoRepository;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @PostMapping("/{videoId}/comments")
//    public Comment addComment(@PathVariable Long videoId, @RequestBody Comment comment) {
//        // Lấy thông tin người dùng từ SecurityContext
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername(); // Sử dụng email hoặc username của người dùng
//
//        // Lấy thông tin người dùng từ cơ sở dữ liệu
////        User user = userRepository.findByEmail(username)
////                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        // Cập nhật người dùng và video vào bình luận
//        comment.setUser(user);
//        Video video = videoRepository.findById(videoId)
//                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));
//        comment.setVideo(video);
//
//        // Lưu bình luận
//        return commentService.addComment(comment);
//    }
}
