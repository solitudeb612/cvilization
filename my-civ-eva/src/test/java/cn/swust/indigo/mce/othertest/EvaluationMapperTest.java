package cn.swust.indigo.mce.othertest;

import cn.swust.indigo.mce.entity.po.ContextSite;
import cn.swust.indigo.mce.entity.vo.CommitRuleTableInfo;
import cn.swust.indigo.mce.entity.vo.QCommitDetail;
import cn.swust.indigo.mce.mapper.EvaluationRulesMapper;
import cn.swust.indigo.mce.mapper.SitePageMapper;
import cn.swust.indigo.mce.service.CommitService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Binary cat
 * @date 2023/4/7 11:22
 */
@SpringBootTest
public class EvaluationMapperTest {

    @Autowired
    EvaluationRulesMapper evaluationMapper;

    @Autowired
    CommitService commitService;

    @Test
    public void test(){
        List<Integer> integers = new ArrayList<>();
        integers.add(6226);
        List<CommitRuleTableInfo> allRuleInfo = evaluationMapper.getAllRuleInfo(integers);
        System.out.println(allRuleInfo);
    }



    @Autowired
    SitePageMapper sitePageMapper;

    @Test
    void normal(){
        List<ContextSite> contextSite = sitePageMapper.getContextSite();
        System.out.println(contextSite);
    }
}
