package utils;

public final class ArrayUtils {

    private static final int DEFAULT_GROW_BY = 5;


    private ArrayUtils() {
    }


    public static Object[] create (Object[] array){

       return new Object[DEFAULT_GROW_BY];
    }

    private static Object[] resize (Object[] oldArray){

        if (oldArray == null){
            throw new IllegalArgumentException("Source array can't be null");
        }
        Object[] resizedArray = new Object[oldArray.length + DEFAULT_GROW_BY];

        for (int i = 0; i < oldArray.length; i++){

            resizedArray[i] = oldArray[i];
        }

        return resizedArray;
    }

    public static Object[] add(Object[] array, Object element) {
        if (array == null) {
            throw new IllegalArgumentException("Array can't be null");
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = element;
                return array;
            }
        }

        Object[] resizedArray = resize(array);
        resizedArray[array.length] = element;
        return resizedArray;
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

        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        array[array.length - 1] = null;

        return array;
    }

}
