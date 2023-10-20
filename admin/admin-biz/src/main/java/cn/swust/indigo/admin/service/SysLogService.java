package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.po.SysLog;
import cn.swust.indigo.admin.entity.vo.PreLogVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * 日志表 服务类
 * </p>
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 批量插入前端错误日志
     *
     * @param preLogVoList 日志信息
     * @return true/false
     */
    Boolean saveBatchLogs(List<PreLogVo> preLogVoList);

}
