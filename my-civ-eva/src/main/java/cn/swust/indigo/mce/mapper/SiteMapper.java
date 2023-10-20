package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.Site;
import cn.swust.indigo.mce.entity.vo.SiteVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 站点 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 17:01:59
 */
@Mapper
public interface SiteMapper extends BaseMapper<Site> {

    IPage<SiteVo> getSiteVoList(@Param("page") Page<SiteVo> page, @Param("longitude") String longitude, @Param("latitude") String latitude, @Param("sitePy") String sitePy, @Param("leaderName") String leaderName);

    SiteVo getSiteVoById(@Param("siteId") Integer siteId);

    List<SiteVo> getAll();

    SiteVo getSiteVoByLeaderId(@Param("leaderId") Integer leaderId);

    @Select("select site_id from site where department_id in (select dept_id from sys_dept where leader_id = #{leaderId}) and (leader_id is null or trim(leader_id)='')")
    List<Site> checkSiteList(Integer leaderId);
}
