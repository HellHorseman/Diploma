package ru.skypro.kakavito.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.kakavito.dto.AdDTO;
import ru.skypro.kakavito.dto.AdsDTO;
import ru.skypro.kakavito.dto.CreateOrUpdateAdDTO;
import ru.skypro.kakavito.dto.ExtendedAdDTO;
import ru.skypro.kakavito.exceptions.AdNotFoundException;
import ru.skypro.kakavito.mappers.AdMapper;
import ru.skypro.kakavito.model.Ad;
import ru.skypro.kakavito.repository.AdRepo;
import ru.skypro.kakavito.service.impl.AdServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdServiceTest {
    @Mock
    AdRepo adRepo;
    @Mock
    AdMapper adMapper;

    @InjectMocks
    AdServiceImpl adServiceImpl;


    @Test
    void getAllAdsWithAds() {
        Ad ad1 = new Ad();
        Ad ad2 = new Ad();
        when(adRepo.findAll()).thenReturn(Arrays.asList(ad1, ad2));

        AdDTO adDTO1 = new AdDTO();
        AdDTO adDTO2 = new AdDTO();
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setResults(Arrays.asList(adDTO1, adDTO2));
        when(adMapper.convertToAdsDTO(Arrays.asList(ad1, ad2))).thenReturn(adsDTO);

        AdsDTO result = adServiceImpl.findAll();
        Assertions.assertEquals(adsDTO, result);
    }

    @Test
    void getAllAdsWithoutAds() {
        when(adRepo.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(AdNotFoundException.class, () -> {
            adServiceImpl.findAll();
        });
    }

    @Test
    void getExtAd_WhenAdExists_ShouldReturnExtendedAd() {
        Integer adId = 1;
        Ad ad = new Ad();
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();

        when(adRepo.findById(adId)).thenReturn(Optional.of(ad));
        when(adMapper.convertToExtendedAd(ad)).thenReturn(extendedAdDTO);


        ExtendedAdDTO result = adServiceImpl.findById(adId);

        verify(adRepo, times(1)).findById(adId);
        verify(adMapper, times(1)).convertToExtendedAd(ad);
        Assertions.assertEquals(extendedAdDTO, result);
    }

    @Test
    void getExtAd_WhenAdDoesNotExist_ShouldReturnNull() {

        Integer id = 1;

        when(adRepo.findById(id)).thenReturn(Optional.empty());

        ExtendedAdDTO result = adServiceImpl.findById(id);

        verify(adRepo, times(1)).findById(id);
        verify(adMapper, never()).convertToExtendedAd(any());
        Assertions.assertNull(result);
    }

    @Test
    void deleteAd_WhenAdExistsAndUserCanAccess_ShouldReturnDeletedAd() {
        Integer id = 1;
        Ad ad = new Ad();
        AdDTO adDTO = new AdDTO();

        when(adRepo.findById(id)).thenReturn(Optional.of(ad));
        when(adMapper.convertToAdDTO(ad)).thenReturn(adDTO);

        adServiceImpl.deleteAd(id);

        verify(adRepo, times(1)).findById(id);
        verify(adRepo, times(1)).deleteById(id);
        verify(adMapper, times(1)).convertToAdDTO(ad);
        verify(adRepo).deleteById(id);
    }


    @Test
    void patchAd_WhenAdExists_ShouldReturnUpdatedAd() {

        Integer id = 1;
        CreateOrUpdateAdDTO updateAd = new CreateOrUpdateAdDTO();
        updateAd.setTitle("Updated Title");
        updateAd.setDescription("Updated Description");
        updateAd.setPrice(100);

        Ad existingAdEntity = new Ad();
        existingAdEntity.setId(id);
        existingAdEntity.setTitle("Original Title");
        existingAdEntity.setDescription("Original Description");
        existingAdEntity.setPrice(50);

        Ad updatedAdEntity = new Ad();
        updatedAdEntity.setId(id);
        updatedAdEntity.setTitle("Updated Title");
        updatedAdEntity.setDescription("Updated Description");
        updatedAdEntity.setPrice(100);

        AdDTO updateAdDTO = new AdDTO();
        updateAdDTO.setPk(id);
        updateAdDTO.setTitle("Updated Title");
        updateAdDTO.setPrice(100);

        when(adRepo.findById(id)).thenReturn(Optional.of(existingAdEntity));
        when(adRepo.save(any())).thenReturn(updatedAdEntity);
        when(adMapper.convertToAdDTO(updatedAdEntity)).thenReturn(updateAdDTO);

        AdDTO result = adServiceImpl.updateAd(id, updateAd);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateAdDTO, result);
        Assertions.assertEquals(updateAd.getTitle(), result.getTitle());
        Assertions.assertEquals(updateAd.getPrice(), result.getPrice());

        verify(adRepo, times(1)).findById(id);
        verify(adRepo, times(1)).save(existingAdEntity);
    }

    @Test
    void patchAd_WhenAdDoesNotExist_ShouldReturnNull() {

        Integer id = 1;
        CreateOrUpdateAdDTO updateAd = new CreateOrUpdateAdDTO();
        updateAd.setTitle("Updated Title");
        updateAd.setDescription("Updated Description");
        updateAd.setPrice(100);

        when(adRepo.findById(id)).thenReturn(Optional.empty());

        AdDTO result = adServiceImpl.updateAd(id, updateAd);

        Assertions.assertNull(result);

        verify(adRepo, times(1)).findById(id);
        verify(adRepo, never()).save(any());
    }
}
