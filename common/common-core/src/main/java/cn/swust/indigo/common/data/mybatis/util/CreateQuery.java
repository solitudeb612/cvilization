package cn.swust.indigo.common.data.mybatis.util;

import cn.hutool.core.util.ReflectUtil;
import cn.swust.indigo.common.core.util.CamelUtils;
import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CreateQuery {

    public static QueryWrapper getQueryWrapper(Object queryObj) {

        int len;
        String fieldName2;
        String first;
        Object endValue;
        Method endMethod;

        Class<?> clazz = queryObj.getClass();
        Field[] fields = clazz.getDeclaredFields();//获取类成员变量
        QueryWrapper queryWrapper = new QueryWrapper();
        for (Field field : fields) {//遍历

            DynamicQuery query = field.getAnnotation(DynamicQuery.class);
            if (query == null)
                continue;

            Object queryValue = ReflectUtil.getFieldValue(queryObj, field);

            if (queryValue != null) {
                String dbField = query.dbField();
                if (dbField.equals("")) {
                    dbField = CamelUtils.underscoreName(field.getName());
                }
                switch (query.value()) {
                    case eq:
                        queryWrapper.eq(dbField, queryValue);
                        break;
                    case ne:
                        queryWrapper.ne(dbField, queryValue);
                        break;
                    case gt:
                        queryWrapper.gt(dbField, queryValue);
                        break;
                    case ge:
                        queryWrapper.ge(dbField, queryValue);
                        break;
                    case lt:
                        queryWrapper.lt(dbField, queryValue);
                        break;
                    case le:
                        queryWrapper.le(dbField, queryValue);
                        break;
                    case like:
                        queryWrapper.like(dbField, queryValue);
                        break;
                    case notLike:
                        queryWrapper.notLike(dbField, queryValue);
                        break;
                    case between:
                        len = field.getName().length();
                        String temp = field.getName().substring(0, 1);
                        temp = temp.toUpperCase();
                        temp += field.getName().substring(1, field.getName().length());
                        fieldName2 = "get" + temp + "End";
                        endMethod = ReflectUtil.getMethod(queryObj.getClass(), fieldName2);
                        try {
                            endValue = endMethod.invoke(queryObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        if (endValue != null)
                            queryWrapper.between(dbField, queryValue, endValue);
                        break;

                    case notBetween:
                        len = field.getName().length();
                        temp = field.getName().substring(0, 1);
                        temp = temp.toUpperCase();
                        temp += field.getName().substring(1, field.getName().length());
                        fieldName2 = "get" + temp + "End";
                        endMethod = ReflectUtil.getMethod(queryObj.getClass(), fieldName2);
                        try {
                            endValue = endMethod.invoke(queryObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        if (endValue != null) {
                            queryWrapper.notBetween(dbField, queryValue, endValue);
                        }
                        break;
                    case likeLeft:
                        queryWrapper.likeLeft(dbField, queryValue);
                        break;
                    case likeRight:
                        queryWrapper.likeRight(dbField, queryValue);
                        break;
                    case isNull:
                        queryWrapper.isNull(dbField);
                        break;
                    case inNotNull:
                        queryWrapper.isNotNull(dbField);
                        break;
                    case in:
                        if (queryValue instanceof ArrayList)
                        {
                            ArrayList<Integer> list = (ArrayList<Integer>) queryValue;
                            queryWrapper.in(dbField,list);
                        }
                        else
                            queryWrapper.in(dbField,queryValue);
                        break;
                    case notIn:
                        if (queryValue instanceof ArrayList)
                        {
                            ArrayList<Integer> list = (ArrayList<Integer>) queryValue;
                            queryWrapper.notIn(dbField,list);
                        }
                        else
                            queryWrapper.notIn(dbField, queryValue);
                        break;
                    default:
                        break;
                }
            }
        }
        return queryWrapper;
    }
}
