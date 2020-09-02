package com.axgrid.state.demo;

import com.axgrid.state.repository.AxStateConcurrentHashMapRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AxDemoStateRepository extends AxStateConcurrentHashMapRepository<AxDemoState> {  }
