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
}