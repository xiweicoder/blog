package com.lfs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//运行阶段通过反射看到
@Retention(RetentionPolicy.RUNTIME)
//可以加在那些上面(方法)
@Target({ElementType.METHOD})
//这是一个自定义的注解
public @interface SystemLog {
    String BusinessName();

}
