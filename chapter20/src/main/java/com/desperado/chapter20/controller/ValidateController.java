package com.desperado.chapter20.controller;

import com.desperado.chapter20.annotation.DateTime;
import com.desperado.chapter20.group.Groups;
import com.desperado.chapter20.pojo.Book;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 参数校验
 * @Validated： 开启数据有效性校验，添加在类上即为验证方法，添加在方法参数中即为验证参数对象。（添加在方法上无效）
 * @NotBlank： 被注释的字符串不允许为空（value.trim() > 0 ? true : false）
 * @Length： 被注释的字符串的大小必须在指定的范围内
 * @NotNull： 被注释的字段不允许为空(value ! = null ? true : false)
 * @DecimalMin： 被注释的字段必须大于或等于指定的数值
 */
@Validated
@RestController
public class ValidateController {

    /**
     * 参数校验
     * @param name
     * @return
     */
    @GetMapping("/test2")
    public String test2(@NotBlank(message = "name 不能为空") @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间") String name) {
        return "success";
    }

    /**
     * 对象校验
     * @param book
     * @return
     */
    @GetMapping("/test3")
    public String test3(@Validated Book book) {
        return "success";
    }

    @GetMapping("/test")
    public String test(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") String date) {
        return "success";
    }

    /**
     * 创建一个 ValidateController 类，然后定义好 insert、update 俩个方法，比由于 insert 方法并不关心 ID 字段，
     * 所以这里 @Validated 的 value 属性写成 Groups.Default.class 就可以了；而 update 方法需要去验证 ID 是否为空，
     * 所以此处 @Validated 注解的 value 属性值就要写成 Groups.Default.class, Groups.Update.class；
     * 代表只要是这分组下的都需要进行数据有效性校验操作…
     * @param book
     * @return
     */
    @GetMapping("/insert")
    public String insert(@Validated(value = Groups.Default.class) Book book) {
        return "insert";
    }


    @GetMapping("/update")
    public String update(@Validated(value = {Groups.Default.class, Groups.Update.class}) Book book) {
        return "update";
    }
}
