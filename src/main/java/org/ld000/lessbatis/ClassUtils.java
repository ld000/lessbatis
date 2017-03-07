package org.ld000.lessbatis;

/**
 * @author lidong9144@163.com 17-3-7.
 */
public class ClassUtils {

    /**
     * 用于判断是否是 Proxy </br>
     * CGLIB 代理 判断字段 {@code $$EnhancerByCGLIB$$} </br>
     * JDK 代理 判断字段 {@code $Proxy}
     *
     * @param clazz {@link Class} 用来判断
     * @return {@code true} 如果是 Proxy
     */
    public static boolean isProxy(final Class<?> clazz) {
        if (clazz == null)
            return false;

        final String name = clazz.getName();

        return name.contains("$$EnhancerByCGLIB$$") || name.contains("$Proxy");
    }

    /**
     * 获取一个类的原始类，避免取得 {@code proxy}
     *
     * @param <T> 类名
     * @param clazz 要取得原始类的 {@link Class}
     * @return 原始类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getOriginalClass(final Class<T> clazz) {
        if (isProxy(clazz))
            return (Class<T>) clazz.getSuperclass();

        return clazz;
    }

}
