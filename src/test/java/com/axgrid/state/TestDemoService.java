package com.axgrid.state;

import com.axgrid.state.demo.AxDemoState;
import com.axgrid.state.demo.AxDemoStateService;
import com.axgrid.state.demo.AxDemoTurnTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Import(TestApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
@Slf4j
public class TestDemoService {

    @Autowired
    AxDemoStateService service;

    @Test
    public void testTransactionProcess() {

        AxDemoState state = service.create();
        Assert.assertNotNull(state.getId());
        state.getPlayers().add(Arrays.asList(10,20,30));
        state.getPlayers().add(Arrays.asList(40,50,60));
        service.save(state);
        log.info("{}", state);
        AxDemoTurnTransaction transaction = new AxDemoTurnTransaction(state.getId(), 1, 0);
        service.applyTransaction(transaction, null);
        AxDemoState state2 = service.get(state.getId());
        Assert.assertEquals(state.getId(), state2.getId());
        Assert.assertEquals(1, state2.getTable().size());
        Assert.assertEquals(40, (long)state2.getTable().get(0));
        log.info("{}", state2);
    }
}
