package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.SearchRecord;
import cn.swust.indigo.mce.entity.vo.SearchRecordVo;
import cn.swust.indigo.mce.service.ISearchRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
 * <p>
 * 微信搜索记录 前端控制器
 * </p>
 *
 * @author binary
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/searchrecord")
@Api(tags = "微信搜索记录")
public class SearchRecordController {

    @Autowired
    ISearchRecordService searchRecordService;

    @PutMapping("/save")
    @ApiOperation(value = "根据用户ID存储搜索记录")
    public R save(@RequestBody SearchRecordVo searchRecordVo) {
        searchRecordService.saveSearchRecord(searchRecordVo);
        return R.ok();
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据用户ID获取相关搜索记录")
    public R list(@RequestParam ArrayList<Integer> searchIds) {
        LambdaQueryWrapper<SearchRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchRecord::getUserId, SecurityUtils.getUser().getSysUser().getUserId())
                .in(ObjectUtil.isNotNull(searchIds),SearchRecord::getSearchId, searchIds)
                .orderByDesc(SearchRecord::getSearchTimes);
        return R.ok(searchRecordService.list(wrapper));
    }
}
