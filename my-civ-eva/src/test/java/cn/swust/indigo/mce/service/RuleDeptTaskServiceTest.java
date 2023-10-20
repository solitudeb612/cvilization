package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.vo.RuleDeptTaskVo;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Binary cat
 * @date 2023/4/16 1:39
 */
@SpringBootTest
class RuleDeptTaskServiceTest {

    @Autowired
    RuleDeptTaskService ruleDeptTaskService;

    @Test
    void test(){
        List<RuleDeptTaskVo> ruleDeptTaskVos = ruleDeptTaskService.listRuleDeptTaskVo(7837, 7845, 1);
        System.out.println(ruleDeptTaskVos);
    }
}