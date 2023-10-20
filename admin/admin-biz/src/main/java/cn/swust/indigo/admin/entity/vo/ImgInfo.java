package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.admin.entity.po.SysFile;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("返回图片信息")
@Data
public class ImgInfo extends SysFile {
    private String objectURL;
}
