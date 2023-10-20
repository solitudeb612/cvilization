package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.CommitReport;
import cn.swust.indigo.mce.entity.po.CommitReportVo1;
import cn.swust.indigo.mce.entity.po.DataTimeAndCount;
import cn.swust.indigo.mce.entity.vo.CommitRuleTableInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Binary cat
 * @date 2023/3/23 14:31
 */
@Mapper
public interface CommitReportMapper extends BaseMapper<CommitReport> {
    List<CommitRuleTableInfo> getCommitReportCount(@Param("userId") Integer userId, @Param("guideId") Integer guideId);

    List<CommitReportVo1> findCount(@Param("guideId") Integer guideId, @Param("departmentId") Integer departmentId);

    Integer getTotalReportCount();

    List<DataTimeAndCount> getYearTimeAndCount();
}
