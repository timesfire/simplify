package com.simplify.adapter;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by shujian on 24/4/16.
 *
 * the base bean
 *
 */

public class BaseBean implements Serializable {


    public Object get(String key) {
        String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
        Object result = null;
        try {
            Method method = ((Object) this).getClass().getMethod(methodName, new Class[]{});
            result = method.invoke(this, new Object[]{});
        } catch (NoSuchMethodException e) {
            try {
                Field field = ((Object) this).getClass().getDeclaredField(key);
                field.setAccessible(true);
                result = field.get(this);
            } catch (NoSuchFieldException e1) {
                Log.i("Bae","Class" + ((Object) this).getClass().getName() + "need field：" + key +" or corresponding the get method");
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
