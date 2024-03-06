package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.type.MemberType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    /**
     * @NotBlank: null, "", " " 모두 허용하지 않음
     * @NotNull: 단순히 null만 허용하지 않음
     */

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotNull
    private MemberType memberType;

    /**
     * user로 부터 회원가입 정보 받아서 db에 저장하기 위해 entity로 변환하는 함수
     * @return Member entity
     */
    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .userName(this.username)
                .password(this.password)
                .phone(this.phone)
                .memberType(this.memberType)
                .build();
    }

}
