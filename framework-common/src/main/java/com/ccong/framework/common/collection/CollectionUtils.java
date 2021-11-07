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

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Provides utility methods and decorators for {@link Collection} instances.
 * <p>
 * Various utility methods might put the input objects into a Set/Map/Bag. In case
 * the input objects override {@link Object#equals(Object)}, it is mandatory that
 * the general contract of the {@link Object#hashCode()} method is maintained.
 * <p>
 * NOTE: From 4.0, method parameters will take {@link Iterable} objects when possible.
 *
 * @since 1.0
 */
public class CollectionUtils {

    /** Constant to avoid repeated object creation */
    private static Integer INTEGER_ONE = new Integer(1);

    public static boolean isEmpty(Iterable iterable) {
        return iterable == null || Iterables.isEmpty(iterable);
    }

    public static boolean isNotEmpty(Iterable iterable) {
        return !isEmpty(iterable);
    }

    public static <E> List<E> emptyList() {
        return Collections.emptyList();
    }

    public static <E> Set<E> emptySet() {
        return Collections.emptySet();
    }

    public static <E> ArrayList<E> newArrayList() {
        return Lists.newArrayList();
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Lists.newArrayList(elements);
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
        return Lists.newArrayListWithCapacity(initialArraySize);
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        return Lists.newLinkedList(elements);
    }

    private static final int getFreq(final Object obj, final Map freqMap) {
        Integer count = (Integer) freqMap.get(obj);
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }

    /**
     * Returns a {@link Map} mapping each unique element in the given
     * {@link Collection} to an {@link Integer} representing the number
     * of occurrences of that element in the {@link Collection}.
     * <p>
     * Only those elements present in the collection will appear as
     * keys in the map.
     *
     * @param coll  the collection to get the cardinality map for, must not be null
     * @return the populated cardinality map
     */
    public static Map getCardinalityMap(final Collection coll) {
        Map count = new HashMap();
        for (Iterator it = coll.iterator(); it.hasNext();) {
            Object obj = it.next();
            Integer c = (Integer) (count.get(obj));
            if (c == null) {
                count.put(obj,INTEGER_ONE);
            } else {
                count.put(obj,new Integer(c.intValue() + 1));
            }
        }
        return count;
    }

    /**
     * Returns <code>true</code> iff at least one element is in both collections.
     * <p>
     * In other words, this method returns <code>true</code> iff the
     * {@link #intersection} of <i>coll1</i> and <i>coll2</i> is not empty.
     *
     * @param coll1  the first collection, must not be null
     * @param coll2  the first collection, must not be null
     * @return <code>true</code> iff the intersection of the collections is non-empty
     * @since 2.1
     * @see #intersection
     */
    public static boolean containsAny(final Collection coll1, final Collection coll2) {
        if (coll1.size() < coll2.size()) {
            for (Iterator it = coll1.iterator(); it.hasNext();) {
                if (coll2.contains(it.next())) {
                    return true;
                }
            }
        } else {
            for (Iterator it = coll2.iterator(); it.hasNext();) {
                if (coll1.contains(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a {@link Collection} containing the union
     * of the given {@link Collection}s.
     * <p>
     * The cardinality of each element in the returned {@link Collection}
     * will be equal to the maximum of the cardinality of that element
     * in the two given {@link Collection}s.
     *
     * @param a  the first collection, must not be null
     * @param b  the second collection, must not be null
     * @return  the union of the two collections
     * @see Collection#addAll
     */
    public static Collection union(final Collection a, final Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            for(int i=0,m=Math.max(getFreq(obj,mapa),getFreq(obj,mapb));i<m;i++) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Returns a {@link Collection} containing the intersection
     * of the given {@link Collection}s.
     * <p>
     * The cardinality of each element in the returned {@link Collection}
     * will be equal to the minimum of the cardinality of that element
     * in the two given {@link Collection}s.
     *
     * @param a  the first collection, must not be null
     * @param b  the second collection, must not be null
     * @return the intersection of the two collections
     * @see Collection#retainAll
     * @see #containsAny
     */
    public static Collection intersection(final Collection a, final Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            for(int i=0,m=Math.min(getFreq(obj,mapa),getFreq(obj,mapb));i<m;i++) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Returns a {@link Collection} containing the exclusive disjunction
     * (symmetric difference) of the given {@link Collection}s.
     * <p>
     * The cardinality of each element <i>e</i> in the returned {@link Collection}
     * will be equal to
     * <tt>max(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>)) - min(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>))</tt>.
     * <p>
     * This is equivalent to
     * <tt>{@link #subtract subtract}({@link #union union(a,b)},{@link #intersection intersection(a,b)})</tt>
     * or
     * <tt>{@link #union union}({@link #subtract subtract(a,b)},{@link #subtract subtract(b,a)})</tt>.
     *
     * @param a  the first collection, must not be null
     * @param b  the second collection, must not be null
     * @return the symmetric difference of the two collections
     */
    public static Collection disjunction(final Collection a, final Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            for(int i=0,m=((Math.max(getFreq(obj,mapa),getFreq(obj,mapb)))-(Math.min(getFreq(obj,mapa),getFreq(obj,mapb))));i<m;i++) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Returns a new {@link Collection} containing <tt><i>a</i> - <i>b</i></tt>.
     * The cardinality of each element <i>e</i> in the returned {@link Collection}
     * will be the cardinality of <i>e</i> in <i>a</i> minus the cardinality
     * of <i>e</i> in <i>b</i>, or zero, whichever is greater.
     *
     * @param a  the collection to subtract from, must not be null
     * @param b  the collection to subtract, must not be null
     * @return a new collection with the results
     * @see Collection#removeAll
     */
    public static Collection subtract(final Collection a, final Collection b) {
        ArrayList list = new ArrayList( a );
        for (Iterator it = b.iterator(); it.hasNext();) {
            list.remove(it.next());
        }
        return list;
    }

    /**
     * Gets the size of the collection/iterator specified.
     * <p>
     * This method can handles objects as follows
     * <ul>
     * <li>Collection - the collection size
     * <li>Map - the map size
     * <li>Array - the array size
     * <li>Iterator - the number of elements remaining in the iterator
     * <li>Enumeration - the number of elements remaining in the enumeration
     * </ul>
     *
     * @param object  the object to get the size of
     * @return the size of the specified collection
     * @throws IllegalArgumentException thrown if object is not recognised or null
     * @since Commons Collections 3.1
     */
    public static int size(Object object) {
        int total = 0;
        if (object instanceof Map) {
            total = ((Map) object).size();
        } else if (object instanceof Collection) {
            total = ((Collection) object).size();
        } else if (object instanceof Object[]) {
            total = ((Object[]) object).length;
        } else if (object instanceof Iterator) {
            Iterator it = (Iterator) object;
            while (it.hasNext()) {
                total++;
                it.next();
            }
        } else if (object instanceof Enumeration) {
            Enumeration it = (Enumeration) object;
            while (it.hasMoreElements()) {
                total++;
                it.nextElement();
            }
        } else if (object == null) {
            throw new IllegalArgumentException("Unsupported object type: null");
        } else {
            try {
                total = Array.getLength(object);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
        return total;
    }


    //-----------------------------------------------------------------------
    /**
     * Adds an element to the collection unless the element is null.
     *
     * @param collection  the collection to add to, must not be null
     * @param object  the object to add, if null it will not be added
     * @return true if the collection changed
     * @throws NullPointerException if the collection is null
     * @since Commons Collections 3.2
     */
    public static boolean addIgnoreNull(Collection collection, Object object) {
        return (object == null ? false : collection.add(object));
    }

    /**
     * Adds all elements in the iteration to the given collection.
     *
     * @param collection  the collection to add to, must not be null
     * @param iterator  the iterator of elements to add, must not be null
     * @throws NullPointerException if the collection or iterator is null
     */
    public static void addAll(Collection collection, Iterator iterator) {
        while (iterator.hasNext()) {
            collection.add(iterator.next());
        }
    }

    /**
     * Adds all elements in the enumeration to the given collection.
     *
     * @param collection  the collection to add to, must not be null
     * @param enumeration  the enumeration of elements to add, must not be null
     * @throws NullPointerException if the collection or enumeration is null
     */
    public static void addAll(Collection collection, Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            collection.add(enumeration.nextElement());
        }
    }

    /**
     * Adds all elements in the array to the given collection.
     *
     * @param collection  the collection to add to, must not be null
     * @param elements  the array of elements to add, must not be null
     * @throws NullPointerException if the collection or array is null
     */
    public static void addAll(Collection collection, Object[] elements) {
        for (int i = 0, size = elements.length; i < size; i++) {
            collection.add(elements[i]);
        }
    }
}