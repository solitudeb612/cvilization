package cn.swust.indigo.admin.mapper;

import cn.swust.indigo.admin.AdminApplicationTest;
import cn.swust.indigo.admin.entity.po.SysLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = AdminApplicationTest.class)
class SysLogMapperTest {
    @Autowired
    private SysLogMapper mapper;

    @Test
    public void testSelectById() {
        SysLog log = mapper.selectById(5007);
        Assertions.assertNotNull(log);
    }
}