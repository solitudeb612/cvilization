package cn.swust.indigo.mce.entity.po;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (CheckProblem)表实体类
 *
 * @author makejava
 * @since 2023-03-25 16:03:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("check_problem")
public class CheckProblem  {
    //问题id@TableId
    @TableId(value = "check_problem_id", type = IdType.AUTO)
    private Integer checkProblemId;
    //检测问题Id
    private Integer checkCommitId;
    //问题类型
    private Integer problemType;

    @TableField(value = "name")
    private String name;

    @TableField(exist = false)
    private String creatorName;

    //问题描述
    private String description;
    //上报人id
    private Integer creatorId;
    //上报时间
    private Date createTime;

    @TableField(exist = false)
    private List<Integer> fileIdsList;

    public List<Integer> getFileIdsList() {
        return JSONObject.parseArray(fileIds, Integer.class);
    }


    public String getFileIds() {
        return JSONObject.toJSONString(fileIdsList);
    }

    //提交证明图片
    private String fileIds;
    //维修人
    private String repairerName;
    //维修修时间
    private Date repairedTime;

    //维修记录时间
    private Date repairerTime;
    //地址
    private String address;
    //提交设备
    private String commitDevice;

    @TableField(exist = false)
    private Integer checkReportId;
}
