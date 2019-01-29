package com.desperado.chapter8.mapper;

import com.desperado.chapter8.entity.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * t_user 操作，继承 BaseMapper<T> 就可以了，是不是有点类似 JpaRepository
 */
@Mapper
public interface UserMapper  extends BaseMapper<User> {
    /**
     * 根据用户名统计（TODO 假设它是一个很复杂的SQL）
     *
     * @param username 用户名
     * @return 统计结果
     */
    int countByUsername(String username);
}
