package ru.skypro.kakavito.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.kakavito.dto.CommentsDTO;
import ru.skypro.kakavito.dto.CreateOrUpdateCommentDTO;
import ru.skypro.kakavito.exceptions.CommentNotFoundException;
import ru.skypro.kakavito.exceptions.EmptyException;
import ru.skypro.kakavito.model.Comment;
import ru.skypro.kakavito.repository.CommentRepo;
import ru.skypro.kakavito.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentsDTO getAllByCommentById(int pk) {
        logger.info("Comment getAllByCommentById is running");
        commentRepo.count();
        return commentRepo.getAllByAdPk(pk);
    }

    @Override
    public Comment createComment(CreateOrUpdateCommentDTO text) {
        logger.info("Comment createComment is running");
        final var comment = commentRepo.getCommentByText(String.valueOf(text)).orElseThrow();
        comment.setText(String.valueOf(text));
        return commentRepo.save(comment);
    }

    @Override
    public Comment findById(Long commentId) {
        logger.info("Comment findById is running");
        return commentRepo.findById(Math.toIntExact(commentId)).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    @Override
    public ResponseEntity<Comment> deleteComment(Long adId, Long commentId) {
        logger.info("Comment deleteComment is running");
        if (adId != null) {
            commentRepo.deleteById(Math.toIntExact(commentId));
        }
        throw new CommentNotFoundException("Cell is empty");
    }

    @Override
    public Comment updateComment(Long adId, Long commentId, CreateOrUpdateCommentDTO text) {
        logger.info("Comment updateComment is running");
        final var comment = commentRepo.findById(Math.toIntExact(commentId)).orElseThrow();
        comment.setText(String.valueOf(text));
        return commentRepo.save(comment);
    }
}