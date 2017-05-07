package ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangjingyun on 2017/5/7.
 */

@Target({ElementType.METHOD})  //方法上的 标志
@Retention(RetentionPolicy.RUNTIME)// 运行时 注解
public @interface OnClick {

    int [] value();
}
