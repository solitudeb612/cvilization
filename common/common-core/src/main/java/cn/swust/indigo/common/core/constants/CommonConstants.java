package cn.swust.indigo.common.core.constants;


/**
 * 通用常量
 */
public interface CommonConstants {
    /**
     * 成功标记
     */
    Integer SUCCESS = 0;
    /**
     * 失败标记
     */
    Integer FAIL = 1;

    String BUCKET_NAME = "myciv";

    /**
     * 正常
     */
    String STATUS_NORMAL = "1";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";
    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 菜单树根节点
     */
    Integer MENU_TREE_ROOT_ID = -1;
    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "MY_CIV_CAPTCHA_";


}
