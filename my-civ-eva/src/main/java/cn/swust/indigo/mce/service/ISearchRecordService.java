package cn.swust.indigo.mce.service;


import cn.swust.indigo.mce.entity.po.SearchRecord;
import cn.swust.indigo.mce.entity.vo.SearchRecordVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微信搜索记录 服务类
 * </p>
 *
 * @author binary
 * @since 2023-05-23
 */
public interface ISearchRecordService extends IService<SearchRecord> {

    public void saveSearchRecord(SearchRecordVo searchRecordVo);
}
