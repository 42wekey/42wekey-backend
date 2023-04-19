package com.ftence.ftwekey.config.oauth;

import com.ftence.ftwekey.entity.Project;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.enums.Role;
import com.ftence.ftwekey.repository.ProjectRepository;
import com.ftence.ftwekey.repository.UserRepository;
import com.ftence.ftwekey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> userInfo = oAuth2User.getAttributes();

        checkNewUser(userInfo);

        return oAuth2User;
    }

    private void checkNewUser(Map<String, Object> userInfo) {

        String intraId = userInfo.get("login").toString();

        ArrayList array = (ArrayList) userInfo.get("cursus_users");
        LinkedHashMap object = (LinkedHashMap) array.get(1);


        User user = userRepository.findByIntraId(intraId);
        Project project;

        if (user == null) {

            user = User.builder()
                    .uniqueId(Long.valueOf(userInfo.get("id").toString()))
                    .intraId(userInfo.get("login").toString())
                    .level((Double) object.get("level"))
                    .role(Role.USER)
                    .reloadTime(LocalDateTime.now())
                    .refreshToken("aa")
                    .build();

            project = userService.getUserProjectInfo(user, userInfo);

        } else {

            project = userService.getUserProjectInfo(user, userInfo);

            user.setLevel((double) object.get("level"));
            user.setReloadTime(LocalDateTime.now());
        }

        project.setUser(user);
        userRepository.save(user);
        projectRepository.save(project);
    }
}
