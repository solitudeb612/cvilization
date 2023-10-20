package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.mce.entity.po.CheckCommit;
import cn.swust.indigo.mce.entity.po.CheckReport;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CheckReportAll extends CheckReport {
    List<CheckCommit> checkCommits;
}
