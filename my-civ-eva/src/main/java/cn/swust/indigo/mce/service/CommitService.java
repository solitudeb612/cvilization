package cn.swust.indigo.mce.service;

import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.po.*;
import cn.swust.indigo.mce.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目提交内容
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
public interface CommitService extends IService<CommitDetail> {

    @Deprecated
    List<CommitRuleTableInfo> getCommitList(QCommitDetail commit);

    List<Department> getLeadDepartment(Integer guideId);

    Page getAllDepartmentCommitByAdmin(QAdminRuleTask qAdminRuleTask, Page page);

    List<CommitReportVo> getCommitReportByTaskId(Integer taskId, Integer guideId, Integer status);

    List<CommitReportVo> getTaskCommitReportByTaskId(Integer ruleId, Integer departmentId, Integer guideId, Integer status);
    @Deprecated
    R saveCommitDetailList(CommitReportAndDetail commitReportAndDetail);


    boolean reviewCommit(QCommitReview qCommitReview);

    Integer spotReviewCommit(QCommitSpotCheck qCommitSpotCheck);

    @Deprecated
    List<CommitReport> getCommitReportList(QCommitReport commitDetail);

    List<Integer> getStarImageFileIds(Integer guideId, Integer ruleId, boolean isStar);

    void getStarImagesByIds(ArrayList<Integer> fileIds, HttpServletResponse response);

    /**
     * 将一次网上申报内容加入图片数据库中，
     *
     * @param reportId 必须正确
     * @return 返回添加的图片个数
     */
    Integer saveImagesIdsByReportId(Integer reportId);

    List<CommitRuleTableInfo> listCommitRuleTableInfo(QCommitDetail qCommitDetail);

    List<TaskCommitWithDetail> getTaskAndDetail(Integer ruleId, Integer departmentId, Integer guideId, List<TaskCommit> taskCommits);

    boolean clearFileIds(Integer commitId);

}
