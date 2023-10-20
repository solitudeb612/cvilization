package cn.swust.indigo.mce.entity.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class QCommitSpotCheck extends CommitSpotCheck {

    @ApiModelProperty(value = "抽查驳回")
    private Integer status=2;//默认是通过 （2代表通过 3代表牵头部门驳回 4代表抽查驳回）

    @NotNull
    @ApiModelProperty(value = "分组id")
    private Integer reportId;
}
