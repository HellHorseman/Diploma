package ru.skypro.kakavito.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.kakavito.dto.CommentDTO;
import ru.skypro.kakavito.dto.CommentsDTO;
import ru.skypro.kakavito.dto.CreateOrUpdateCommentDTO;
import ru.skypro.kakavito.exceptions.AdNotFoundException;
import ru.skypro.kakavito.exceptions.CommentNotFoundException;
import ru.skypro.kakavito.exceptions.UserNotAuthorizedException;
import ru.skypro.kakavito.exceptions.UserNotFoundException;
import ru.skypro.kakavito.mappers.CommentMapper;
import ru.skypro.kakavito.model.Ad;
import ru.skypro.kakavito.model.Comment;
import ru.skypro.kakavito.model.User;
import ru.skypro.kakavito.repository.AdRepo;
import ru.skypro.kakavito.repository.CommentRepo;
import ru.skypro.kakavito.repository.UserRepo;
import ru.skypro.kakavito.service.impl.CommentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.skypro.kakavito.util.Constant.*;
import static ru.skypro.kakavito.util.DTOXover.EntityDtoFactory.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {
    @Mock
    CommentRepo commentRepo;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @Mock
    AdRepo adRepo;

    @Mock
    UserRepo userRepo;

    @Mock
    CommentMapper commentMapper;

    @InjectMocks
    CommentServiceImpl out;

    @Test
    void testGetCommentsForAd() {
        Comment comment = getComment();
        when(commentMapper.toDto(any(Comment.class))).thenReturn(getCommentDto());
        when(commentRepo.getAllByAdId(ID1)).thenReturn(List.of(comment));
        when(commentMapper.toCommentsDTO(List.of(comment))).thenReturn(new CommentsDTO(1, List.of(getCommentDto())));

        CommentsDTO expected = new CommentsDTO(1, List.of(commentMapper.toDto(comment)));
        assertThat(out.getAllByCommentById(ID1)).isEqualTo(expected);
        assertThat(out.getAllByCommentById(ID1).getCount()).isEqualTo(1);
    }

    @Test
    void testAddCommentToAd() {
        setAuthentication();
        when(adRepo.findById(ID1)).thenReturn(Optional.of(getAd()));
        when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.of(getUser()));
        when(commentRepo.save(any(Comment.class))).thenReturn(getComment());
        when(commentMapper.toDto(getComment())).thenReturn(getCommentDto());

        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText(COMMENT_TEXT);
        assertThat(out.createComment(ID1, createOrUpdateCommentDTO).getText()).isEqualTo(COMMENT_TEXT);
    }

    @Test
    void addCommentToAd_shouldThrow() {
        when(adRepo.findById(ID1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> out.createComment(ID1, new CreateOrUpdateCommentDTO()))
                .isInstanceOf(AdNotFoundException.class)
                .hasMessageContaining("Объявление с Id: " + ID1 + " не найдено");
    }

    @Test
    void deleteComment() {
        Ad ad = new Ad();
        ad.setId(ID1);
        Comment comment = new Comment();
        comment.setId(ID3);
        assertThatCode((() -> out.deleteComment(ad.getId(), comment.getId())))
                .doesNotThrowAnyException();
    }

    private void setAuthentication() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(getUserPrincipal());
    }
}
