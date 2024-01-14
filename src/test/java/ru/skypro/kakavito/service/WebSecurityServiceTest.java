package ru.skypro.kakavito.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.kakavito.exceptions.AdNotFoundException;
import ru.skypro.kakavito.exceptions.CommentNotFoundException;
import ru.skypro.kakavito.model.Ad;
import ru.skypro.kakavito.model.Comment;
import ru.skypro.kakavito.model.User;
import ru.skypro.kakavito.repository.AdRepo;
import ru.skypro.kakavito.repository.CommentRepo;
import ru.skypro.kakavito.service.impl.WebSecurityServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
public class WebSecurityServiceTest {
    @Mock
    AdRepo adRepo;
    @Mock
    CommentRepo commentRepo;
    @InjectMocks
    WebSecurityServiceImpl webSecurityService;


    @Test
    void canAccessInAdTest(){
        Ad ad = new Ad();
        User user = new User();
        user.setEmail("test");
        ad.setUser(user);
        when(adRepo.findById(anyInt())).thenReturn(Optional.of(ad));
        Assertions.assertTrue(webSecurityService.canAccessInAd(2, "test"));
    }

    @Test
    void deniedAccessInAdTest(){
        Ad ad = new Ad();
        User user = new User();
        user.setEmail("test");
        ad.setUser(user);
        when(adRepo.findById(anyInt())).thenReturn(Optional.of(ad));
        Assertions.assertFalse(webSecurityService.canAccessInAd(2, "test"));
    }

    @Test
    void notFoundAdInWebServiceTest(){
        when(adRepo.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(AdNotFoundException.class,() -> {
            webSecurityService.canAccessInAd(2, "TestName");
        });
    }

    @Test
    void canAccessInCommentTest(){
        Comment comment = new Comment();
        User user = new User();
        user.setEmail("test");
        comment.setUser(user);
        when(commentRepo.findById(anyInt())).thenReturn(Optional.of(comment));
        Assertions.assertTrue(webSecurityService.canAccessInComment(2, "test"));
    }

    @Test
    void deniedAccessInCommentTest(){
        Comment comment = new Comment();
        User user = new User();
        user.setEmail("test");
        comment.setUser(user);
        when(commentRepo.findById(anyInt())).thenReturn(Optional.of(comment));
        Assertions.assertFalse(webSecurityService.canAccessInComment(2, "TestName"));
    }

    @Test
    void notFoundCommentInWebServiceTest(){
        when(commentRepo.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(CommentNotFoundException.class,() -> {
            webSecurityService.canAccessInComment(2, "TestName");
        });
    }
}
