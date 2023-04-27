package com.ftence.ftwekey.service;

import com.ftence.ftwekey.dto.request.SubjectRequestDTO;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.entity.Wiki;
import com.ftence.ftwekey.repository.SubjectRepository;
import com.ftence.ftwekey.repository.UserRepository;
import com.ftence.ftwekey.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final WikiRepository wikiRepository;

    public void createSubject(SubjectRequestDTO subjectRequestDTO) {

        convertSubjectRequestDtoToEntityForCreate(subjectRequestDTO);
    }

    public void editSubject(String subjectName, SubjectRequestDTO subjectRequestDTO) {

        // todo 예외 처리
        Subject subject = subjectRepository.findByName(subjectName);

        convertSubjectRequestDtoToEntityForEdit(subject, subjectRequestDTO);
    }

    public void deleteSubject(String subjectName) {

        // todo 예외처리
        Subject subject = subjectRepository.findByName(subjectName);

        subjectRepository.delete(subject);
    }

    private void convertSubjectRequestDtoToEntityForCreate(SubjectRequestDTO subjectRequestDTO) {

        Subject subject = Subject.builder()
                .circle(subjectRequestDTO.getCircle())
                .info(subjectRequestDTO.getSubjectInfo())
                .wikiId(0L)
                .description(subjectRequestDTO.getDescription())
                .commentCnt(0)
                .rating(0)
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
