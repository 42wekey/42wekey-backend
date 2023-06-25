package com.ftence.ftwekey.service;

import com.ftence.ftwekey.dto.request.SubjectRequestDTO;
import com.ftence.ftwekey.entity.*;
import com.ftence.ftwekey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final WikiRepository wikiRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final RatingRepository ratingRepository;

    public List<SubjectRequestDTO> getSubjectList() {

        return subjectRepository.findAll()
                .stream()
                .map(this::convertEntityToSubjectRequestDto)
                .collect(Collectors.toList());
    }

    public SubjectRequestDTO getSubjectInfo(Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId).get();

        return SubjectRequestDTO.builder()
                .subjectName(subject.getName())
                .circle(subject.getCircle())
                .subjectInfo(subject.getInfo())
                .description(subject.getDescription())
                .build();
    }

    public void createSubject(SubjectRequestDTO subjectRequestDTO) {

        convertSubjectRequestDtoToEntityForCreate(subjectRequestDTO);
    }

    public void editSubject(Long subjectId, SubjectRequestDTO subjectRequestDTO) {

        Subject subject = subjectRepository.findById(subjectId).get();

        convertSubjectRequestDtoToEntityForEdit(subject, subjectRequestDTO);
    }

    public void deleteSubject(Long subjectName) {

        Subject subject = subjectRepository.findById(subjectName).get();
        List<Wiki> wikis = wikiRepository.findBySubject(subject);
        List<Comment> comments = commentRepository.findBySubject(subject);
        List<Heart> hearts = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();

        for (Comment comment : comments)
            hearts.addAll(heartRepository.findByComment(comment));

        for (Comment comment : comments)
            ratings.add(comment.getRating());

        heartRepository.deleteAll(hearts);
        ratingRepository.deleteAll(ratings);
        commentRepository.deleteAll(comments);
        wikiRepository.deleteAll(wikis);
        subjectRepository.delete(subject);
    }

    private SubjectRequestDTO convertEntityToSubjectRequestDto(Subject subject) {

        return SubjectRequestDTO.builder()
                .subjectName(subject.getName())
                .circle(subject.getCircle())
                .subjectInfo(subject.getInfo())
                .description(subject.getDescription())
                .build();
    }

    private void convertSubjectRequestDtoToEntityForCreate(SubjectRequestDTO subjectRequestDTO) {

        Subject subject = Subject.builder()
                .name(subjectRequestDTO.getSubjectName())
                .circle(subjectRequestDTO.getCircle())
                .info(subjectRequestDTO.getSubjectInfo())
                .wikiId(0L)
                .description(subjectRequestDTO.getDescription())
                .commentCnt(0)
                .build();
        subjectRepository.save(subject);

        Wiki wiki = Wiki.builder()
                .subject(subject)
                .content(" ")
                .user(userRepository.findByIntraId("wonlim"))
                .build();
        wikiRepository.save(wiki);

        subject.setWikiId(wiki.getId());
        subjectRepository.save(subject);
    }

    private void convertSubjectRequestDtoToEntityForEdit(Subject subject, SubjectRequestDTO subjectRequestDTO) {

        subject.setCircle(subjectRequestDTO.getCircle());
        subject.setName(subjectRequestDTO.getSubjectName());
        subject.setInfo(subjectRequestDTO.getSubjectInfo());
        subject.setDescription(subjectRequestDTO.getDescription());

        subjectRepository.save(subject);
    }
}
