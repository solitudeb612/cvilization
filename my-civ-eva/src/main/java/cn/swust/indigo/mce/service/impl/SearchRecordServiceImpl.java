package cn.swust.indigo.mce.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.SearchRecord;
import cn.swust.indigo.mce.entity.vo.SearchRecordVo;
import cn.swust.indigo.mce.mapper.SearchRecordMapper;
import cn.swust.indigo.mce.service.ISearchRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信搜索记录 服务实现类
 * </p>
 *
 * @author binary
 * @since 2023-05-23
 */
@Service
public class SearchRecordServiceImpl extends ServiceImpl<SearchRecordMapper, SearchRecord> implements ISearchRecordService {

    @Override
    public void saveSearchRecord(SearchRecordVo searchRecordVo) {
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setUserId(SecurityUtils.getUser().getSysUser().getUserId());
        searchRecord.setRecordContent(searchRecordVo.getRecordContent());
        searchRecord.setSearchId(searchRecordVo.getSearchId());

        LambdaQueryWrapper<SearchRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchRecord::getSearchId,searchRecord.getSearchId())
                .eq(SearchRecord::getUserId,searchRecord.getUserId())
                .eq(SearchRecord::getRecordContent,searchRecord.getRecordContent()).last(" limit 1");
        SearchRecord origin = this.getOne(wrapper);

        if (ObjectUtil.isNotNull(origin)) {
            origin.setSearchTimes(origin.getSearchTimes() + 1);
            this.updateById(origin);
        } else{
            searchRecord.setCreateTime(LocalDateTime.now());
            searchRecord.setSearchTimes(1);
            this.save(searchRecord);
        }
    }
}
