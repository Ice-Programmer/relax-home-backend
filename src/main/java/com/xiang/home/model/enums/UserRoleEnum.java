package com.xiang.home.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User Roles Enum
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2024/9/19 16:17
 */
public enum UserRoleEnum {

    TENANT("tenant", "tenant"),
    LANDLORD("landlord", "landlord"),
    ADMIN("ADMIN", "admin"),
    BAN("BANNED", "ban");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * Obtain the list of values
     *
     * @return Values
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * Retrieve the enumeration based on the value
     *
     * @param value value
     * @return Enum
     */
    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
