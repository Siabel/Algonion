package com.e1i5.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user_file_info")
public class ProfileFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", updatable = false)
    private int userFileInfoId; //TODO profileFileInfoId로 변경
    private String originalFile;
    private String saveFile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ProfileFileInfo(String originalFile, String saveFile, User user) {
        this.originalFile = originalFile;
        this.saveFile = saveFile;
        this.user = user;
    }
}