package cn.swust.indigo.codegen.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @date 2018-07-30
 */
public interface SysGeneratorMapper {

    /**
     * 查询表列信息
     *
     * @param tableName 表名称
     * @return
     */
    @Select("select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, "
            + "extra ,is_nullable as isNullable,column_type as columnType"
            + ", CHARACTER_MAXIMUM_LENGTH as size from information_schema.columns"
            + " where table_name = #{tableName} order by ordinal_position") List<Map<String, String>> queryColumns(
            String tableName);
}
