package cn.swust.indigo.admin.controller;


import cn.swust.indigo.admin.entity.po.SysDict;
import cn.swust.indigo.admin.entity.po.SysDictItem;
import cn.swust.indigo.admin.entity.vo.QSysDict;
import cn.swust.indigo.admin.entity.vo.QSysDictItem;
import cn.swust.indigo.admin.service.SysDictItemService;
import cn.swust.indigo.admin.service.SysDictService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.common.log.annotation.Log;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/dicts")
@Api(value = "dict", tags = "字典管理模块")
public class SysDictController {
    private final SysDictService sysDictService;
    private final SysDictItemService sysDictItemService;

    /**
     * 通过ID查询字典信息
     *
     * @param dictId ID
     * @return 字典信息
     */
    @GetMapping("/get/")
    @ApiParam(required = true, value = "查询编号")
    @ApiOperation(value = "通过ID查询字典信息", notes = "通过ID查询字典信息")
    public R getById(@RequestParam("dictId") @NotNull(message = "dictId不能为空") Integer dictId) {
        return R.ok(sysDictService.getById(dictId));
    }


    /**
     * 通过字典类型查找字典
     *
     * @param type 类型
     * @return 同类型字典
     */
    @GetMapping("/type/")
    @ApiOperation(value = "通过字典类型查找字典", notes = "通过字典类型查找字典")
    @Cacheable(value = CacheConstants.DICT_DETAILS, key = "#type", unless = "#result == null")
    public R getDictByType(@RequestParam("type") String type) {
        return R.ok(sysDictItemService.list(Wrappers
                .<SysDictItem>query().lambda()
                .eq(SysDictItem::getType, type)));
    }

    /**
     * 添加字典
     *
     * @param sysDict 字典信息
     * @return success、false
     */
    @Log("添加字典")
    //修改其二:  --->/add
    @PostMapping("/add")
    @ApiOperation(value = "添加字典", notes = "添加字典")
//    @PreAuthorize("hasAuthority('sys_dict_add')")
    public R add(@Valid @RequestBody SysDict sysDict) {
        return R.ok(sysDictService.save(sysDict));
    }

    /**
     * 删除字典，并且清除字典缓存
     * @param id ID
     * @return R
     */
    @Log("删除字典")
    @DeleteMapping("/del/")
    @ApiOperation(value = "删除字典", notes = "删除字典")
//    @PreAuthorize("hasAuthority('sys_dict_del')")
    public R removeById(@ApiParam(required = true,value = "字典ID") @RequestParam("id") Integer id) {
        return sysDictService.removeDict(id);
    }


    /**
     * 修改字典
     *
     * @param sysDict 字典信息
     * @return success/false
     */
    //修改： ---> /edit
    @PutMapping("/update")
    @ApiOperation(value = "修改字典", notes = "修改字典")
    @Log("修改字典")
//    @PreAuthorize("hasAuthority('sys_dict_edit')")
    public R updateDict(@Valid @RequestBody SysDict sysDict) {
        return sysDictService.updateDict(sysDict);
    }

    /**
     * 分页查询字典信息
     *
     * @return 分页对象
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息")
    public R getDictPage(Page<SysDict> page, QSysDict sysDict) {
        return R.ok(sysDictService.page(page, CreateQuery.getQueryWrapper(sysDict)));
    }


    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param sysDictItem 字典项
     * @return
     */
    @GetMapping("/item/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R getSysDictItemPage(Page page, QSysDictItem sysDictItem) {
        return R.ok(sysDictItemService.page(page, CreateQuery.getQueryWrapper(sysDictItem)));
    }


    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return R
     */
    @GetMapping("/item/{id}")
    @ApiOperation(value = "通过id查询字典项", notes = "通过id查询字典项")
    public R getDictItemById(@PathVariable("id") Integer id) {
        return R.ok(sysDictItemService.getById(id));
    }

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return R
     */
    @GetMapping("/item/get")
    @ApiOperation(value = "通过id查询字典项", notes = "通过id查询字典项")
    public R getDictItemByParamId(@RequestParam("id") @NotNull(message = "id不能为空") Integer id) {
        return R.ok(sysDictItemService.getById(id));
    }

    /**
     * 新增字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @Log("新增字典项")
    @PostMapping("/item")
    @ApiOperation(value = "新增字典项", notes = "新增字典项")
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R save(@RequestBody SysDictItem sysDictItem) {
        return R.ok(sysDictItemService.save(sysDictItem));
    }

    /**
     * 修改字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @Log("修改字典项")
    @PutMapping("/item")
    @ApiOperation(value = "修改字典项", notes = "修改字典项")
    public R updateById(@RequestBody SysDictItem sysDictItem) {
        return sysDictItemService.updateDictItem(sysDictItem);
    }

    /**
     * 通过id删除字典项
     *
     * @param id id
     * @return R
     */
    @Log("删除字典项")
    @DeleteMapping("/item/{id}")
    @ApiOperation(value = "通过id删除字典项", notes = "通过id删除字典项")
    public R removeDictItemById(@PathVariable Integer id) {
        return sysDictItemService.removeDictItem(id);
    }

    /**
     * 通过id查询所有字典项
     *
     * @return R
     */
    @Log("得到所有字典")
    @ApiOperation(value = "通过id查询所有字典项", notes = "通过id查询所有字典项")
    @GetMapping("/getAll")
    public R getDictByAll() {
        return R.ok(sysDictService.list(Wrappers.emptyWrapper()));
    }

}
