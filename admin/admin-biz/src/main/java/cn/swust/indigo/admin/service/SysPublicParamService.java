package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.po.SysPublicParam;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 公共参数配置
 *
 * @author Lucky
 * @date 2019-04-29
 */
public interface SysPublicParamService extends IService<SysPublicParam> {

    /**
     * 通过key查询公共参数指定值
     *
     * @param publicKey
     * @return
     */
    String getSysPublicParamKeyToValue(String publicKey);

    /**
     * 更新参数
     *
     * @param sysPublicParam
     * @return
     */
    R updateParam(SysPublicParam sysPublicParam);

    /**
     * 删除参数
     *
     * @param publicId
     * @return
     */
    R removeParam(Long publicId);
}
