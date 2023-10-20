package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.entity.vo.GuidePerCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 指南 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 16:57:45
 */
@Mapper
public interface GuideMapper extends BaseMapper<Guide> {

    @Update("update guide set read_num = #{readNum}+1 where news_id = #{newsId}")
    int addReadNum(Integer id, Integer readNum);

    List<GuidePerCount> monthTotalCount();

    List<GuidePerCount> monthPublishCount();

    List<GuidePerCount> yearTotalCount();

    List<GuidePerCount> yearPublishCount();

    @Update("update guide set status = #{status} where guide_id = #{guideId} ")
    boolean updateStatus(@Param("guideId") Integer guideId, @Param("status") Integer status);

    @Select("SELECT * FROM guide g where g.status = 1 order by g.guide_id desc limit 1")
    Guide getLastUsefulGuide();
}
