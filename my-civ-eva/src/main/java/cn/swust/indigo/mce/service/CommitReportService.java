package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.CommitReport;
import cn.swust.indigo.mce.entity.po.CommitReportVo1;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author Binary cat
 * @date 2023/3/23 14:33
 */
public interface CommitReportService extends IService<CommitReport> {
    /**
     * 检查report的状态是否已经提交，如果是 true 表示已经提交
     * @param reportId reportId
     * @return ture or false
     */
    boolean checkStatusCommit(Integer reportId);

    /**
     * 检查commitDetail的所属report的状态是否已经提交，如果是 true 表示已经提交
     * @param commitDetailId
     * @return
     */
    boolean checkStatusByDetailId(Integer commitDetailId);


    /**
     * 根据reportId检测结果是否类型正确和包括必要文件
     * @param reportId reportId
     * @return CommitReportFailedType
     */
    Integer checkSubmit(Integer reportId);
}
