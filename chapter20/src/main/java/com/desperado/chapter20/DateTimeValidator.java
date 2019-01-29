package com.desperado.chapter20;

import com.desperado.chapter20.annotation.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 定义校验器类 DateTimeValidator 实现 ConstraintValidator 接口，实现接口后需要实现它里面的 initialize： 与 isValid： 方法。
 */
public class DateTimeValidator implements ConstraintValidator<DateTime,String> {

    private DateTime dateTime;

    /**
     * 主要用于初始化，它可以获得当前注解的所有属性
     * @param constraintAnnotation
     */
    @Override
    public void initialize(DateTime constraintAnnotation) {
        this.dateTime = constraintAnnotation;
    }

    /**
     * 进行约束验证的主体方法，其中 value 就是验证参数的具体实例，context 代表约束执行的上下文环境。
     * @param value
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
        if (value == null) {
            return true;
        }
        String format = dateTime.format();
        if (value.length() != format.length()) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            simpleDateFormat.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
