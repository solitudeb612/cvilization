package cn.swust.indigo.mce.entity.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Binary cat
 * @date 2023/4/7 10:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QCommitDetailPage extends QCommitDetail{

    private Integer current;

    private Integer size;
}
