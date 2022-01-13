package com.${company_name}.${project_name}.adapter.outbound.persistence.${capital_upper_table_name?lower_case};

import com.${company_name}.${project_name}.domain.${capital_upper_table_name?lower_case}.${capital_upper_table_name}Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ${capital_upper_table_name}RepositoryImpl implements ${capital_upper_table_name}Repository {

    private final ${capital_upper_table_name}JpaRepository ${capital_upper_table_name?uncap_first}JpaRepository;

}

