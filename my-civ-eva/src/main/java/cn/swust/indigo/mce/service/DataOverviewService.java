package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.vo.DataVo;
import cn.swust.indigo.mce.entity.vo.GuidePerCount;

import java.util.List;

/**
 * @author Binary cat
 * @date 2023/4/25 15:26
 */
public interface DataOverviewService {

    List<GuidePerCount> getGuideMonthCount();
    List<GuidePerCount> getGuideYearCount();

    DataVo getDataVo();


}
