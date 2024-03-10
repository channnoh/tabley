package com.zerobase.tabley.domain;

import com.zerobase.tabley.type.MemberType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String email;
    private String userName;
    private String password;
    private String phone;

    /**
     * 자바 enum 타입 사용하려면 @Enumerated 어노테이션 붙여야함
     * EnumType.ORDINAL을 사용하면 데이터 크기는 작아지지만 순서 변동 되면 문제 커짐 => EnumType.STRING 사용!!
     */

    @Enumerated(EnumType.STRING)
    private MemberType memberType;


    /**
     * reservation entity와 일대다 관계로 매핑
     * mappedby에는 join entity에서 @ManytoOne 어노테이션 붙은 변수명
     */

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<>();


    /**
     * GrantedAuthority 인터페이스는 사용자에게 부여된 권한을 나타낸다. 일반적으로 역할(ex. "ROLE_USER", "ROLE_ADMIN" )로 표현된다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memberType.toString()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
