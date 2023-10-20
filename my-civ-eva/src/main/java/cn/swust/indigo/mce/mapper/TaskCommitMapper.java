package cn.swust.indigo.mce.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.swust.indigo.mce.entity.po.TaskCommit;

/**
 * 在线申报提交要求 Mapper 接口
 *
 * @author lhz
 * @date 2023-03-23 10:37:51
 */
@Mapper
public interface TaskCommitMapper extends BaseMapper<TaskCommit> {

}
