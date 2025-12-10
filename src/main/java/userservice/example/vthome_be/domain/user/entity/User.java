package userservice.example.vthome_be.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import userservice.example.vthome_be.domain.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users", uniqueConstraints = {
        // 유니크 제약 조건은 테이블에 걸기( 단일 컬럼이어도 컨벤션 맞춤 )
        @UniqueConstraint(name = "uq_users_email_normalized", columnNames = {"email_normalized"}),
        @UniqueConstraint(name = "uq_users_user_name_normalized", columnNames = {"user_name_normalized"})
})
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    // 기본 키 전략 db에 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다른 유저 멘션이나 태그 등에 사용
    @Column(name="user_name", length = 30)
    private String userName;

    @Column(name="user_name_normalized", length = 30)
    private String userNameNormalized;

    @Column(nullable=false)
    private String email;

    @Column(name="email_normalized", nullable=false)
    private String emailNormalized;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    // 인증 시간은 NULL 가능
    private LocalDateTime verifiedAt;

    // Status
    @Enumerated(EnumType.STRING)
    @Column(name="user_status", nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE; // ACTIVE, INACTIVE, DELETED

    // LOCAL 계정 비밀번호 (BCrypt 해시), 소셜 계정일 경우 null 허용
    @Column(name = "password", length = 100)
    private String password;

    // 인증 제공자 (LOCAL, GOOGLE, KAKAO 등)
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 50)
    private AuthProvider provider;

    // 소셜 프로바이더에서 내려주는 식별자 (LOCAL 계정은 null)
    @Column(name = "provider_id")
    private String providerId;
}
