package com.desperado.chapter16;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 自定义健康端点</p>
 *  * <p>功能更加强大一点，DataSourceHealthIndicator / RedisHealthIndicator 都是这种写法</p>
 *  第二种方式：继承AbstractHealthIndicator抽象类，重写doHealthCheck方法，功能比第一种要强大一点点，
 *  默认的DataSourceHealthIndicator 、 RedisHealthIndicator 都是这种写法，
 *  内容回调中还做了异常的处理。
 */
@Component("my2")
public class MyAbstractHealthIndicator extends AbstractHealthIndicator {
    private static final String VERSION = "v1.0.0";
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
            int code = check();
            if (code != 0) {
                builder.down().withDetail("code", code).withDetail("version", VERSION).build();
            }
            builder.withDetail("code", code)
                    .withDetail("version", VERSION).up().build();
        }

        private int check() {
            return 0;
        }
}
