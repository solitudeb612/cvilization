package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupCriteriaMapper extends BaseMapper<CheckGroupCriteria> {
    List<CheckGroupCriteria> list(@Param("group_id") int group_id);

}
