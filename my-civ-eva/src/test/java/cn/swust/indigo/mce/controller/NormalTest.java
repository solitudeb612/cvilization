package cn.swust.indigo.mce.controller;

import cn.swust.indigo.mce.entity.vo.DataVo;
import cn.swust.indigo.mce.entity.vo.GuidePerCount;
import cn.swust.indigo.mce.entity.vo.RuleDeptTaskVo;
import cn.swust.indigo.mce.mapper.GuideMapper;
import cn.swust.indigo.mce.mapper.RuleDeptTaskMapper;
import cn.swust.indigo.mce.service.CommitService;
import cn.swust.indigo.mce.service.DataOverviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Binary cat
 * @date 2023/4/13 16:04
 */
@SpringBootTest
public class NormalTest {

    @Autowired
    CommitService commitService;

    @Autowired
    GuideMapper guideMapper;

    @Autowired
    RuleDeptTaskMapper ruleDeptTaskMapper;

    @Autowired
    DataOverviewService dataOverviewService;

    @Test
    void test1(){
        Integer integer = commitService.saveImagesIdsByReportId(4);
        System.out.println("-----save:" + integer);
    }

    @Test
    void test2(){
        List<GuidePerCount> guidePerCounts = guideMapper.monthTotalCount();
        System.out.println(guidePerCounts);
    }

    @Test
    void test4(){
//        List<RuleDeptTaskVo> ruleDeptTaskVos = ruleDeptTaskMapper.listRuleDeptTaskVoByUser(7837, null);
        List<RuleDeptTaskVo> ruleDeptTaskVos = ruleDeptTaskMapper.listRuleDeptTaskVoByUserAll(7837, 569);

        System.out.println(ruleDeptTaskVos);
    }

    @Test
    void test5(){
        DataVo dataVo = dataOverviewService.getDataVo();
        System.out.println(dataVo);
    }
}
