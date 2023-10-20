package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.po.SysDictItem;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典项
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     * @return
     */
    R removeDictItem(Integer id);

    /**
     * 更新字典项
     *
     * @param item 字典项
     * @return
     */
    R updateDictItem(SysDictItem item);
}
