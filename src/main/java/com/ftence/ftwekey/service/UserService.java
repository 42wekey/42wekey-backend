package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.response.UserInfoDTO;
import com.ftence.ftwekey.dto.response.UserMeInfoDTO;
import com.ftence.ftwekey.entity.Project;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.repository.CommentRepository;
import com.ftence.ftwekey.repository.HeartRepository;
import com.ftence.ftwekey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    public UserMeInfoDTO getMyInfo(PrincipalDetails user) {

        return UserMeInfoDTO.builder()
                .intraId(user.getUsername())
                .level(user.getLevel())
                .build();
    }

    public UserInfoDTO getUserInfo(String intraId) {

        // todo 예외 처리
        User user = userRepository.findByIntraId(intraId);

        int cntComment = commentRepository.getUserCommentCnt(user.getId());
        int cntLikes = heartRepository.getUserLikesCnt(user.getId());

        return UserInfoDTO.builder()
                .userLevel(user.getLevel())
                .cntComment(cntComment)
                .cntLikes(cntLikes)
                .build();
    }

    public Project getUserProjectInfo(User user, Map<String, Object> userInfo) {

        Project project = new Project();
        project.setUser(user);

        ArrayList<LinkedHashMap> projectsUsers = (ArrayList<LinkedHashMap>) userInfo.get("projects_users");

        for (LinkedHashMap o : projectsUsers) {

            LinkedHashMap p = (LinkedHashMap) o.get("project");

            Object validated = o.get("validated?");

            switch (p.get("name").toString()) {
                // 0 서클
                case "Libft":
                    if (validated == null)
                        project.setLibft(false);
                    else if (validated.toString().equals("true"))
                        project.setLibft(true);
                    else
                        project.setLibft(false);
                    break;
                // 1서클
                case "ft_printf":
                    if (validated == null)
                        project.setFt_printf(false);
                    else if (validated.toString().equals("true"))
                        project.setFt_printf(true);
                    else
                        project.setFt_printf(false);
                    break;
                case "get_next_line":
                    if (validated == null)
                        project.setGet_next_line(false);
                    else if (validated.toString().equals("true"))
                        project.setGet_next_line(true);
                    else
                        project.setGet_next_line(false);
                    break;
                case "Born2beroot":
                    if (validated == null)
                        project.setBorn2beroot(false);
                    else if (validated.toString().equals("true"))
                        project.setBorn2beroot(true);
                    else
                        project.setBorn2beroot(false);
                // 2서클
                    break;
                case "minitalk":
                    if (validated == null)
                        project.setMinitalk(false);
                    else if (validated.toString().equals("true"))
                        project.setMinitalk(true);
                    else
                        project.setMinitalk(false);
                    break;
                case "pipex":
                    if (validated == null)
                        project.setPipex(false);
                    else if (validated.toString().equals("true"))
                        project.setPipex(true);
                    else
                        project.setPipex(false);
                    break;
                case "so_long":
                    if (validated == null)
                        project.setSo_long(false);
                    else if (validated.toString().equals("true"))
                        project.setSo_long(true);
                    else
                        project.setSo_long(false);
                    break;
                case "FdF":
                    if (validated == null)
                        project.setFdf(false);
                    else if (validated.toString().equals("true"))
                        project.setFdf(true);
                    else
                        project.setFdf(false);
                    break;
                case "fract-ol":
                    if (validated == null)
                        project.setFract_ol(false);
                    else if (validated.toString().equals("true"))
                        project.setFract_ol(true);
                    else
                        project.setFract_ol(false);
                    break;
                case "push_swap":
                    if (validated == null)
                        project.setPush_swap(false);
                    else if (validated.toString().equals("true"))
                        project.setPush_swap(true);
                    else
                        project.setPush_swap(false);
                    break;
                // 3 서클
                case "minishell":
                    if (validated == null)
                        project.setMinishell(false);
                    else if (validated.toString().equals("true"))
                        project.setMinishell(true);
                    else
                        project.setMinishell(false);
                    break;
                case "Philosophers":
                    if (validated == null)
                        project.setPhilosopher(false);
                    else if (validated.toString().equals("true"))
                        project.setPhilosopher(true);
                    else
                        project.setPhilosopher(false);
                    break;
                // 4 서클
                case "NetPractice":
                    if (validated == null)
                        project.setNetpractice(false);
                    else if (validated.toString().equals("true"))
                        project.setNetpractice(true);
                    else
                        project.setNetpractice(false);
                    break;
                case "cub3d":
                    if (validated == null)
                        project.setCub3d(false);
                    else if (validated.toString().equals("true"))
                        project.setCub3d(true);
                    else
                        project.setCub3d(false);
                    break;
                case "miniRT":
                    if (validated == null)
                        project.setMinirt(false);
                    else if (validated.toString().equals("true"))
                        project.setMinirt(true);
                    else
                        project.setMinirt(false);
                    break;
                case "CPP Module 00":
                    if (validated == null)
                        project.setCpp00(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp00(true);
                    else
                        project.setCpp00(false);
                    break;
                case "CPP Module 01":
                    if (validated == null)
                        project.setCpp01(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp01(true);
                    else
                        project.setCpp01(false);
                    break;
                case "CPP Module 02":
                    if (validated == null)
                        project.setCpp02(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp02(true);
                    else
                        project.setCpp02(false);
                    break;
                case "CPP Module 03":
                    if (validated == null)
                        project.setCpp03(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp03(true);
                    else
                        project.setCpp03(false);
                    break;
                case "CPP Module 04":
                    if (validated == null)
                        project.setCpp04(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp04(true);
                    else
                        project.setCpp04(false);
                    break;
                // 5 서클
                case "CPP Module 05":
                    if (validated == null)
                        project.setCpp05(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp05(true);
                    else
                        project.setCpp05(false);
                    break;
                case "CPP Module 06":
                    if (validated == null)
                        project.setCpp06(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp06(true);
                    else
                        project.setCpp06(false);
                    break;
                case "CPP Module 07":
                    if (validated == null)
                        project.setCpp07(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp07(true);
                    else
                        project.setCpp07(false);
                    break;
                case "CPP Module 08":
                    if (validated == null)
                        project.setCpp08(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp08(true);
                    else
                        project.setCpp08(false);
                    break;
                case "CPP Module 09":
                    if (validated == null)
                        project.setCpp09(false);
                    else if (validated.toString().equals("true"))
                        project.setCpp09(true);
                    else
                        project.setCpp09(false);
                    break;
                case "Inception":
                    if (validated == null)
                        project.setInception(false);
                    else if (validated.toString().equals("true"))
                        project.setInception(true);
                    else
                        project.setInception(false);
                    break;
                case "webserv":
                    if (validated == null)
                        project.setWebserv(false);
                    else if (validated.toString().equals("true"))
                        project.setWebserv(true);
                    else
                        project.setWebserv(false);
                    break;
                case "ft_irc":
                    if (validated == null)
                        project.setFt_irc(false);
                    else if (validated.toString().equals("true"))
                        project.setFt_irc(true);
                    else
                        project.setFt_irc(false);
                    break;
                // 6 서클
                case "ft_transcendence":
                    if (validated == null)
                        project.setFt_transcendence(false);
                    else if (validated.toString().equals("true"))
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
