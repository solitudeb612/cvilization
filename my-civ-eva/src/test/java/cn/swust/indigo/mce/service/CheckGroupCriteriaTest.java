package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.vo.RuleDeptTaskVo;
import cn.swust.indigo.mce.mapper.CheckGroupCriteriaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CheckGroupCriteriaTest {
    @Autowired
    CheckGroupCriteriaMapper checkGroupCriteriaMapper;

    @Test
    void test(){
        checkGroupCriteriaMapper.list(1);
    }
}
