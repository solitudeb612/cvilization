package cn.swust.indigo.common.data.mybatis.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicQuery {
    QType value() default QType.eq;

    String dbField() default "";

    enum QType {
        /**
         *         等于 = eq, 不等于 <> ne, 大于 > gt, 大于等于 >= ge, 小于 < lt, 小于等于 <= le, LIKE '%值%' like,
         *         NOT LIKE '%值%' notLike,
         *          BETWEEN 值1 AND 值2 between, NOT BETWEEN 值1 AND 值2 notBetween,
         *          LIKE '%值' likeLeft, LIKE '值%' likeRight, 字段 IS NULL isNull, 字段 IS NOT NULL inNotNull,
         *         in, notIn, inSql, notInSql, groupBy, orderByAsc,
         *         orderByDesc
         */
        eq, ne, gt, ge, lt, le, like, notLike,
        between, notBetween,
        likeLeft, likeRight, isNull, inNotNull,
        in, notIn, inSql, notInSql, groupBy, orderByAsc,
        orderByDesc
    }//定义查询顺序

}
