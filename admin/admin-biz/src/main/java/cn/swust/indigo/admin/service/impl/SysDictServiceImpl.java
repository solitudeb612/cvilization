package cn.swust.indigo.admin.service.impl;


import cn.swust.indigo.admin.entity.po.SysDict;
import cn.swust.indigo.admin.entity.po.SysDictItem;
import cn.swust.indigo.admin.mapper.SysDictItemMapper;
import cn.swust.indigo.admin.mapper.SysDictMapper;
import cn.swust.indigo.admin.service.SysDictService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.constants.enums.DictTypeEnum;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典表
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    private final SysDictItemMapper dictItemMapper;

    /**
     * 根据ID 删除字典
     *
     * @param id 字典ID
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public R removeDict(Integer id) {
        SysDict dict = this.getById(id);
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典不能删除");
        }
        baseMapper.deleteById(id);
        dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery()
                .eq(SysDictItem::getDictId, id));
        return R.ok();
    }

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    @Override
    public R updateDict(SysDict dict) {
        SysDict sysDict = this.getById(dict.getDictId());
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(sysDict.getSystem())) {
            return R.failed("系统内置字典不能更新");
        }
        return R.ok(this.updateById(dict));
    }
}
