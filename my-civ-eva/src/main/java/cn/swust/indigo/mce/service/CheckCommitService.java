package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.vo.CheckCommitAllInfo;
import cn.swust.indigo.mce.entity.vo.QCheckCommit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.CheckCommit;

/**
 * 自查报告提交内容
 *
 * @author lhz
 * @date 2023-03-18 10:33:42
 */
public interface CheckCommitService extends IService<CheckCommit> {

    Page<CheckCommitAllInfo> commitPage(Page page, QCheckCommit checkCommit);

    boolean saveCommit(CheckCommit checkCommit);
}
