package com.desperado.chapter20.pojo;

import com.desperado.chapter20.group.Groups;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @NotNull 限制必须不为null
 * @NotEmpty 验证注解的元素值不为 null 且不为空（字符串长度不为0、集合大小不为0）
 * @NotBlank 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
 * @Pattern(value) 限制必须符合指定的正则表达式
 * @Size(max,min) 限制字符长度必须在 min 到 max 之间（也可以用在集合上）
 * @Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
 * @Max(value) 限制必须为一个不大于指定值的数字
 * @Min(value) 限制必须为一个不小于指定值的数字
 * @DecimalMax(value) 限制必须为一个不大于指定值的数字
 * @DecimalMin(value) 限制必须为一个不小于指定值的数字
 * @Null 限制只能为null（很少用）
 * @AssertFalse 限制必须为false （很少用）
 * @AssertTrue 限制必须为true （很少用）
 * @Past 限制必须是一个过去的日期
 * @Future 限制必须是一个将来的日期
 * @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过 integer，小数部分的位数不能超过 fraction （很少用）
 */
public class Book {

    //groups 属性的作用就让 @Validated 注解只验证与自身 value 属性相匹配的字段，可多个，只要满足就会去纳入验证范围；
    // 我们都知道针对新增的数据我们并不需要验证 ID 是否存在，我们只在做修改操作的时候需要用到，因此这里将 ID 字段归纳到 Groups.Update.class 中去，
    // 而其它字段是不论新增还是修改都需要用到所以归纳到 Groups.Default.class 中…
    @NotNull(message = "id 不能为空",groups = Groups.Update.class)
    private Integer id;

    @NotBlank(message = "name 不能为空",groups = Groups.Default.class)
    @Length(min = 2, max = 10, message = "name 长度必须在 {min}-{max} 之间")
    private String name;

    @NotNull(message = "price 不能为空",groups = Groups.Default.class)
    @DecimalMin(value = "0.2", message ="价格不能低于  {value}" )
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
