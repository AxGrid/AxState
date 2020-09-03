package com.axgrid.state.jdbc;

import com.axgrid.cache.AxCacheObject;
import com.axgrid.cache.EnableAxCache;
import com.axgrid.state.EnableAxState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = "com.axgrid.state.jdbc")
@EnableScheduling
@EnableAxState
@EnableAxCache
public class AxStateJdbcConfiguration {

    public static final String STATE_CACHE ="state-id-cache";
    @Bean
    public AxCacheObject getAxStateCache(
            @Value("${ax.cache.state.expire:360}") int stateExpire,
            @Value("${ax.cache.state.size:100000}") int stateSize
    )
    {
        return AxCacheObject
                .builder()
                .configuration(new AxCacheObject.CacheObjectConfiguration(STATE_CACHE,
                        AxCacheObject.ExpireType.Access,
                        stateExpire,
                        stateSize))
                .build();
    }

}
