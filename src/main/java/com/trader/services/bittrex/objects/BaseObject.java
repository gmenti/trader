package com.trader.services.bittrex.objects;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

abstract class BaseObject {
    private static final SimpleDateFormat dateFormmatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    BaseObject(Map<String, Object> map) {
        Class<BaseObject> self = (Class<BaseObject>) this.getClass();

        map.forEach((String fieldName, Object value) -> {
            String fieldNameToTitleCase = StringUtils.uncapitalize(fieldName);

            try {
                Field field = self.getDeclaredField(fieldNameToTitleCase);

                if (field.getType() == Date.class) {
                    field.set(this, dateFormmatter.parse((String) value));
                } else {
                    field.set(this, (field.getType()).cast(value));
                }
            } catch (NoSuchFieldException | IllegalAccessException | ParseException e) {
                // invalid field
            }
        });
    }
}