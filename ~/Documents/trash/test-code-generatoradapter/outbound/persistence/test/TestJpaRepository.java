package com.faas.test.adapter.outbound.persistence.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TestJpaRepository extends JpaRepository<TestPO, String>, QuerydslPredicateExecutor<TestPO> {
}