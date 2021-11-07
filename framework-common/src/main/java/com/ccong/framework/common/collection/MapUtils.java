/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ccong.framework.common.collection;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

/**
 * Provides utility methods and decorators for
 * {@link Map} and {@link SortedMap} instances.
 * <p>
 * It contains various type safe methods
 * as well as other useful features like deep copying.
 * <p>
 *
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class MapUtils {

    /**
     * Null-safe check if the specified map is empty.
     * <p>
     * Null returns true.
     *
     * @param map the map to check, may be null
     * @return true if empty or null
     * @since 3.2
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     *
     * @param map the map to check, may be null
     * @return true if non-null and non-empty
     * @since 3.2
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !MapUtils.isEmpty(map);
    }

    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return Maps.newHashMap();
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return Maps.newHashMap(map);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return Maps.newConcurrentMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return Maps.newLinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int expectedSize) {
        return Maps.newLinkedHashMapWithExpectedSize(expectedSize);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return Maps.newLinkedHashMap(map);
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return Maps.newTreeMap();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return Maps.newTreeMap(map);
    }

    // Type safe getters
    //-------------------------------------------------------------------------
    /**
     * Gets from a Map in a null-safe manner.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map, <code>null</code> if null map input
     */
    public static Object getObject(final Map map, final Object key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * Gets a String from a Map in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a String, <code>null</code> if null map input
     */
    public static String getString(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    /**
     * Gets a Boolean from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Boolean, <code>null</code> if null map input
     */
    public static Boolean getBoolean(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;

                } else if (answer instanceof String) {
                    return new Boolean((String) answer);

                } else if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }

    /**
     * Gets a Number from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Number</code> it is returned directly.
     * If the value is a <code>String</code> it is converted using
     * {@link NumberFormat#parse(String)} on the system default formatter
     * returning <code>null</code> if the conversion fails.
     * Otherwise, <code>null</code> is returned.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Number, <code>null</code> if null map input
     */
    public static Number getNumber(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;

                } else if (answer instanceof String) {
                    try {
                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);

                    } catch (ParseException e) {
                        // failure means null is returned
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets a Byte from a Map in a null-safe manner.
     * <p>
     * The Byte is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Byte, <code>null</code> if null map input
     */
    public static Byte getByte(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return new Byte(answer.byteValue());
    }

    /**
     * Gets a Short from a Map in a null-safe manner.
     * <p>
     * The Short is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Short, <code>null</code> if null map input
     */
    public static Short getShort(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Short) {
            return (Short) answer;
        }
        return new Short(answer.shortValue());
    }

    /**
     * Gets a Integer from a Map in a null-safe manner.
     * <p>
     * The Integer is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Integer, <code>null</code> if null map input
     */
    public static Integer getInteger(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return new Integer(answer.intValue());
    }

    /**
     * Gets a Long from a Map in a null-safe manner.
     * <p>
     * The Long is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Long, <code>null</code> if null map input
     */
    public static Long getLong(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return new Long(answer.longValue());
    }

    /**
     * Gets a Float from a Map in a null-safe manner.
     * <p>
     * The Float is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Float, <code>null</code> if null map input
     */
    public static Float getFloat(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return new Float(answer.floatValue());
    }

    /**
     * Gets a Double from a Map in a null-safe manner.
     * <p>
     * The Double is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Double, <code>null</code> if null map input
     */
    public static Double getDouble(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return new Double(answer.doubleValue());
    }

    // Type safe getters with default values
    //-------------------------------------------------------------------------
    /**
     *  Looks up the given key in the given map, converting null into the
     *  given default value.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null
     *  @return  the value in the map, or defaultValue if the original value
     *    is null or the map is null
     */
    public static Object getObject( Map map, Object key, Object defaultValue ) {
        if ( map != null ) {
            Object answer = map.get( key );
            if ( answer != null ) {
                return answer;
            }
        }
        return defaultValue;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a string, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a string, or defaultValue if the
     *    original value is null, the map is null or the string conversion
     *    fails
     */
    public static String getString( Map map, Object key, String defaultValue ) {
        String answer = getString( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a boolean, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a boolean, or defaultValue if the
     *    original value is null, the map is null or the boolean conversion
     *    fails
     */
    public static Boolean getBoolean( Map map, Object key, Boolean defaultValue ) {
        Boolean answer = getBoolean( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a number, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Number getNumber( Map map, Object key, Number defaultValue ) {
        Number answer = getNumber( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a byte, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Byte getByte( Map map, Object key, Byte defaultValue ) {
        Byte answer = getByte( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a short, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Short getShort( Map map, Object key, Short defaultValue ) {
        Short answer = getShort( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  an integer, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Integer getInteger( Map map, Object key, Integer defaultValue ) {
        Integer answer = getInteger( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a long, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Long getLong( Map map, Object key, Long defaultValue ) {
        Long answer = getLong( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a float, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Float getFloat( Map map, Object key, Float defaultValue ) {
        Float answer = getFloat( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     *  Looks up the given key in the given map, converting the result into
     *  a double, using the default value if the the conversion fails.
     *
     *  @param map  the map whose value to look up
     *  @param key  the key of the value to look up in that map
     *  @param defaultValue  what to return if the value is null or if the
     *     conversion fails
     *  @return  the value in the map as a number, or defaultValue if the
     *    original value is null, the map is null or the number conversion
     *    fails
     */
    public static Double getDouble( Map map, Object key, Double defaultValue ) {
        Double answer = getDouble( map, key );
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

}