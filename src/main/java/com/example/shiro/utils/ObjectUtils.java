package com.example.shiro.utils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rudolf.
 * @date 2019/8/13.
 */
public class ObjectUtils {

    /**
     * 判断数据是否不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return StrKit.isNotEmpty((String)obj) ? true : false;
            }
            if (obj instanceof Integer) {
                return (Integer)obj !=null ? true : false;
            }
            if (obj instanceof Date) {
                return (Date)obj !=null ? true : false;
            }
            if (obj instanceof Boolean) {
                return (Boolean)obj !=null ? true : false;
            }
            if (obj instanceof Long) {
                return (Long)obj !=null ? true : false;
            }
            if (obj instanceof BigDecimal) {
                return (BigDecimal)obj !=null ? true : false;
            }
            return true;
        }
        return false;
    }

    /**
     * 数据转Integer
     * @param obj
     * @return
     */
    public static Integer parseInt(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return StrKit.isNotEmpty(String.valueOf(obj)) ? Integer.parseInt(obj.toString()) : null;
            }
            if (obj instanceof Integer) {
                return obj != null ? (Integer) obj : null;
            }
        }
        return null;
    }

    /**
     * 数据转String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return StrKit.isNotEmpty(String.valueOf(obj)) ? (String) obj : null;
            }
            if (obj instanceof Long) {
                return obj != null ? obj.toString() : null;
            }
            if (obj instanceof Integer) {
                return obj != null ? obj.toString() : null;
            }
            if (obj instanceof BigDecimal) {
                return obj != null ? obj.toString() : null;
            }
            if (obj instanceof Date) {
                if (obj != null) {
                    String format = DateUtils.format((Date) obj, DateUtils.YYYY_MM_DD_HH_MM_SS);
                    return format;
                }else {
                    return null;
                }
            }
        }
        return null;
    }
}
