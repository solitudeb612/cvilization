package cn.swust.indigo.common.core;

import cn.swust.indigo.common.core.response.NowResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Cent.Chen  2019-07-14
 * @Description Say something.
 */
@RestController
@RequestMapping("/date")
public class DateController {

    /**
     * 获取当前时间
     * @return
     */
    @GetMapping("/now")
    public NowResponse now() {
        return new NowResponse();
    }
}
