package cn.swust.indigo.admin.service.impl;

import cn.swust.indigo.admin.entity.po.SysNoticeRead;
import cn.swust.indigo.admin.service.SysNoticeReadService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.admin.entity.po.SysNotice;
import cn.swust.indigo.admin.mapper.SysNoticeMapper;
import cn.swust.indigo.admin.service.SysNoticeService;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
/**
 * 系统通知
 *
 * @author lhz
 * @date 2023-03-01 13:38:37
 */
@Service
@AllArgsConstructor
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    private final SysNoticeReadService readService;

    /**
     * 查询系统公告，公告状态为1则返回，为0则返回null
     * @param id
     * @return
     */
    @Override
    public SysNotice getById(Serializable id) {
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        SysNoticeRead noticeRead = new SysNoticeRead((Integer) id, userId, null);
        QueryWrapper<SysNotice> wrapper = new QueryWrapper<>();
        wrapper.eq("status","1").eq("notice_id",id);
        SysNotice sysNotice = super.getOne(wrapper);
        if(sysNotice==null){
            return null;
        }
        readService.saveOrUpdate(noticeRead);
        return sysNotice;
    }

    @Override
    public IPage<SysNotice> page(IPage<SysNotice> page, Wrapper<SysNotice> queryWrapper) {
                IPage<SysNotice> noticePage =  super.page(page, queryWrapper);
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();

        for (SysNotice notice:noticePage.getRecords()){
            int count = readService.count(new QueryWrapper<SysNoticeRead>().eq("notice_id", notice.getNoticeId()).eq("user_id", userId));
            if (count > 0) {
                notice.setIsRead(true);
            }
        }
        return   noticePage;
    }
    //    @Override
//    public <E extends IPage<SysNotice>> E page(E page, Wrapper<SysNotice> queryWrapper) {
//        IPage<SysNotice> noticePage =  super.page(page, queryWrapper);
//        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
//
//        for (SysNotice notice:noticePage.getRecords()){
//            int count = readService.count(new QueryWrapper<SysNoticeRead>().eq("notice_id", notice.getNoticeId()).eq("user_id", userId));
//            if (count > 0) {
//                notice.setIsRead(true);
//            }
//        }
//
//        return (E) noticePage;
//    }
}
