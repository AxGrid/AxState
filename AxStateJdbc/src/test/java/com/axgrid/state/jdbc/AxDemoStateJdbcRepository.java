package com.axgrid.state.jdbc;

import com.axgrid.state.jdbc.repository.AxStateJdbcRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class AxDemoStateJdbcRepository extends AxStateJdbcRepository<AxDemo2State> {
}
