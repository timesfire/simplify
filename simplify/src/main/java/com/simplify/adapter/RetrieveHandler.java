package com.simplify.adapter;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by shujian on 6/7/16.
 */
public class RetrieveHandler {

    public static Object getValueFromKey(String key,Object object) {

        if(object instanceof Map){
           return  ((Map) object).get(key);
        }

        String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
        Object result = null;
        try {
            Method method = object.getClass().getMethod(methodName, new Class[]{});
            result = method.invoke(object, new Object[]{});
        } catch (NoSuchMethodException e) {
            try {
                Field field = object.getClass().getDeclaredField(key);
                field.setAccessible(true);
                result = field.get(object);
            } catch (NoSuchFieldException e1) {
                Log.i("Bae", "Class" + object.getClass().getName() + "need field：" + key + " or corresponding the get method");
                e.printStackTrace();
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
