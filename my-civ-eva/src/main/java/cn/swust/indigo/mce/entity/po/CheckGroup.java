package cn.swust.indigo.mce.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName check_group
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckGroup implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer checkGroupId;

    /**
     * 组名
     */
    private String groupName;


    /**
     * 组名拼音
     */
    private String groupPy;

    /**
     * 检测方法
     */
    @TableField(value = "check_method")
    private String checkMethod;

    /**
     * 说明
     */
    private String memo;

    private static final long serialVersionUID = 1L;
}