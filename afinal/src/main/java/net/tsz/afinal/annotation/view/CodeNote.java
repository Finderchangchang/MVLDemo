package net.tsz.afinal.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 代码注解
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeNote {
    int id();

    String hint() default "";

    String text() default "";

    String click() default "";

    String longClick() default "";

    String itemClick() default "";

    String itemLongClick() default "";

    Select select() default @Select(
            selected = ""
    );
}
