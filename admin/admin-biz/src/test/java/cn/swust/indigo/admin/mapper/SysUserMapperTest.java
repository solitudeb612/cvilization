package cn.swust.indigo.admin.mapper;

import cn.swust.indigo.admin.AdminApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(classes = AdminApplicationTest.class)
class SysUserMapperTest {
    @Autowired
    private SysUserMapper baseMapper;
    private PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Test
    public void changePsw() {
        String psw = ENCODER.encode("123456");
        System.out.println(psw);
        baseMapper.changePassword(1, psw) ;
    }
}