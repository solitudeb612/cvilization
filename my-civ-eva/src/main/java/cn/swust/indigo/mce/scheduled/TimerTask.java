package cn.swust.indigo.mce.scheduled;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.entity.po.SelfCheck;
import cn.swust.indigo.mce.service.GuideService;
import cn.swust.indigo.mce.service.ISearchRecordService;
import cn.swust.indigo.mce.service.SelfCheckService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author Binary cat
 * @date 2023/4/19 19:33
 */
@Component
public class TimerTask {
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Autowired
    GuideService guideService;

    @Autowired
    ISearchRecordService searchRecordService;

    @Autowired
    SelfCheckService selfCheckService;

    /**
     * corn：每天凌晨一点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/30 * * * * ?")
    public void runTask() {
        executor.schedule(() -> {
            List<Guide> list = guideService.list(null);
            LocalDateTime now = LocalDateTimeUtil.now();
            for (Guide guide : list) {
                System.out.println("processing : " + guide.getGuideId());
                if (ObjectUtil.isNotNull(guide.getBeginTime())&& ObjectUtil.isNotNull(guide.getEndTime())){
                    if (now.isBefore(guide.getBeginTime())) {
                        guide.setStatus(1);
                    } else if (now.isAfter(guide.getEndTime())) {
                        guide.setStatus(3);
                    } else {
                        guide.setStatus(2);
                    }
                }
            }
            guideService.updateBatchById(list);
            System.out.println("-----更新完成--------");

            // 清除搜索记录
            searchRecordService.remove(null);

            // 考察活动的推进 活动状态 0 活动初稿 1 活动发布 2 活动开始 3 活动结束
            List<SelfCheck> selfChecks = selfCheckService.list(new LambdaQueryWrapper<SelfCheck>()
                    .eq(SelfCheck::getStatus, 1).
                    eq(SelfCheck::getStatus, 2));

            for (SelfCheck selfCheck : selfChecks) {
                if (ObjectUtil.isNotNull(selfCheck.getBeginTime()) && ObjectUtil.isNotNull(selfCheck.getEndTime())){
                    if (selfCheck.getStatus() == 1 && selfCheck.getBeginTime().isAfter(now) ){
                        //活动开始
                        selfCheck.setStatus(2);
                    }
                    if (selfCheck.getStatus() == 2 && selfCheck.getEndTime().isAfter(now) ){
                        //活动结束
                        selfCheck.setStatus(3);
                    }
                }
            }



        }, 3000, TimeUnit.MILLISECONDS);
    }

}
