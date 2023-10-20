package cn.swust.indigo.admin.service.impl;

import cn.swust.indigo.admin.entity.po.NewsApprove;
import cn.swust.indigo.admin.mapper.NewsApproveMapper;
import cn.swust.indigo.admin.mapper.NewsMapper;
import cn.swust.indigo.admin.service.NewsApproveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 新闻点赞数据
 *
 * @author lhz
 * @date 2023-02-24 17:00:04
 */
@Service
@AllArgsConstructor
public class NewsApproveServiceImpl extends ServiceImpl<NewsApproveMapper, NewsApprove> implements NewsApproveService {
    private final NewsMapper newsMapper;
    @Override
    public boolean saveOrUpdate(NewsApprove entity) {
        int count = 1;
        if (!entity.getPraise()) {
            count = -1;
        }
        newsMapper.addApproveNum(entity.getNewsId(), count);
        return super.saveOrUpdate(entity);
    }
}
