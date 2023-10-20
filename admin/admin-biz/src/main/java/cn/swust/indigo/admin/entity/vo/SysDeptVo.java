package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.admin.entity.po.SysDept;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Binary cat
 * @date 2023/3/4 20:18
 */
@Data
public class SysDeptVo extends SysDept {

    @TableField(value = "nickname")
    private String nickname;
}
