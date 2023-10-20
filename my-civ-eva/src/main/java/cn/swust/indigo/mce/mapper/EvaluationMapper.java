package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.Evaluation;
import cn.swust.indigo.mce.entity.vo.EvaluationUseCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface EvaluationMapper extends BaseMapper<Evaluation> {
    List<EvaluationUseCountVo> listUsed();
}
