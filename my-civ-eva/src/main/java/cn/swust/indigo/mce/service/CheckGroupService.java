package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.CheckGroup;
import cn.swust.indigo.mce.entity.vo.QCheckGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CheckGroupService extends IService<CheckGroup> {

    List<CheckGroup> getGroupChecksByPy(String py);

    Boolean updateCheckGroup(QCheckGroup qCheckGroup);
}
