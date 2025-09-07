package utils;

public final class ArrayUtils {

    private ArrayUtils() {
    }

    public static Object[] add(Object[] array, Object element) {
        if (array == null) {
            throw new IllegalArgumentException("Array can't be null");
        }
        if (element == null) {
            throw new IllegalArgumentException("Element can't be null");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = element;
                return array;
            }
        }
        int newSize = array.length + 1;
        Object[] newArray = (Object[]) java.lang.reflect.Array.newInstance(
                array.getClass().getComponentType(),
                newSize
        );
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = element;
        return newArray;
    }

    public static Object[] delete(Object[] array, Object element) {
        if (array == null) {
            throw new IllegalArgumentException("Array can't be null");
        }
        if (element == null) {
            throw new IllegalArgumentException("Element can't be null");
        }
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return array;
        }
        Object[] newArray = (Object[]) java.lang.reflect.Array.newInstance(
                array.getClass().getComponentType(),
                array.length - 1
        );
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                newArray[j++] = array[i];
            }
        }

        return newArray;
    }

    public static Object getById(Object[] array, long id) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        for (Object element : array) {
            if (element != null) {
                try {
                    java.lang.reflect.Field idField = element.getClass().getDeclaredField("id");
                    idField.setAccessible(true);
                    Long elementId = (Long) idField.get(element);
                    if (elementId != null && elementId == id) {
                        return element;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    System.err.println("no such element found in array");
                }
            }
        }
        return null;
    }
}
