package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.ContextSite;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.SitePage;
import java.util.List;

/**
 * 站点主页
 *
 * @author lhz
 * @date 2023-02-24 17:02:13
 */
public interface SitePageService extends IService<SitePage> {

    List<ContextSite> getContextSite();

    ContextSite getById(Integer siteId);
}
