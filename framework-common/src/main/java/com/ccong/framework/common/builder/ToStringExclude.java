package com.ccong.framework.common.builder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation to exclude a field from being being used by
 * the {@link ReflectionToStringBuilder}.
 *
 * @since 3.5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ToStringExclude {

}