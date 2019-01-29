package com.desperado.chapter11.controller;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.chapter11.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * swagger 注解说明：
 * @Api： 描述Controller
 * @ApiIgnore： 忽略该Controller，指不对当前类做扫描
 * @ApiOperation： 描述Controller类中的method接口
 * @ApiParam： 单个参数描述，与@ApiImplicitParam不同的是，他是写在参数左侧的。如（@ApiParam(name = "username",value = "用户名") String username）
 * @ApiModel： 描述POJO对象
 * @ApiProperty： 描述POJO对象中的属性值
 * @ApiImplicitParam： 描述单个入参信息
 * @ApiImplicitParams： 描述多个入参信息
 * @ApiResponse： 描述单个出参信息
 * @ApiResponses： 描述多个出参信息
 * @ApiError： 接口错误所返回的信息
 */
@RestController
@RequestMapping("/users")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @ApiOperation(value = "条件查询(DONE)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType =ApiDataType.STRING ,paramType = ApiParamType.QUERY),
            @ApiImplicitParam(name = "password", value = "密码", dataType =ApiDataType.STRING ,paramType = ApiParamType.QUERY),
    })
    public User query(String username, String password){
        log.info("多个参数用  @ApiImplicitParams");
        return new User(1L,"u1","p1");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH),
    })
    public User get(@PathVariable Long id) {
        log.info("单个参数用  @ApiImplicitParam");
        return new User(id, "u1", "p1");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH)
    public void delete(@PathVariable Long id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }
}
