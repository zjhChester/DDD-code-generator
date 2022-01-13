package com.faas.test.adapter.inbound.rest.resources.test.builder;

import com.faas.test.adapter.outbound.persistence.test.TestJpaRepository;
import com.faas.test.adapter.outbound.persistence.test.TestPO;
import com.faas.test.common.utils.SpringApplicationContext;
import com.faas.test.common.utils.UUIDUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestTestBuilder {

    private final TestPO entity = new TestPO();

    public static TestTestBuilder withDefault(){
        return new TestTestBuilder()
;
    }


    public TestPO persist() {
        return SpringApplicationContext.getBean(TestJpaRepository.class).saveAndFlush(entity);
    }
}