package cn.swust.indigo.mce.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.swust.indigo.mce.entity.po.SelfCheck;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 自查通知 Mapper 接口
 *
 * @author lhz
 * @date 2023-03-18 10:14:00
 */
@Mapper
public interface SelfCheckMapper extends BaseMapper<SelfCheck> {
    @Update("update self_check set status = #{status} where check_id = #{checkId} ")
    boolean updateStatus(@Param("checkId") Integer checkId, @Param("status") Integer status);
}
