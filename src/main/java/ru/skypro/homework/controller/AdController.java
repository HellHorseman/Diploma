package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;

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

    @GetMapping("{id}")
    public ResponseEntity<Ad> getById(@PathVariable Long id) {
        Ad foundAd = adService.findById(id);
        if (foundAd == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundAd);
    }

    /*Получение комментариев объявления*/
    @GetMapping("{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByAd(@PathVariable Long id) {
        Ad foundAd = adService.findById(id);
        if (foundAd == null) {
            return ResponseEntity.notFound().build();
        }
        List<Comment> comments = foundAd.getComments();
        return ResponseEntity.ok(comments);
    }

    /*Добавление комментария к объявлению*/
//    @PostMapping("add_comment")
//    public ResponseEntity<Void> addComment() {
//
//        return ResponseEntity.ok().build();
//    }
    @PostMapping("add")
    public ResponseEntity<Void> addAd(@RequestBody Ad ad) {
        if (ad == null) {
            return ResponseEntity.notFound().build();
        }
        adServise.add(ad);
        return ResponseEntity.ok().build();
    }

    @PatchMapping()
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
