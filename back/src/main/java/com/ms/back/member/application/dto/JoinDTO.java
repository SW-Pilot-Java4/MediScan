package com.ms.back.member.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinDTO {
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
}