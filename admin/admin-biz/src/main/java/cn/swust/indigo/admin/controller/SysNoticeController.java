package cn.swust.indigo.admin.controller;

import cn.swust.indigo.admin.entity.po.SysNoticeRead;
import cn.swust.indigo.admin.entity.vo.SysNoticeInfo;
import cn.swust.indigo.admin.service.SysNoticeReadService;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.admin.entity.po.SysNotice;
import cn.swust.indigo.admin.service.SysNoticeService;
import cn.swust.indigo.admin.entity.vo.QSysNotice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 系统通知
 *
 * @author lhz
 * @date 2023-03-01 13:38:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/notice")
@Api(value = "sys-notice", tags = "系统通知管理")
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;



    /**
     *
     * @param sysNotice
     * @return
     */
    //发布和撤销发布
    @ApiOperation(value = "发布/撤销发布公告",notes = "发布/撤销发布公告")
    @PutMapping("/published")
    public R publishOrCancel(@RequestBody SysNotice sysNotice){
        SysNotice noticeServiceById = sysNoticeService.getBaseMapper().selectById(sysNotice.getNoticeId());
        String status = noticeServiceById.getStatus();
        if("0".equals(status)){
            sysNotice.setStatus("1");
        }else if("1".equals(status)){
            sysNotice.setStatus("0");
        }
        return R.ok(sysNoticeService.updateById(sysNotice));
    }
    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param sysNotice 系统通知
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getSysNoticePage(Page<SysNotice> page, QSysNotice sysNotice) {
//        SecurityUtils.getUser().getSysUser().getUserId()
        QueryWrapper<SysNotice> query = CreateQuery.getQueryWrapper(sysNotice);
        query.and(qr->qr.eq("user_id", null).or().eq("user_id", SecurityUtils.getUser().getSysUser().getUserId()));
        query.orderByDesc("create_time").eq("status","1");
        return R.ok(sysNoticeService.page(page, query));
    }

    /**
     * 通过id查询系统通知
     * 系统状态为1则返回,为0返回null
     * @param id id
     * @return R
     */

    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get")
    public R getById(@RequestParam("noticeId") Integer id) {
        return R.ok(sysNoticeService.getById(id));
    }

    /**
     * 新增系统通知
     *
     * @param sysNotice 系统通知
     * @return R
     */
    @ApiOperation(value = "新增系统通知", notes = "新增系统通知")
    @Log("新增系统通知")
    @PostMapping
//    @PreAuthorize("hasAuthority('sysnotice_add')" )
    public R save(@RequestBody SysNotice sysNotice) {
        sysNotice.setStatus("0");
        return R.ok(sysNoticeService.save(sysNotice));
    }

    /**
     * 修改系统通知
     *
     * @param sysNotice 系统通知
     * @return R
     */
    @ApiOperation(value = "修改系统通知", notes = "修改系统通知")
    @Log("修改系统通知")
    @PutMapping
//    @PreAuthorize("hasAuthority('sysnotice_edit')" )
    public R updateById(@RequestBody SysNotice sysNotice) {
        SysNotice noticeServiceById = sysNoticeService.getBaseMapper().selectById(sysNotice.getNoticeId());
        if("1".equals(noticeServiceById.getStatus())){
            return R.failed("通知已发布，不可修改");
        }
        return R.ok(sysNoticeService.updateById(sysNotice));
    }


    /**
     * 通过id删除系统通知
     *
     * @param id
     * @return R
     */
    @ApiOperation(value = "通过id删除系统通知", notes = "通过id删除系统通知")
    @Log("通过id删除系统通知")
    @DeleteMapping("/del")
//    @PreAuthorize("hasAuthority('sysnotice_del')" )
    public R removeById(@RequestParam("noticeId") Integer id) {
        SysNotice sysNotice = sysNoticeService.getBaseMapper().selectById(id);
        if("1".equals(sysNotice.getStatus())){
            return R.failed("通知已发布，不可删除,请撤销发布后删除");
        }
        return R.ok(sysNoticeService.removeById(id));
    }

}
