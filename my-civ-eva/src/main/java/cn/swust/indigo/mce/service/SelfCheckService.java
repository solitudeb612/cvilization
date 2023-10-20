package cn.swust.indigo.mce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.SelfCheck;

/**
 * 自查通知
 *
 * @author lhz
 * @date 2023-03-18 10:14:00
 */
public interface SelfCheckService extends IService<SelfCheck> {
    boolean updateState(Integer guideId, Integer status);

    SelfCheck calculateNum(SelfCheck selfCheck);
}
