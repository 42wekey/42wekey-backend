package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.request.WikiRequestDTO;
import com.ftence.ftwekey.dto.response.WikiResponseDTO;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.entity.Wiki;
import com.ftence.ftwekey.repository.SubjectRepository;
import com.ftence.ftwekey.repository.UserRepository;
import com.ftence.ftwekey.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WikiService {

    private final WikiRepository wikiRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public WikiResponseDTO getWiki(String subjectName) {

        Subject subject = subjectRepository.findByName(subjectName);
        Wiki wiki = wikiRepository.findById(subject.getWikiId()).get();

        return entityToWikiResponseDTO(wiki);
    }

    public boolean setWiki(PrincipalDetails principalUser, String subjectName, WikiRequestDTO wikiRequestDTO) {

        Subject subject = subjectRepository.findByName(subjectName);

        if (subject.getWikiId() == wikiRequestDTO.getId()) {

            User user = userRepository.findByIntraId(principalUser.getUsername());

            wikiRequestDtoToEntity(subject, user, wikiRequestDTO);

            return false;
        }
        return true;
    }

    private WikiResponseDTO entityToWikiResponseDTO(Wiki wiki) {

        return WikiResponseDTO.builder()
                .id(wiki.getId())
                .content(wiki.getContent())
                .build();
    }

    private void wikiRequestDtoToEntity(Subject subject, User user, WikiRequestDTO wikiRequestDTO) {

        Wiki wiki = Wiki.builder()
                .subject(subject)
                .content(wikiRequestDTO.getContent())
                .user(user)
                .build();

        wikiRepository.save(wiki);

        subject.setWikiId(wiki.getId());
        subjectRepository.save(subject);
    }
}
