package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.po.SysUserRole;
import cn.swust.indigo.admin.service.SysDeptService;
import cn.swust.indigo.admin.service.SysRoleService;
import cn.swust.indigo.admin.service.SysUserRoleService;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.mce.entity.po.Site;
import cn.swust.indigo.mce.entity.vo.SiteVo;
import cn.swust.indigo.mce.mapper.SiteMapper;
import cn.swust.indigo.mce.service.SiteService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 站点
 *
 * @author lhz
 * @date 2023-02-24 17:01:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/site")
@Api(value = "site", tags = "站点管理")
public class SiteController {

    @Autowired
    private final SiteService siteService;

    @Autowired
    SiteMapper siteMapper;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysDeptService sysDeptService;

    /**
     * 分页查询
     *
     * @param index 当前页数
     * @param size  每页大小
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getSitePage(@RequestParam Integer index, @RequestParam Integer size, @RequestParam(value = "longitude", required = false) String longitude, @RequestParam(value = "latitude", required = false) String latitude, @RequestParam(value = "sitePy", required = false) String sitePy, @RequestParam(value = "leaderName", required = false) String leaderName) {
        Page<SiteVo> sitePage = new Page<>(index, size);
        IPage<SiteVo> siteVoList = siteService.getSiteVoList(sitePage, longitude, latitude, sitePy, leaderName);
        return siteVoList != null? R.ok(siteVoList) : R.failed("查询失败，站点不存在");
    }

    /**
     * 通过id查询站点
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询站点", notes = "通过id查询站点")
    @GetMapping("/get")
    public R getById(@RequestParam("siteId") Integer id) {
        SiteVo siteVoById = siteService.getSite(id);
        if (ObjectUtil.isNotNull(siteVoById)) {
            return R.ok(siteVoById);
        } else {
            return R.failed("站点不存在或者已被删除！");
        }
    }

    /**
     * 新增站点
     *
     * @param site 站点
     * @return R
     */
    @ApiOperation(value = "新增站点", notes = "新增站点")
    @Log("新增站点")
    @PostMapping
//    @PreAuthorize("hasAuthority('site_add')" )
    public R save(@RequestBody Site site) {
        boolean flag = siteService.save(site);
        return flag? R.ok(site.getSiteId()) : R.failed("新增失败");
    }

    /**
     * 修改站点
     *
     * @param site 站点
     * @return R
     */
    @ApiOperation(value = "修改站点", notes = "修改站点")
    @Log("修改站点")
    @PutMapping
//    @PreAuthorize("hasAuthority('site_edit')" )
    public R updateById(@RequestBody Site site) {
        return R.ok(siteService.updateById(site));
    }


    /**
     * 通过id删除站点
     *
     * @param id
     * @return R
     */
    @ApiOperation(value = "通过站点的id删除站点", notes = "通过站点的id删除站点")
    @Log("通过id删除站点")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
//    @PreAuthorize("hasAuthority('site_del')" )
    public R removeById(@RequestParam("id") Integer id) {
        return R.ok(siteService.removeById(id));
    }

    /**
     * 将站点归纳到部门下后查看所有部门树
     *
     * @return 部门树(包括站点)
     */
    @ApiOperation(value = "将站点归纳到部门下后查看所有部门树", notes = "将站点归纳到部门下后查看所有部门树")
    @GetMapping("/tree")
    public R getDeptWithSite(@RequestParam("department_type") Integer department_type) {
        return R.ok(siteService.selectTreeAll(department_type));
//        return R.ok(siteService.selectCheckedTreeAll(department_type));
    }


    /**
     * 获取所有的站点信息
     *
     * @return 站点
     */
    @ApiOperation(value = "获取所有的站点信息", notes = "获取所有的站点信息")
    @GetMapping("/getAll")
    public R getAllSite() {
        List<SiteVo> all = siteService.getAll();
        return R.ok(all);
    }

    /**
     * 根据用户Id查询站点信息
     *
     * @param leaderId 用户Id
     * @return 成功返回站点信息，错误返回错误
     */
    @ApiOperation(value = "根据用户Id查询站点信息", notes = "根据用户Id查询站点信息")
    @GetMapping("/getSiteById")
    public R getSiteByLeaderId(@RequestParam Integer leaderId) {
        if (ObjectUtil.isNull(leaderId)) {
            R.failed("参数不能为空！");
        }
        SiteVo siteVoById = siteMapper.getSiteVoById(leaderId);
        return ObjectUtil.isNotNull(siteVoById) ? R.ok(siteVoById) : R.failed("数据不存在！");
    }

    @Deprecated
    @PostMapping("/nouse")
    public R setSitePy() {
        List<Site> sites = siteMapper.selectList(null);
        List<Site> collect = sites.stream().peek(Site::getSitePy).collect(Collectors.toList());
        siteService.saveOrUpdateBatch(collect);

//        List<SysDept> list = sysDeptService.list(null);
//        List<SysDept> collect = list.stream().peek(SysDept::getDepartmentPy).collect(Collectors.toList());
//        sysDeptService.saveOrUpdateBatch(collect);
        return R.ok();
    }


    @ApiOperation(value = "获取所有用户身份是站点管理员的信息")
    @GetMapping("/userInfo")
    public R getUserInfo() {
        // 根据角色获取用户ID
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, 4);
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(queryWrapper);
        // 获取用户
        List<Integer> userIds = sysUserRoles.stream().map(SysUserRole::getUserId).collect(Collectors.toList());

        if (ObjectUtil.isNull(userIds) || userIds.size() == 0 ) {
            return R.failed("不存在站点管理员！请新增或授权后操作！");
        } else {
            Collection<SysUser> sysUsers = sysUserService.listByIds(userIds);
            if (ObjectUtil.isNotNull(sysUsers)) {
                return R.ok(sysUsers);
            } else {
                return R.failed("查询失败！可能是没有此类用户或数据错误！");
            }

        }
    }



}
