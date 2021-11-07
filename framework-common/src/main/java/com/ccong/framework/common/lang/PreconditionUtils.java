package com.ccong.framework.common.lang;


import com.ccong.framework.common.collection.CollectionUtils;
import com.ccong.framework.common.collection.MapUtils;
import com.ccong.framework.common.exception.args.ArgumentIllegalException;

import java.util.Collection;
import java.util.Map;

public class PreconditionUtils {

    private PreconditionUtils() {

    }

    public static void notNull(Object value, String message) {
        if (value == null) {
            throw new ArgumentIllegalException(null, message);
        }
    }

    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new ArgumentIllegalException(false, message);
        }
    }

    public static void isFalse(boolean value, String message) {
        if (value) {
            throw new ArgumentIllegalException(false, message);
        }
    }

    public static void notEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new ArgumentIllegalException(value, message);
        }
    }

    public static void notBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw new ArgumentIllegalException(value, message);
        }
    }

    public static void notEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ArgumentIllegalException(collection, message);
        }
    }

    public static void notEmpty(Map map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new ArgumentIllegalException(map, message);
        }
    }

    public static <T> void notEmpty(T[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw new ArgumentIllegalException(array, message);
        }
    }

    public static void lessThan(Number number, Number compareTo, String message) {
        boolean isLesser = number != null && compareTo != null && number.doubleValue() < compareTo.doubleValue();
        if (!isLesser) {
            throw new ArgumentIllegalException(number, compareTo, message);
        }
    }

    public static void lessOrEqualsThan(Number number, Number compareTo, String message) {
        boolean isLessOrEq = number != null && compareTo != null && number.doubleValue() <= compareTo.doubleValue();
        if (!isLessOrEq) {
            throw new ArgumentIllegalException(number, compareTo, message);
        }
    }

    public static void greatThan(Number number, Number compareTo, String message) {
        boolean isGreater = number != null && compareTo != null && number.doubleValue() > compareTo.doubleValue();
        if (!isGreater) {
            throw new ArgumentIllegalException(number, compareTo, message);
        }
    }

    public static void greatOrEqualsThan(Number number, Number compareTo, String message) {
        boolean isGreaterOrEq = number != null && compareTo != null && number.doubleValue() >= compareTo.doubleValue();
        if (!isGreaterOrEq) {
            throw new ArgumentIllegalException(number, compareTo, message);
        }
    }

    public static void hasMoreElementThan(Collection collection, int compareTo, String message) {
        boolean hasMoreElement = collection != null && collection.size() > compareTo;
        if (!hasMoreElement) {
            throw new ArgumentIllegalException(collection, compareTo, message);
        }
    }

    public static void hasLessElementThan(Collection collection, int compareTo, String message) {
        boolean hasLessElement = collection != null && collection.size() < compareTo;
        if (!hasLessElement) {
            throw new ArgumentIllegalException(collection, compareTo, message);
        }
    }
}
