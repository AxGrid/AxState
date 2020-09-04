package com.axgrid.state.join;

import com.axgrid.state.AxStateConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AxStateConfiguration.class})
public @interface EnableAxStateJoin {
}
