package cn.swust.indigo.admin.mapper;

import cn.swust.indigo.admin.entity.po.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 新闻 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 18:02:42
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

    @Update("update sys_news set approve_num = approve_num + #{praise} where news_id = #{newsId}")
    int addApproveNum(@Param("newsId")Integer newsId, @Param("praise")Integer praise);

    @Update("update sys_news set read_num = #{readNum}+1 where news_id = #{newsId}")
    int addReadNum(@Param("newsId")Integer newsId, @Param("readNum")Integer readNum);
}
