package cn.swust.indigo.admin.mapper;

import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.vo.SysDeptVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    @Select("select dept_id from sys_dept where leader_id = #{leaderId}")
    List<Integer> getDepIdByLeaderId(@Param("leaderId") Integer leaderId);

    Page<SysDeptVo> getDeptPageVoWithName(@Param("page") Page<SysDeptVo> page, @Param("leadName") String leadName, @Param("deptName")String deptName,@Param("departmentPy") String departmentPy);

    @Select("select department_name from sys_dept where dept_id = #{deptId}")
    String getDeptName(@Param("deptId") Integer deptId);

    Page<SysDeptVo> getDeptPageVo(@Param("page") Page<SysDeptVo> page);

    List<SysDeptVo> getDeptVo();

    IPage<SysDeptVo> selectSysDeptVoPage(IPage<SysDeptVo> page);

}
