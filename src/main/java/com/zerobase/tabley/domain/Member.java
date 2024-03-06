package com.zerobase.tabley.domain;

import com.zerobase.tabley.type.MemberType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.Collections;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String email;

    private String userName;
    private String password;
    private String phone;

    /**
     * EnumType.ORDINAL을 사용하면 데이터 크기는 작아지지만 순서 변동 되면 문제 커짐 => EnumType.STRING 사용!!
     */
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    /**
     * GrantedAuthority 인터페이스는 사용자에게 부여된 권한을 나타낸다. 일반적으로 역할(ex. "ROLE_USER", "ROLE_ADMIN" )로 표현된다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memberType.toString()));
    }

    @Override
    public String getUsername() {
        return email;
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
