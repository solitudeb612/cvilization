package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 微信搜索记录
 * </p>
 *
 * @author binary
 * @since 2023-05-23
 */
@TableName("search_record")
@ApiModel(value = "SearchRecord对象", description = "微信搜索记录")
public class SearchRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    @ApiModelProperty("搜索内容")
    private String recordContent;

    @ApiModelProperty("搜索框编号")
    private Integer searchId;

    @ApiModelProperty("关联用户id")
    private Integer userId;

    @ApiModelProperty("搜索次数")
    private Integer searchTimes;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSearchTimes() {
        return searchTimes;
    }

    public void setSearchTimes(Integer searchTimes) {
        this.searchTimes = searchTimes;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SearchRecord{" +
            "recordId = " + recordId +
            ", recordContent = " + recordContent +
            ", searchId = " + searchId +
            ", userId = " + userId +
            ", searchTimes = " + searchTimes +
            ", createTime = " + createTime +
        "}";
    }
}
