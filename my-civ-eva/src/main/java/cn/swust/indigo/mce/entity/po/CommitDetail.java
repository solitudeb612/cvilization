package cn.swust.indigo.mce.entity.po;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目提交内容
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
@Data
@TableName("commit_detail")
@ApiModel(value = "网上申报提交内容")
public class CommitDetail {
    private static final long serialVersionUID = 20202L;

    /**
     * 项目提交名称
     */
    @TableId(value = "commit_id",type = IdType.AUTO)
    @ApiModelProperty(value = "提交id")
    private Integer commitId;

    /**
     * 任务id
     */
    @TableField("task_id")
    @ApiModelProperty(value = "任务id")
    private Integer taskId;

    /**
     * 材料名称
     */
    @TableField("name")
    @Size(min = 1, max = 255, message = "材料名称 最大为255")
    @ApiModelProperty(value = "材料名称 长度: 1-255")
    private String name;

    /**
     * 文本内容
     */
    @TableField("description")
    @Size(min = 1, max = 255, message = "文本内容 最大为255")
    @ApiModelProperty(value = "文本内容 长度: 1-255")
    private String Description;

    /**
     * 文件id
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "文件id")
    private List<Integer> fileIdList;

    public List<Integer> getFileIdList() {
        return JSONObject.parseArray(fileIds, Integer.class);
    }

    public String getFileIds() {
        return JSONObject.toJSONString(fileIdList);
    }

    /**
     * 图片文件id
     */
    @TableField("file_ids")
    private String fileIds;

    /**
     * 1.图片，2.文本，3.附件
     */
    @ApiModelProperty(value = "1.图片，2，文本，3附件")
    @TableField("type")
    private Integer type;

    /**
     * 所属分组Id：用于区分是否属于同一次提交
     */
    @ApiModelProperty(value = "所属分组Id：用于区分是否属于同一次提交")
    @TableField("report_id")
    private Integer reportId;

    /**
     * 对应一个任务 task 的一个子项 比如提交两个文件，其中存在两个此id
     */
    @ApiModelProperty(value = "对应一个任务 task 的一个子项 比如提交两个文件，其中存在两个此id")
    @TableField("task_commit_id")
    private Integer taskCommitId;

}
