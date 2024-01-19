package com.ftence.ftwekey.config.oauth;

import com.ftence.ftwekey.constant.OAuthProperties;
import com.ftence.ftwekey.constant.SubjectProperties;
import com.ftence.ftwekey.entity.Project;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.enums.Role;
import com.ftence.ftwekey.repository.ProjectRepository;
import com.ftence.ftwekey.repository.UserRepository;
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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> userInfo = oAuth2User.getAttributes();

        checkNewUser(userInfo);

        return oAuth2User;
    }

    private void checkNewUser(Map<String, Object> userInfo) {

        String intraId = userInfo.get(OAuthProperties.LOGIN).toString();

        ArrayList array = (ArrayList) userInfo.get(OAuthProperties.CURSUS_USERS);
        LinkedHashMap object = (LinkedHashMap) array.get(1);


        User user = userRepository.findByIntraId(intraId);
        Project project;

        if (user == null) {

            user = User.builder()
                    .uniqueId(Long.valueOf(userInfo.get(OAuthProperties.ID).toString()))
                    .intraId(userInfo.get(OAuthProperties.LOGIN).toString())
                    .level((Double) object.get(OAuthProperties.LEVEL))
                    .role(Role.USER)
                    .refreshToken("aa")
                    .build();

            project = getUserProjectInfo(null, user, userInfo);

        } else {

            project = projectRepository.findByUser(user);
            project = getUserProjectInfo(project, user, userInfo);

            user.setLevel((double) object.get(OAuthProperties.LEVEL));
            user.setReloadTime(LocalDateTime.now());
        }

        project.setUser(user);
        userRepository.save(user);
        projectRepository.save(project);
    }

    private Project getUserProjectInfo(Project project, User user, Map<String, Object> userInfo) {

        if (project == null) {
            project = new Project();
            project.setUser(user);
        }

        ArrayList<LinkedHashMap> projectsUsers = (ArrayList<LinkedHashMap>) userInfo.get(OAuthProperties.PROJECTS_USERS);

        for (LinkedHashMap o : projectsUsers) {

            LinkedHashMap p = (LinkedHashMap) o.get(OAuthProperties.PROJECT);

            Object validated = o.get(OAuthProperties.VALIDATED);

            switch (p.get(OAuthProperties.NAME).toString()) {
                // 0 서클
                case SubjectProperties.LIBFT:
                    if (validated == null)
                        project.setLibft(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setLibft(true);
                    else
                        project.setLibft(false);
                    break;
                // 1서클
                case SubjectProperties.FT_PRINTF:
                    if (validated == null)
                        project.setFt_printf(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setFt_printf(true);
                    else
                        project.setFt_printf(false);
                    break;
                case SubjectProperties.GET_NEXT_LINE:
                    if (validated == null)
                        project.setGet_next_line(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setGet_next_line(true);
                    else
                        project.setGet_next_line(false);
                    break;
                case SubjectProperties.BORN2BEROOT:
                    if (validated == null)
                        project.setBorn2beroot(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setBorn2beroot(true);
                    else
                        project.setBorn2beroot(false);
                    break;
                // 2서클
                case SubjectProperties.MINITALK:
                    if (validated == null)
                        project.setMinitalk(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setMinitalk(true);
                    else
                        project.setMinitalk(false);
                    break;
                case SubjectProperties.PIPEX:
                    if (validated == null)
                        project.setPipex(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setPipex(true);
                    else
                        project.setPipex(false);
                    break;
                case SubjectProperties.SO_LONG:
                    if (validated == null)
                        project.setSo_long(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setSo_long(true);
                    else
                        project.setSo_long(false);
                    break;
                case SubjectProperties.FDF:
                    if (validated == null)
                        project.setFdf(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setFdf(true);
                    else
                        project.setFdf(false);
                    break;
                case SubjectProperties.FRACT_OL:
                    if (validated == null)
                        project.setFract_ol(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setFract_ol(true);
                    else
                        project.setFract_ol(false);
                    break;
                case SubjectProperties.PUSH_SWAP:
                    if (validated == null)
                        project.setPush_swap(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setPush_swap(true);
                    else
                        project.setPush_swap(false);
                    break;
                // 3 서클
                case SubjectProperties.MINISHELL:
                    if (validated == null)
                        project.setMinishell(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setMinishell(true);
                    else
                        project.setMinishell(false);
                    break;
                case SubjectProperties.PHILOSOPHERS:
                    if (validated == null)
                        project.setPhilosopher(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setPhilosopher(true);
                    else
                        project.setPhilosopher(false);
                    break;
                // 4 서클
                case SubjectProperties.NETPRACTICE:
                    if (validated == null)
                        project.setNetpractice(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setNetpractice(true);
                    else
                        project.setNetpractice(false);
                    break;
                case SubjectProperties.CUB3D:
                    if (validated == null)
                        project.setCub3d(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCub3d(true);
                    else
                        project.setCub3d(false);
                    break;
                case SubjectProperties.MINIRT:
                    if (validated == null)
                        project.setMinirt(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setMinirt(true);
                    else
                        project.setMinirt(false);
                    break;
                case SubjectProperties.CPP_MODULE_00:
                    if (validated == null)
                        project.setCpp00(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp00(true);
                    else
                        project.setCpp00(false);
                    break;
                case SubjectProperties.CPP_MODULE_01:
                    if (validated == null)
                        project.setCpp01(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp01(true);
                    else
                        project.setCpp01(false);
                    break;
                case SubjectProperties.CPP_MODULE_02:
                    if (validated == null)
                        project.setCpp02(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp02(true);
                    else
                        project.setCpp02(false);
                    break;
                case SubjectProperties.CPP_MODULE_03:
                    if (validated == null)
                        project.setCpp03(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp03(true);
                    else
                        project.setCpp03(false);
                    break;
                case SubjectProperties.CPP_MODULE_04:
                    if (validated == null)
                        project.setCpp04(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp04(true);
                    else
                        project.setCpp04(false);
                    break;
                // 5 서클
                case SubjectProperties.CPP_MODULE_05:
                    if (validated == null)
                        project.setCpp05(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp05(true);
                    else
                        project.setCpp05(false);
                    break;
                case SubjectProperties.CPP_MODULE_06:
                    if (validated == null)
                        project.setCpp06(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp06(true);
                    else
                        project.setCpp06(false);
                    break;
                case SubjectProperties.CPP_MODULE_07:
                    if (validated == null)
                        project.setCpp07(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp07(true);
                    else
                        project.setCpp07(false);
                    break;
                case SubjectProperties.CPP_MODULE_08:
                    if (validated == null)
                        project.setCpp08(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp08(true);
                    else
                        project.setCpp08(false);
                    break;
                case SubjectProperties.CPP_MODULE_09:
                    if (validated == null)
                        project.setCpp09(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setCpp09(true);
                    else
                        project.setCpp09(false);
                    break;
                case SubjectProperties.INCEPTION:
                    if (validated == null)
                        project.setInception(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setInception(true);
                    else
                        project.setInception(false);
                    break;
                case SubjectProperties.WEBSERV:
                    if (validated == null)
                        project.setWebserv(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setWebserv(true);
                    else
                        project.setWebserv(false);
                    break;
                case SubjectProperties.FT_IRC:
                    if (validated == null)
                        project.setFt_irc(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setFt_irc(true);
                    else
                        project.setFt_irc(false);
                    break;
                // 6 서클
                case SubjectProperties.FT_TRANSCENDENCE:
                    if (validated == null)
                        project.setFt_transcendence(false);
                    else if (validated.toString().equals(SubjectProperties.TRUE))
                        project.setFt_transcendence(true);
                    else
                        project.setFt_transcendence(false);
                    break;
                default:
                    break;
            }
        }

        return project;
    }
}
