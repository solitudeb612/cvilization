package cn.swust.indigo.admin.controller;

import cn.swust.indigo.admin.entity.po.SysPublicParam;
import cn.swust.indigo.admin.entity.vo.QSysParam;
import cn.swust.indigo.admin.service.SysPublicParamService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.common.log.annotation.Log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * 公共参数
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/param")
@Api(value = "param", tags = "公共参数配置")
public class SysPublicParamController {

    private final SysPublicParamService sysPublicParamService;

    /**
     * 通过key查询公共参数值
     *
     * @param publicKey
     * @return
     */
    @ApiOperation(value = "查询公共参数值", notes = "根据key查询公共参数值")
    @GetMapping("/publicValue/{publicKey}")
    public R publicKey(@PathVariable("publicKey") String publicKey) {
        return R.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
    }

    /**
     * 通过key查询公共参数值
     *
     * @param publicKey
     * @return
     */
    @ApiOperation(value = "查询公共参数值", notes = "根据key查询公共参数值")
    @GetMapping("/publicValue/publicKey")
    public R getByPublicKey(@RequestParam("publicKey") @NotNull(message = "publicKey不能为空") String publicKey) {
        return R.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
    }

    /**
     * 分页查询
     *
     * @param page           分页参数
     * @param sysPublicParam 公共参数
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getSysPublicParamPage(Page<SysPublicParam> page, QSysParam sysPublicParam) {
        return R.ok(sysPublicParamService.page(page, CreateQuery.getQueryWrapper(sysPublicParam)));
    }


    /**
     * 通过id查询公共参数
     *
     * @param publicId id
     * @return R
     */
    @ApiOperation(value = "通过id查询公共参数", notes = "通过id查询公共参数")
    @GetMapping("/{publicId}")
    public R getById(@PathVariable("publicId") Long publicId) {
        return R.ok(sysPublicParamService.getById(publicId));
    }

    /**
     * 通过id查询公共参数
     *
     * @param publicId id
     * @return R
     */
    @ApiOperation(value = "通过id查询公共参数", notes = "通过id查询公共参数")
    @GetMapping("/get")
    public R getByParamId(@RequestParam("publicId") @NotNull(message = "publicId不能为空") Long publicId) {
        return R.ok(sysPublicParamService.getById(publicId));
    }

    /**
     * 新增公共参数
     *
     * @param sysPublicParam 公共参数
     * @return R
     */
    @ApiOperation(value = "新增公共参数", notes = "新增公共参数")
    @Log("新增公共参数")
    @PostMapping
//    @PreAuthorize("hasAuthority('admin_syspublicparam_add')")
    public R save(@RequestBody SysPublicParam sysPublicParam) {
        return R.ok(sysPublicParamService.save(sysPublicParam));
    }

    /**
     * 修改公共参数
     *
     * @param sysPublicParam 公共参数
     * @return R
     */
    @ApiOperation(value = "修改公共参数", notes = "修改公共参数")
    @Log("修改公共参数")
    @PutMapping
//    @PreAuthorize("hasAuthority('admin_syspublicparam_edit')")
    public R updateById(@RequestBody SysPublicParam sysPublicParam) {
        return sysPublicParamService.updateParam(sysPublicParam);
    }

    /**
     * 通过id删除公共参数
     *
     * @param publicId id
     * @return R
     */
    @ApiOperation(value = "删除公共参数", notes = "删除公共参数")
    @Log("删除公共参数")
    @DeleteMapping("/{publicId}")
//    @PreAuthorize("hasAuthority('admin_syspublicparam_del')")
    public R removeById(@PathVariable Long publicId) {
        return sysPublicParamService.removeParam(publicId);
    }

    /**
     * 通过id删除公共参数
     *
     * @param publicId id
     * @return R
     */
    @ApiOperation(value = "删除公共参数", notes = "删除公共参数")
    @Log("删除公共参数")
    @DeleteMapping("/del")
//    @PreAuthorize("hasAuthority('admin_syspublicparam_del')")
    public R removeByParamId(@RequestParam("publicId") @NotNull(message = "publicId不能为空") Long publicId) {
        return sysPublicParamService.removeParam(publicId);
    }

}
