package com.faas.test.adapter.outbound.persistence.test;

import com.faas.test.domain.test.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl implements TestRepository {

    private final TestJpaRepository testJpaRepository;

}

