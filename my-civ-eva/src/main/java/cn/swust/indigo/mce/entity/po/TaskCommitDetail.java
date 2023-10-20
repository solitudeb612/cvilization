package cn.swust.indigo.mce.entity.po;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 项目提交内容
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
@Data
@ApiModel(value = "网上申报提交内容")
public class TaskCommitDetail extends TaskCommit{
    private static final long serialVersionUID = 20202L;

    /**
     * 项目提交id
     */
    @TableId(value = "commit_id",type = IdType.AUTO)
    @ApiModelProperty(value = "提交id")
    private Integer commitId;

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
}
