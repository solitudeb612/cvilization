package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.CheckGroup;
import cn.swust.indigo.mce.entity.po.DeptDetail;
import cn.swust.indigo.mce.entity.vo.CheckReportVo;
import cn.swust.indigo.mce.entity.vo.SiteDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.swust.indigo.mce.entity.po.CheckGroup
 */
@Mapper
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {

    List<SiteDetail> selectSiteDetail(@Param("checkPlanId") Integer checkPlanId,
                                      @Param("departmentId") Integer departmentId);

    List<CheckReportVo> findSiteCountAndLastTime(@Param("checkId") Integer checkPlanId,
                                             @Param("departmentId") Integer departmentId);

    List<DeptDetail> selectDeptDetail(@Param("checkPlanId") Integer checkPlanId,
                                      @Param("departmentId") Integer departmentId);

    List<CheckReportVo> findDeptCountAndLastTime(@Param("checkId") Integer checkId,
                                                 @Param("departmentId") Integer departmentId);
}




