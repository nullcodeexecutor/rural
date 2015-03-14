package org.rural.utils;

/**
 * Created by yuantao on 2014/8/9.
 */
public class TypeUtil {
    private TypeUtil(){
    }

    public static Object typeCast(Class<?> clazz, String value) throws NumberFormatException{
        String typeName = clazz.getTypeName();
        if(typeName.equals(boolean.class.getName()) || typeName.equals(Boolean.class.getName())){
            return Boolean.parseBoolean(value);
        }
        if(typeName.equals(char.class.getName()) || typeName.equals(Character.class.getName())){
            return (Character)value.charAt(0);
        }
        if(typeName.equals(byte.class.getName()) || typeName.equals(Byte.class.getName())){
            return Byte.parseByte(value);
        }
        if(typeName.equals(short.class.getName()) || typeName.equals(Short.class.getName())){
            return Short.parseShort(value);
        }
        if(typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName())){
            return Integer.parseInt(value);
        }
        if(typeName.equals(long.class.getName()) || typeName.equals(Long.class.getName())){
            return Long.parseLong(value);
        }
        if(typeName.equals(float.class.getName()) || typeName.equals(Float.class.getName())){
            return Float.parseFloat(value);
        }
        if(typeName.equals(double.class.getName()) || typeName.equals(Double.class.getName())){
            return Double.parseDouble(value);
        }
        if(typeName.equals(String.class.getName())){
            return value;
        }
        return null;
    }

}
