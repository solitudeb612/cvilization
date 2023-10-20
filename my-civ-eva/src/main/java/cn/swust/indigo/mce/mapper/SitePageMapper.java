package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.ContextSite;
import cn.swust.indigo.mce.entity.po.SitePage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 站点主页 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 17:02:13
 */
@Mapper
public interface SitePageMapper extends BaseMapper<SitePage> {

    List<ContextSite> getContextSite();

    ContextSite getById(Integer siteId);

}
