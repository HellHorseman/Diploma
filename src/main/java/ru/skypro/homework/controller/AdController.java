package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Ad;

import java.util.List;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @GetMapping("getAllAds")
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads = adService.findAll();
        if (ads.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ads);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Ad> getById(@PathVariable Long id) {
        Ad foundAd = adService.findById(id);
        if (foundAd == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundAd);
    }

    @PostMapping("addAd")
    public ResponseEntity<Void> addAd(@RequestBody Ad ad) {
        if (ad == null) {
            return ResponseEntity.notFound().build();
        }
        adServise.add(ad);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateAd(@RequestBody Ad ad) {
        Ad foundAd = adService.find(ad);
        if (foundAd == null) {
            return ResponseEntity.notFound().build();
        }
        Ad correctedAd = adService.updateAd(ad);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteReportById(@PathVariable Long id) {
        Ad ad = adService.findById(id);
        if (ad == null) {
            return ResponseEntity.notFound().build();
        }
        adService.delete(ad);
        return ResponseEntity.ok().build();
    }

}
