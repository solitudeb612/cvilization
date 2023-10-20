package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.admin.entity.po.SysNotice;
import lombok.Data;

import java.util.Date;

@Data
public class SysNoticeInfo extends SysNotice {
    private boolean isRead;
    private Date readTime;
}
