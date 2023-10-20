package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.mce.entity.po.SitePage;
import cn.swust.indigo.mce.entity.vo.Department;
import cn.swust.indigo.mce.entity.vo.Site;
import cn.swust.indigo.mce.entity.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserVo> {
    int insertUser(UserVo userVo);

    int insertSysUserRole(UserVo userVo);

    List<SysUser> findMyUserAll(@Param("leaderId") int leaderId,@Param("status") int status);

    void updateByUserIdsAndStatus(@Param("userIds") List<Integer> userIds,@Param("status") int status);

    List<Site> getSiteIdAndName(@Param("leaderId") Integer leaderId);

    List<Department> getDepartmentIdAndName(@Param("leaderId") Integer leaderId);
}
