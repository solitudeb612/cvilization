package cn.swust.indigo.admin.mapper;

import cn.swust.indigo.admin.entity.po.NewsApprove;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.NotNull;

/**
 * 新闻点赞数据 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 17:00:04
 */
@Mapper
public interface NewsApproveMapper extends BaseMapper<NewsApprove> {

}
