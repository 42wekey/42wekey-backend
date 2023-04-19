package com.ftence.ftwekey.entity;

import com._42ence.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uniqueId;

    @Column(name = "intra_id")
    private String intraId;

    private double level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @UpdateTimestamp
    private LocalDateTime reloadTime;

    private String refreshToken;

    public String getRoleKey() {
        return this.role.getKey();
    }
}
