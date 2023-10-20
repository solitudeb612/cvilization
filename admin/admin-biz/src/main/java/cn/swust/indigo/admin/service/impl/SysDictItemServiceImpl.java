package cn.swust.indigo.admin.service.impl;

import cn.swust.indigo.admin.entity.po.SysDict;
import cn.swust.indigo.admin.entity.po.SysDictItem;
import cn.swust.indigo.admin.mapper.SysDictItemMapper;
import cn.swust.indigo.admin.service.SysDictItemService;
import cn.swust.indigo.admin.service.SysDictService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.constants.enums.DictTypeEnum;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 字典项
 */
@Service
@AllArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {
    private final SysDictService dictService;

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R removeDictItem(Integer id) {
        //查询itemId
        SysDictItem dictItem = this.getById(id);
        //根据itemID得到字典id 查询字典ID
        SysDict dict = dictService.getById(dictItem.getDictId());
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典项目不能删除");
        }
        return R.ok(this.removeById(id));
    }

    /**
     * 更新字典项
     *
     * @param item 字典项
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R updateDictItem(SysDictItem item) {
        //查询字典
        SysDict dict = dictService.getById(item.getDictId());
        if (dict == null) {
            return R.failed("字典不存在");
        }
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典项目不能删除");
        }
        return R.ok(this.updateById(item));
    }
}
