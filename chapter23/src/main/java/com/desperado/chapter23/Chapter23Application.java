package com.desperado.chapter23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 目前 Spring Boot 支持较好的两款工具分别是 flyway、liquibase，支持 sql script，在初始化数据源之后执行指定的脚本代码或者脚本文件，
 * 本章基于 Liquibase
 *
 * Liquibase
 * LiquiBase 是一个用于数据库重构和迁移的开源工具，通过 changelog文件 的形式记录数据库的变更，然后执行 changelog文件 中的修改，
 * 将数据库更新或回滚到一致的状态。
 *
 * 主要特点
 *
 * 支持几乎所有主流的数据库，如MySQL、PostgreSQL、Oracle、Sql Server、DB2等
 * 支持多开发者的协作维护；
 * 日志文件支持多种格式；如XML、YAML、SON、SQL等
 * 支持多种运行方式；如命令行、Spring 集成、Maven 插件、Gradle 插件等
 * 在平时开发中，无可避免测试库增加字段或者修改字段以及创建表之类的，环境切换的时候如果忘记修改数据库那么肯定会出现
 * 不可描述的事情 ，这个时候不妨考虑考虑Liquibase。
 */
@SpringBootApplication
public class Chapter23Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter23Application.class, args);
    }

}

