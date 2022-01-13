package com.${company_name}.${project_name}.adapter.outbound.persistence.${capital_upper_table_name?lower_case};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ${capital_upper_table_name}JpaRepository extends JpaRepository<${capital_upper_table_name}PO, String>, QuerydslPredicateExecutor<${capital_upper_table_name}PO> {
}