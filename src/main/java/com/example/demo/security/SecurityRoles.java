package com.example.demo.security;

import java.util.Arrays;
import java.util.List;

public class SecurityRoles {

    public static final String ROLE_1 = "SOME_ROLE";
    public static final String ROLE_2 = "SOME_OTHER_ROLE";

    static final List<String> ALL_ROLES = Arrays.asList(ROLE_1, ROLE_2);

    private SecurityRoles() {

    }
}
