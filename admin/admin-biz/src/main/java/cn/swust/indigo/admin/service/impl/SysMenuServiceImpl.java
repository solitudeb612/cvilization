package cn.swust.indigo.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.swust.indigo.admin.entity.po.SysMenu;
import cn.swust.indigo.admin.entity.po.SysRoleMenu;
import cn.swust.indigo.admin.entity.vo.MenuVO;
import cn.swust.indigo.admin.mapper.SysMenuMapper;
import cn.swust.indigo.admin.mapper.SysRoleMenuMapper;
import cn.swust.indigo.admin.service.SysMenuService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    private final SysRoleMenuMapper sysRoleMenuMapper;


    /**
     * 通过角色id查询菜单
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")
    public List<MenuVO> findMenuByRoleId(Integer roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    /**
     * 不能删除含有下级的菜单
     *
     * @param id 菜单ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public boolean removeMenuById(Integer id) {
        // 查询父节点为当前节点的节点
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query()
                .lambda().eq(SysMenu::getParentId, id));
        if (CollUtil.isNotEmpty(menuList)) {
            return false; //有子节点，不能删除
        }
        sysRoleMenuMapper
                .delete(Wrappers.<SysRoleMenu>query()
                        .lambda().eq(SysRoleMenu::getMenuId, id));

        //删除当前菜单及其子菜单
        return this.removeById(id);
    }

    /**
     * 更新菜单
     *
     * @param sysMenu 菜单信息
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }
}
