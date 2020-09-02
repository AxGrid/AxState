package com.axgrid.state;

import com.axgrid.state.demo.AxDemoState;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class TestSerialization {

    @Test
    public void testEncodeDecode() throws Exception {
        AxDemoState demoState = new AxDemoState();
        demoState.setId(1L);
        demoState.getTable().add(500);
        String encode = demoState.encode();
        Assert.assertNotNull(encode);
        log.info("Json:{}", encode);
        AxDemoState demoState2 = (AxDemoState)AxDemoState.decode(encode, AxDemoState.class);
        Assert.assertEquals(demoState, demoState2);
        Assert.assertEquals(demoState.getTable().get(0), demoState2.getTable().get(0));

    }

}
