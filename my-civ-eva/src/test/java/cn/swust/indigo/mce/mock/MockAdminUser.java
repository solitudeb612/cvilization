package cn.swust.indigo.mce.mock;


import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface MockAdminUser {
    String username() default "admin";

    String name() default "admin";

    int id() default 1;

    int deptId() default 1;

}