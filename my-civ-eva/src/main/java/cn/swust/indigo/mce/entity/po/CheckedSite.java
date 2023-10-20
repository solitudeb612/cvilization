package cn.swust.indigo.mce.entity.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Binary cat
 * @date 2023/3/7 16:42
 */
@ApiModel("附加选择属性的Site")
@Data
public class CheckedSite extends Site {
    private boolean checked = false;
}
