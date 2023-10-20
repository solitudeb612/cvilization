package cn.swust.indigo.common.core.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author Cent.Chen  2019-07-14
 * @Description Say something.
 */
@Data
public class NowResponse {

    /**
     * 当前时间(LocalDateTime)
     */
    private LocalDateTime localDateTime = LocalDateTime.now();
    /**
     * 当前时间(Date)
     */
    private Date date = new Date();
}
