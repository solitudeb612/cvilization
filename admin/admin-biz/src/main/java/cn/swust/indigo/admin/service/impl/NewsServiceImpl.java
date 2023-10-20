package cn.swust.indigo.admin.service.impl;

import cn.swust.indigo.admin.entity.po.News;
import cn.swust.indigo.admin.entity.po.NewsApprove;
import cn.swust.indigo.admin.entity.po.NewsContext;
import cn.swust.indigo.admin.mapper.NewsApproveMapper;
import cn.swust.indigo.admin.mapper.NewsContextMapper;
import cn.swust.indigo.admin.mapper.NewsMapper;
import cn.swust.indigo.admin.service.NewsService;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 新闻
 *
 * @author lhz
 * @date 2023-02-24 18:02:42
 */
@Service
@AllArgsConstructor
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
   private final NewsContextMapper newsContextMapper;
   private final NewsMapper newsMapper;
   private final NewsApproveMapper approveMapper;

    @Override
    public News getById(Serializable id) {
        News news = super.getById(id);
        NewsContext context = newsContextMapper.selectById(id);
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        NewsApprove newsApprove = approveMapper.selectOne(new QueryWrapper<NewsApprove>().eq("news_id", id).eq("user_id", userId));
        if (newsApprove != null){
            news.setPraise(newsApprove.getPraise());
        }
        if (context != null) {
            news.setContext(context.getContext());
        }
//        news.setReadNum(news.getReadNum()+1);
        newsMapper.addReadNum((Integer) id,news.getReadNum());
        return news;
    }

    @Override
    public boolean removeById(Serializable id) {
        newsContextMapper.deleteById(id);
        approveMapper.delete(new QueryWrapper<NewsApprove>().eq("news_id", id));
        return super.removeById(id);
    }
}
