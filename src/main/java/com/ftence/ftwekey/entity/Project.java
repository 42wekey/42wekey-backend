package com.ftence.ftwekey.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 0 서클
    private Boolean libft;

    // 1 서클
    private Boolean ft_printf;
    private Boolean get_next_line;
    private Boolean born2beroot;

    // 2 서클
    private Boolean minitalk;
    private Boolean pipex;
    private Boolean so_long;
    private Boolean fdf;
    private Boolean fract_ol;
    private Boolean push_swap;

    // 3서클
    private Boolean minishell;
    private Boolean philosopher;

    // 4 서클
    private Boolean netpractice;
    private Boolean cub3d;
    private Boolean minirt;
    private Boolean cpp00;
    private Boolean cpp01;
    private Boolean cpp02;
    private Boolean cpp03;
    private Boolean cpp04;

    // 5 서클
    private Boolean cpp05;
    private Boolean cpp06;
    private Boolean cpp07;
    private Boolean cpp08;
    private Boolean cpp09;
    private Boolean inception;
    private Boolean webserv;
    private Boolean ft_irc;

    // 6 서클
    private Boolean ft_transcendence;

}
