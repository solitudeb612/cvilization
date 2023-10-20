package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.CommitImgStar;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * 申报内容详情 主要是图片  服务类
 * </p>
 *
 * @author binaryCat
 * @since 2023-04-01
 */
public interface ICommitImgStarService extends IService<CommitImgStar> {

    IPage<CommitImgStar> imagesList(Integer guideId, Integer ruleId, Integer reportId, Integer commitDetailId, Integer isStar, IPage page);
}
