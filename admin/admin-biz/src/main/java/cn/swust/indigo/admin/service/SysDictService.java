package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.po.SysDict;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典表
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 根据ID 删除字典
     *
     * @param id
     * @return
     */
    R removeDict(Integer id);

    /**
     * 更新字典
     *
     * @param sysDict 字典
     * @return
     */
    R updateDict(SysDict sysDict);
}
