package PhanVanPhu.Java.DoAn.Service;

import PhanVanPhu.Java.DoAn.Repository.ICommentRepository;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;


@Service
public class CommentService {

    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

}
