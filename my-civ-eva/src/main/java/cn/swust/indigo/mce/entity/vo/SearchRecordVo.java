package cn.swust.indigo.mce.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

/**
 * @author Binary Tree Cat
 */
@Data
@NoArgsConstructor
public class SearchRecordVo {

    @ApiModelProperty("搜索内容")
    @NotNull
    private String recordContent;

    @ApiModelProperty("搜索框编号")
    @NotNull
    private Integer searchId;
}
