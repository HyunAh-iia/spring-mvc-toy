package my.study.springmvc.testconfig.utils;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

public class ReflectionField {
    AtomicLong counter = new AtomicLong(0);

    private static Field setFieldValue(Object obj, String fieldName, Object valueTobeSet) throws IllegalAccessException, NoSuchFieldException {
        Field field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        field.set(obj, valueTobeSet);
        return field;
    }

    private static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    protected void autoIncrement(Object obj) {
        try {
            setFieldValue(obj, "id", counter.incrementAndGet());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
