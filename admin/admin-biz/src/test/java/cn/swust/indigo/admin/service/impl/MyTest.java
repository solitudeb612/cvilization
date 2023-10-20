package cn.swust.indigo.admin.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyTest {
    public static void main(String[] args) {
        PasswordEncoder ENCODER = new BCryptPasswordEncoder();
        String psw = ENCODER.encode("123456");
        System.out.println(psw);
    }
}
