package cn.swust.indigo.admin.controller;

import cn.swust.indigo.admin.entity.po.NewsApprove;
import cn.swust.indigo.admin.entity.po.NewsContext;
import cn.swust.indigo.admin.service.NewsApproveService;
import cn.swust.indigo.admin.service.NewsContextService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.admin.entity.po.News;
import cn.swust.indigo.admin.entity.vo.QNews;
import cn.swust.indigo.admin.service.NewsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 新闻
 *
 * @author lhz
 * @date 2023-02-24 18:02:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/news" )
@Api(value = "news", tags = "新闻管理")
public class SysNewsController {

    private final NewsService newsService;
    private final NewsContextService newsContextService;

    //发布和撤销发布
    /**
     * 发布和撤销发布新闻
     * @param newsId 新闻
     * @return R
     */
    @ApiOperation(value = "新闻的发布和撤销发布", notes = "新闻的发布和撤销发布")
    @Log("新闻的发布和撤销发布" )
    @PutMapping("/pubished")
//    @PreAuthorize("hasAuthority('news_edit')" )
    public R PublishedOrCancel(@RequestParam("newsId") Integer newsId) {
        News myNews = newsService.getById(newsId);
        if (myNews.getStatus() == 1){
            myNews.setStatus(0);
        }else{
            myNews.setStatus(1);
            myNews.setPublishTime(LocalDateTime.now());
        }
        return R.ok(newsService.updateById(myNews));
    }

    /**
     * 分页查询
     * @param page 分页对象
     * @param qNews 新闻
     * @return 普通用户分页查询新闻列表
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/publish/page" )
    public R getPublishNewsPage(Page<News> page, QNews qNews) {
        qNews.setStatus(1);
        return R.ok(newsService.page(page, CreateQuery.getQueryWrapper(qNews)));
    }

    /**
     * 分页查询
     * @param page 分页对象
     * @param news 新闻
     * @return 新闻列表
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNewsPage(Page<News> page, QNews news) {
        return R.ok(newsService.page(page, CreateQuery.getQueryWrapper(news)));
    }

    /**
     * 通过id查询新闻
     * @param newsId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get" )
    public R getById(@RequestParam("newsId" ) Integer newsId) {
        return R.ok(newsService.getById(newsId));
    }

    /**
     * 新增新闻
     * @param news 新闻
     * @return R
     */
    @ApiOperation(value = "新增新闻", notes = "新增新闻")
    @Log("新增新闻" )
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('news_add')" )
    public R save(@RequestBody News news) {
        return R.ok(newsService.save(news));
    }

    /**
     * 修改新闻
     * @param news 新闻
     * @return R
     */
    @ApiOperation(value = "修改新闻", notes = "修改新闻")
    @Log("修改新闻" )
    @PutMapping("/edit")
//    @PreAuthorize("hasAuthority('news_edit')" )
    public R updateById(@RequestBody News news) {
        News myNews = newsService.getById(news.getNewsId());
        if (myNews == null || myNews.getStatus() == 1){
            return R.failed("新闻不存在或新闻已经发布");
        }
        return R.ok(newsService.updateById(news));
    }


    /**
     * 新增新闻详情
     * @param newsContext 新闻详情
     * @return R
     */
    @ApiOperation(value = "新增新闻详情", notes = "新增新闻详情")
    @Log("新增新闻详情" )
    @PutMapping("/detail")
//    @PreAuthorize("hasAuthority('newscontext_add')" )
    public R detailEdit(@RequestBody NewsContext newsContext) {
        return R.ok(newsContextService.saveOrUpdate(newsContext));
    }



    /**
     * 通过id删除新闻
     * @param newsId 新闻id
     * @return R
     */
    @ApiOperation(value = "通过id删除新闻", notes = "通过id删除新闻")
    @Log("通过id删除新闻" )
    @DeleteMapping("/{newsId}" )
//    @PreAuthorize("hasAuthority('news_del')" )
    public R removeById(@RequestParam("newsId") Integer newsId) {
        News myNews = newsService.getById(newsId);
        if (myNews == null || myNews.getStatus() == 1){
            return R.failed("新闻不存在或新闻已经发布");
        }
        return R.ok(newsService.removeById(newsId));
    }

    private final NewsApproveService newsApproveService;

    /**
     * 设置新闻点赞数据
     * @param newsApprove 新闻点赞数据
     * @return R
     */
    @ApiOperation(value = "新增新闻点赞数据", notes = "新增新闻点赞数据")
//    @Log("新增新闻点赞数据" )
    @PostMapping("/approve")
//    @PreAuthorize("hasAuthority('newsapprove_add')" )
    public R saveApprove(@RequestBody NewsApprove newsApprove) {
        return R.ok(newsApproveService.saveOrUpdate(newsApprove));
    }


}
