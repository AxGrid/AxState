package com.axgrid.state.queue;


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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Import(TestApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
@Slf4j
public class QueueTest {

    @Autowired
    AxQueueCustomService messageService;

    @Test
    public void testQueuePutAndGet() {

        AxQueueCustomMessage message = new AxQueueCustomMessage();
        message.setOwner(0);
        message.setPlayerName("Player" + message.getOwner());
        message.setMessage("Hello world!");

        AxQueueContext ctx = new AxQueueContext();
        ctx.setUser(0);

        message = messageService.put(ctx, message);
        Assert.assertNotEquals(0, message.getId());

        List<AxQueueCustomMessage> messages = messageService.get(ctx, 0);
        Assert.assertEquals(messages.size(), 1);
        AxQueueCustomMessage receivedMessage = messages.get(0);
        log.info("{}", receivedMessage);
        Assert.assertEquals(receivedMessage.getPlayerName(), message.getPlayerName());
        Assert.assertEquals(receivedMessage.getMessage(), message.getMessage());

        ctx.setUser(1);
        messages = messageService.get(ctx, 0);
        Assert.assertEquals(messages.size(), 1);
        receivedMessage = messages.get(0);
        log.info("{}", receivedMessage);
        Assert.assertEquals(receivedMessage.getPlayerName(), message.getPlayerName());
        Assert.assertNotEquals(receivedMessage.getMessage(), message.getMessage());


        ctx.setUser(101);
        messages = messageService.get(ctx, 0);
        Assert.assertEquals(messages.size(), 0);

    }

}
