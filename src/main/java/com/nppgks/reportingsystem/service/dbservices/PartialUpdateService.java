package com.nppgks.reportingsystem.service.dbservices;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class PartialUpdateService {

    public static <T> T updateObject(T object, Map<String, String> updates) {
        Field[] declaredFields = object.getClass().getDeclaredFields();

        Arrays.stream(declaredFields).forEach(
                df -> {
                    String fieldName = df.getName();
                    if (updates.containsKey(fieldName)) {
                        df.setAccessible(true);
                        String newFieldValue = updates.get(fieldName);
                        try {
                            Class<?> dfType = df.getType();
                            if(dfType.equals(String.class)){
                                df.set(object, newFieldValue);
                            }
                            else if(dfType.equals(Integer.class)){
                                df.set(object, Integer.parseInt(newFieldValue));
                            } else if (dfType.equals(Boolean.class)) {
                                df.set(object, Boolean.parseBoolean(newFieldValue));
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Значение поля " + fieldName + " не может быть установлено на " + newFieldValue);
                        }
                    }
                }
        );
        return object;
    }
}
