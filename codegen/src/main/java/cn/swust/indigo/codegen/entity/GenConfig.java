package cn.swust.indigo.codegen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor public class GenConfig {

    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;
}
