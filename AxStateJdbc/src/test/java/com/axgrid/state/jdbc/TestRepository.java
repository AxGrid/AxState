package com.axgrid.state.jdbc;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Import(TestApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
@Slf4j
public class TestRepository {

    @Autowired
    AxDemoStateJdbcRepository demoStateJdbcRepository;

    @Test
    public void testCache() {
        AxDemo2State ds = new AxDemo2State();
        ds.test = 15;
        ds = demoStateJdbcRepository.save(ds);
        Assert.assertNotNull(ds.getId());
        Assert.assertNotEquals((long)ds.getId(), 0);

        AxDemo2State ds2 = demoStateJdbcRepository.get(ds.getId());
        Assert.assertEquals(ds, ds2);
        Assert.assertEquals(ds.getId(), ds2.getId());
        Assert.assertEquals(ds.getTest(), ds2.getTest());
        ds2 = demoStateJdbcRepository.get(ds.getId());
    }
}
