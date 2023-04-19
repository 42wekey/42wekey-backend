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


    @PrePersist
    @PreUpdate
    private void initialize() {

        if (libft == null)
            libft = false;

        if (ft_printf == null)
            ft_printf = false;
        if (get_next_line == null)
            get_next_line = false;
        if (born2beroot == null)
            born2beroot = false;

        if (minitalk == null)
            minitalk = false;
        if (pipex == null)
            pipex = false;
        if (so_long == null)
            so_long = false;
        if (fdf == null)
            fdf = false;
        if (fract_ol == null)
            fract_ol = false;
        if (push_swap == null)
            push_swap = false;

        if (minishell == null)
            minishell = false;
        if (philosopher == null)
            philosopher = false;

        if (netpractice == null)
            netpractice = false;
        if (cub3d == null)
            cub3d = false;
        if (minirt == null)
            minirt = false;
        if (cpp00 == null)
            cpp00 = false;
        if (cpp01 == null)
            cpp01 = false;
        if (cpp02 == null)
            cpp02 = false;
        if (cpp03 == null)
            cpp03 = false;
        if (cpp04 == null)
            cpp04 = false;

        if (cpp05 == null)
            cpp05 = false;
        if (cpp06 == null)
            cpp06 = false;
        if (cpp07 == null)
            cpp07 = false;
        if (cpp08 == null)
            cpp08 = false;
        if (cpp09 == null)
            cpp09 = false;
        if (inception == null)
            inception = false;
        if (webserv == null)
            webserv = false;
        if (ft_irc == null)
            ft_irc = false;


        if (ft_transcendence == null)
            ft_transcendence = false;
    }
}
