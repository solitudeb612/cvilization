package cn.swust.indigo.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.admin.entity.po.SysNoticeRead;
import cn.swust.indigo.admin.mapper.SysNoticeReadMapper;
import cn.swust.indigo.admin.service.SysNoticeReadService;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

/**
 * 系统通知阅读
 *
 * @author lhz
 * @date 2023-03-01 13:39:24
 */
@Service
@AllArgsConstructor
public class SysNoticeReadServiceImpl extends ServiceImpl<SysNoticeReadMapper, SysNoticeRead> implements SysNoticeReadService {

}
