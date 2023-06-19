package com.ftence.ftwekey.controller;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.request.WikiRequestDTO;
import com.ftence.ftwekey.dto.response.*;
import com.ftence.ftwekey.service.RatingService;
import com.ftence.ftwekey.service.SubjectService;
import com.ftence.ftwekey.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final WikiService wikiService;
    private final RatingService ratingService;

    @GetMapping("/rank")
    public List<SubjectRankDTO> getRank() {

        return subjectService.getRank();
    }

    @GetMapping("/list")
    public List<SubjectListDTO> getSubjectList() {

        return subjectService.getList();
    }

    @GetMapping("/{subjectName}/description")
    public SubjectDescriptionDTO getSubjectDescription(@AuthenticationPrincipal PrincipalDetails user,
                                                       @PathVariable String subjectName) {

        return subjectService.getDescription(subjectName, user.getUser());
    }

    @GetMapping("/{subjectName}/wiki")
    public WikiResponseDTO getWiki(@PathVariable String subjectName) {

        return wikiService.getWiki(subjectName);
    }

    @PostMapping("/{subjectName}/wiki")
    public ResponseEntity<Void> setWiki(@AuthenticationPrincipal PrincipalDetails user,
                                        @PathVariable String subjectName,
                                        @RequestBody WikiRequestDTO wikiRequestDTO) {

        if (wikiService.setWiki(user, subjectName, wikiRequestDTO))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{subjectName}/rating")
    public SubjectRatingDTO getRatingAverage(@PathVariable String subjectName) {

        try {
            return ratingService.getRatingAverage(subjectName);
        }
        catch (NullPointerException e){
            return null;
        }
    }
}
