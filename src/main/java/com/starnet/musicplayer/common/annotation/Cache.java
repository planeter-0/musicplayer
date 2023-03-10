package com.starnet.musicplayer.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 代替Cacheable, 简单应对缓存雪崩穿透和击穿
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * 命名空间
     */
    String value();
    /**
     * springEL表达式
     */
    String key();
}
