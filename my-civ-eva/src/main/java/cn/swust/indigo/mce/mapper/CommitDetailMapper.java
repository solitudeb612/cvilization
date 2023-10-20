package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.CommitDetail;
import cn.swust.indigo.mce.entity.vo.CommitRuleTableInfo;
import cn.swust.indigo.mce.entity.vo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 项目提交内容 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
@Mapper
public interface CommitDetailMapper extends BaseMapper<CommitDetail> {
    @Deprecated
    List<Integer> getReportIds(@Param("guideId") Integer guideId, @Param("ruleId") Integer ruleId, @Param("departmentId") Integer departmentId);

    List<Department> selectLeadDept(Integer guideId);
}
