package ru.skypro.kakavito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.kakavito.exceptions.ImageSizeExceededException;
import ru.skypro.kakavito.model.Image;
import ru.skypro.kakavito.service.impl.ImageServiceImpl;
import ru.skypro.kakavito.repository.ImageRepo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.skypro.kakavito.util.Constant.ID1;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

    @Mock
    private ImageRepo imageRepo;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void findImageByIdTest() {
        Image image = new Image();

        when(imageRepo.findById(ID1)).thenReturn(Optional.of(image));

        Image expected = image;
        Image actual = imageService.findImageById(ID1);
        assertEquals(expected, actual);
    }

    @Test
    public void upLoadImageTest() throws IOException, ImageSizeExceededException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "image", "image.jpg", "jpg", "data".getBytes(StandardCharsets.UTF_8));
        Image image = new Image();
        image.setData(multipartFile.getBytes());
        image.setFileSize(multipartFile.getSize());
        image.setMediaType(multipartFile.getContentType());
        when(imageRepo.save(image)).thenReturn(image);

        Image expected = image;
        Image actual = imageService.upLoadImage(multipartFile);
        assertEquals(expected, actual);
    }

//    @Test
//    public void deleteImageTest() {
//
//    }
//
//    @Test
//    public void updateImageTest() {
//        Image newImage = new Image();
//
//    }
//
//    @Test
//    public void updateImage() {
//
//    }
//
//    @Test
//    public void checkUserImageTest() {
//
//    }
}