package com.zw.srdz.author;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 授权认证注解类型<br>
 * <ul>
 * <li> 该注解类型可以用在方法签名或类的声明上面
 * <li> 如果一个类的声明上有该注解标识，则表示该类的所有方法都需要授权认证才可以访问
 * <li> 如果一个类的声明上没有该注解，该类的方法中只有声明了该注解的方法才会在调用前执行授权认证
 * <li> 如果方法和类都什么了该注解 以方法声明为准
 * @author cdzhangwei3
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Author {

	AuthorType[] type() default {AuthorType.LOGIN_USER};
}
