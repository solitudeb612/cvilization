package cn.swust.indigo.mce.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.SitePage;
import cn.swust.indigo.mce.service.SitePageService;
import cn.swust.indigo.mce.entity.vo.QSitePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 站点主页
 *
 * @author lhz
 * @date 2023-02-24 17:02:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sitepage" )
@Api(value = "sitepage", tags = "站点主页管理")
public class SitePageController {

    private final  SitePageService sitePageService;

    /**
     * 通过id查询站点主页
     * @param siteId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get" )
    public R getById(@RequestParam("siteId" ) Integer siteId) {
        return R.ok(sitePageService.getById(siteId));
    }

    /**
     * 新增站点主页
     * @param sitePage 站点主页
     * @return R
     */
    @ApiOperation(value = "新增站点主页", notes = "新增站点主页")
    @Log("新增站点主页" )
    @PostMapping
//    @PreAuthorize("hasAuthority('sitepage_add')" )
    public R save(@RequestBody SitePage sitePage) {
        return R.ok(sitePageService.saveOrUpdate(sitePage));
    }


    @ApiOperation(value = "获取站点信息包括site_page", notes = "获取站点信息包括site_page")
    @GetMapping
    public R getSitePage() {
        return R.ok(sitePageService.getContextSite());
    }

}
