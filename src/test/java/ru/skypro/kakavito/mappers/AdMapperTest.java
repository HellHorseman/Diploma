package ru.skypro.kakavito.mappers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.skypro.kakavito.dto.AdDTO;
import ru.skypro.kakavito.dto.AdsDTO;
import ru.skypro.kakavito.dto.CreateOrUpdateAdDTO;
import ru.skypro.kakavito.dto.ExtendedAdDTO;
import ru.skypro.kakavito.model.Ad;
import ru.skypro.kakavito.model.Image;
import ru.skypro.kakavito.model.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdMapperTest {

    private final AdMapper adMapper = new AdMapper(new ModelMapper());

       @Test
    void convertToUpdateOrCreateDTO_ShouldMapCreateOrUpdateAdToAdEntity() {
        CreateOrUpdateAdDTO createOrUpdateAdDTO = new CreateOrUpdateAdDTO();
        createOrUpdateAdDTO.setTitle("Title");
        createOrUpdateAdDTO.setDescription("Description");
        createOrUpdateAdDTO.setPrice(100);

        Ad ad = adMapper.convertCreatDTOToAd(createOrUpdateAdDTO);

        assertEquals(createOrUpdateAdDTO.getTitle(), ad.getTitle());
        assertEquals(createOrUpdateAdDTO.getDescription(), ad.getDescription());
        assertEquals(createOrUpdateAdDTO.getPrice(), ad.getPrice());
    }

}
