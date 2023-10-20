package cn.swust.indigo.admin.service.impl;

import cn.swust.indigo.admin.entity.po.NewsContext;
import cn.swust.indigo.admin.mapper.NewsContextMapper;
import cn.swust.indigo.admin.service.NewsContextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 新闻详情
 *
 * @author lhz
 * @date 2023-02-24 18:02:57
 */
@Service
@AllArgsConstructor
public class NewsContextServiceImpl extends ServiceImpl<NewsContextMapper, NewsContext> implements NewsContextService {

}
