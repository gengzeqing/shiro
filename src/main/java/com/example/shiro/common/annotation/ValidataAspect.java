package com.example.shiro.common.annotation;

import com.example.shiro.common.enums.RetCodeEnum;
import com.example.shiro.model.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Description: 参数验证接口
 * @Author: gengzeqing
 * @CreateDate: 2019/10/8 11:10
 * @Version: 1.0
 */
@Component
@Aspect
@Slf4j
public class ValidataAspect {

    /**
     * @param point
     * @return
     * @Around 标记为环绕通知
     * 1)JoinPoint
     *  java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；
     *  Signature getSignature() ：获取连接点的方法签名对象；
     *  java.lang.Object getTarget() ：获取连接点所在的目标对象； （通俗一点就是被代理的对象）
     *  java.lang.Object getThis() ：获取代理对象本身；
     * 2)ProceedingJoinPoint
     * ProceedingJoinPoint继承JoinPoint子接口，它新增了两个用于执行连接点方法的方法：
     *  java.lang.Object proceed() throws java.lang.Throwable：通过反射执行目标对象的连接点处的方法；
     *  java.lang.Object proceed(java.lang.Object[] args) throws java.lang.Throwable：通过反射执行目标对象连接点处的方法，不过使用新的入参替换原来的入参。
     */
    @Around(value = "@annotation(com.example.shiro.common.annotation.ValidateGroup)")
    public Object ValidataAround(ProceedingJoinPoint point) throws Throwable {
        Object obj;
        Object aThis = point.getThis();
        Object target = point.getTarget();
        // 获得方法名
        String name = point.getSignature().getName();
        Object[] args = point.getArgs();
        // 得到拦截的方法
        Method methodByClassAndName = getMethodByClassAndName(target.getClass(), name);
        ValidateGroup annotationByMethod = (ValidateGroup) getAnnotationByMethod(methodByClassAndName, ValidateGroup.class);
        Result result = validateFiled(annotationByMethod.fileds(), args);
        if (result.successed()) {
            obj = point.proceed();
        }else {
            obj = result;
        }
        return obj;
    }


    /**
     * 根据类和方法名得到方法
     */
    private Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 根据目标方法和注解类型  得到该目标方法的指定注解
     */
    private Annotation getAnnotationByMethod(Method method, Class annoClass) {
        // 获取所有的注解，包括自己声明的以及继承的
        Annotation all[] = method.getAnnotations();
        for (Annotation annotation : all) {
            if (annotation.annotationType() == annoClass) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 根据对象和属性名得到 属性
     */
    private Object getFieldByObjectAndFiledName(Object targetObj, String filedName) {
        Object returnVal = null;
        try {
            String getterMethod = getGetterNameByFiledName(filedName);
            Method method = targetObj.getClass().getMethod(getterMethod);
            returnVal = method.invoke(targetObj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return returnVal;
    }

    /**
     * 根据属性名 得到该属性的getter方法名
     */
    private String getGetterNameByFiledName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 验证参数
     *
     * @param valiedatefiles
     * @param args
     */
    private Result validateFiled(ValidateFiled[] valiedatefiles, Object[] args) {
        for (ValidateFiled valiedatefile : valiedatefiles) {
            int index = valiedatefile.index();
            Object arg = args[index];
            Object field = getFieldByObjectAndFiledName(arg, valiedatefile.filedName());
            boolean notNull = valiedatefile.notNull();
            if (notNull) {
                if (field instanceof String) {
                    if (((String) field) ==null || ((String) field).length() <=0) {
                        log.warn("valiedatefile.filedName() 数据为{}",field);
                        return Result.error("201",valiedatefile.message());
                    }
                }
            }
        }
        return Result.success();
    }

}
