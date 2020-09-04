package com.axgrid.state.queue;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AxQueueConfiguration.class})
public @interface EnableAxQueue {
}
