package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.common.core.constants.CommonConstants;
import cn.swust.indigo.common.minio.service.MinioTemplate;
import cn.swust.indigo.mce.entity.po.ContextSite;
import cn.swust.indigo.mce.mapper.SiteMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.SitePage;
import cn.swust.indigo.mce.mapper.SitePageMapper;
import cn.swust.indigo.mce.service.SitePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 站点主页
 *
 * @author lhz
 * @date 2023-02-24 17:02:13
 */
@Service
@AllArgsConstructor
public class SitePageServiceImpl extends ServiceImpl<SitePageMapper, SitePage> implements SitePageService {

    @Autowired
    SitePageMapper sitePageMapper;

    MinioTemplate minioTemplate;

    @Override
    public List<ContextSite> getContextSite() {
        return sitePageMapper.getContextSite();
    }

    @Override
    public ContextSite getById(Integer siteId) {
        ContextSite contextSite = sitePageMapper.getById(siteId);
        contextSite.setPicUrl(minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, contextSite.getThumbnail(), 7200));
        return contextSite;
    }
}
