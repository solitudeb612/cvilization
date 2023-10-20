package cn.swust.indigo.mce.service;


import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface CheckGroupCriteriaService extends IService<CheckGroupCriteria> {
    List<CheckGroupCriteria> list(int group_id);

}
