package cn.swust.indigo.admin.service.impl;

import cn.swust.indigo.admin.AdminApplicationTest;
import cn.swust.indigo.admin.entity.po.SysLog;
import cn.swust.indigo.admin.service.SysLogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AdminApplicationTest.class)
class SysLogServiceImplTest {
    @Autowired
    private SysLogService service;

    @Test
    public void selectByIdTest() {
        SysLog log = service.getById(5007);
        Assertions.assertNotNull(log);
    }

}