package com.ccong.framework.common.lang;

/**
 * Operations regarding the classpath.
 *
 * <p>The methods of this class do not allow {@code null} inputs.</p>
 *
 * @since 3.3
 */
//@Immutable
public class ClassPathUtils {

    /**
     * <p>{@code ClassPathUtils} instances should NOT be constructed in
     * standard programming. Instead, the class should be used as
     * {@code ClassPathUtils.toFullyQualifiedName(MyClass.class, "MyClass.properties");}.</p>
     *
     * <p>This constructor is public to permit tools that require a JavaBean
     * instance to operate.</p>
     */
    public ClassPathUtils() {
        super();
    }

    /**
     * Returns the fully qualified name for the resource with name {@code resourceName} relative to the given context.
     *
     * <p>Note that this method does not check whether the resource actually exists.
     * It only constructs the name.
     * Null inputs are not allowed.</p>
     *
     * <pre>
     * ClassPathUtils.toFullyQualifiedName(StringUtils.class, "StringUtils.properties") = "org.apache.commons.lang3.StringUtils.properties"
     * </pre>
     *
     * @param context The context for constructing the name.
     * @param resourceName the resource name to construct the fully qualified name for.
     * @return the fully qualified name of the resource with name {@code resourceName}.
     * @throws java.lang.NullPointerException if either {@code context} or {@code resourceName} is null.
     */
    public static String toFullyQualifiedName(final Class<?> context, final String resourceName) {
        Validate.notNull(context, "Parameter '%s' must not be null!", "context" );
        Validate.notNull(resourceName, "Parameter '%s' must not be null!", "resourceName");
        return toFullyQualifiedName(context.getPackage(), resourceName);
    }

    /**
     * Returns the fully qualified name for the resource with name {@code resourceName} relative to the given context.
     *
     * <p>Note that this method does not check whether the resource actually exists.
     * It only constructs the name.
     * Null inputs are not allowed.</p>
     *
     * <pre>
     * ClassPathUtils.toFullyQualifiedName(StringUtils.class.getPackage(), "StringUtils.properties") = "org.apache.commons.lang3.StringUtils.properties"
     * </pre>
     *
     * @param context The context for constructing the name.
     * @param resourceName the resource name to construct the fully qualified name for.
     * @return the fully qualified name of the resource with name {@code resourceName}.
     * @throws java.lang.NullPointerException if either {@code context} or {@code resourceName} is null.
     */
    public static String toFullyQualifiedName(final Package context, final String resourceName) {
        Validate.notNull(context, "Parameter '%s' must not be null!", "context" );
        Validate.notNull(resourceName, "Parameter '%s' must not be null!", "resourceName");
        return context.getName() + "." + resourceName;
    }

    /**
     * Returns the fully qualified path for the resource with name {@code resourceName} relative to the given context.
     *
     * <p>Note that this method does not check whether the resource actually exists.
     * It only constructs the path.
     * Null inputs are not allowed.</p>
     *
     * <pre>
     * ClassPathUtils.toFullyQualifiedPath(StringUtils.class, "StringUtils.properties") = "org/apache/commons/lang3/StringUtils.properties"
     * </pre>
     *
     * @param context The context for constructing the path.
     * @param resourceName the resource name to construct the fully qualified path for.
     * @return the fully qualified path of the resource with name {@code resourceName}.
     * @throws java.lang.NullPointerException if either {@code context} or {@code resourceName} is null.
     */
    public static String toFullyQualifiedPath(final Class<?> context, final String resourceName) {
        Validate.notNull(context, "Parameter '%s' must not be null!", "context" );
        Validate.notNull(resourceName, "Parameter '%s' must not be null!", "resourceName");
        return toFullyQualifiedPath(context.getPackage(), resourceName);
    }


    /**
     * Returns the fully qualified path for the resource with name {@code resourceName} relative to the given context.
     *
     * <p>Note that this method does not check whether the resource actually exists.
     * It only constructs the path.
     * Null inputs are not allowed.</p>
     *
     * <pre>
     * ClassPathUtils.toFullyQualifiedPath(StringUtils.class.getPackage(), "StringUtils.properties") = "org/apache/commons/lang3/StringUtils.properties"
     * </pre>
     *
     * @param context The context for constructing the path.
     * @param resourceName the resource name to construct the fully qualified path for.
     * @return the fully qualified path of the resource with name {@code resourceName}.
     * @throws java.lang.NullPointerException if either {@code context} or {@code resourceName} is null.
     */
    public static String toFullyQualifiedPath(final Package context, final String resourceName) {
        Validate.notNull(context, "Parameter '%s' must not be null!", "context" );
        Validate.notNull(resourceName, "Parameter '%s' must not be null!", "resourceName");
        return context.getName().replace('.', '/') + "/" + resourceName;
    }

}