package cn.swust.indigo.mce.service.impl;



import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import cn.swust.indigo.mce.mapper.CheckGroupCriteriaMapper;
import cn.swust.indigo.mce.service.CheckGroupCriteriaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自查组部门规则设置
 *
 * @author lhz
 * @date 2023-03-21 20:50:21
 */
@Service
@AllArgsConstructor
public class CheckGroupCriteriaServiceImpl extends ServiceImpl<CheckGroupCriteriaMapper, CheckGroupCriteria>
        implements CheckGroupCriteriaService {

    @Autowired
    private CheckGroupCriteriaMapper checkGroupCriteriaMapper;

    @Override
    public List<CheckGroupCriteria> list(int group_id) {
        List<CheckGroupCriteria> list = checkGroupCriteriaMapper.list(group_id);
        return list;
    }

}
