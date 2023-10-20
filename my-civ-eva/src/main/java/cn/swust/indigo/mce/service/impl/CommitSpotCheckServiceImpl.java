package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.entity.po.CommitSpotCheck;
import cn.swust.indigo.mce.mapper.CommitSpotCheckMapper;
import cn.swust.indigo.mce.service.CommitSpotCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 项目抽查(CommitSpotCheck)表服务实现类
 *
 * @author makejava
 * @since 2023-03-25 19:45:06
 */
@Service("commitSpotCheckService")
public class CommitSpotCheckServiceImpl extends ServiceImpl<CommitSpotCheckMapper, CommitSpotCheck> implements CommitSpotCheckService {

}

