package com.desperado.chapter6.repository;

import com.desperado.chapter6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建UserRepository数据访问层接口，需要继承JpaRepository<T,K>，第一个泛型参数是实体对象的名称，第二个是主键类型。只需要这样简单的配置，该UserRepository就拥常用的CRUD功能，JpaRepository本身就包含了常用功能，剩下的查询我们按照规范写接口即可，JPA支持@Query注解写HQL，也支持findAllByUsername这种根据字段名命名的方式（强烈推荐IntelliJ IDEA对JPA支持非常NICE）
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 查询结果
     */
    List<User> findAllByUsername(String username);
}
