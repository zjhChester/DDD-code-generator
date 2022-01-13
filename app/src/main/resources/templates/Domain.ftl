package com.${company_name}.${project_name}.domain.${capital_upper_table_name?lower_case};

import com.${company_name}.${project_name}.domain.core.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${capital_upper_table_name} implements AggregateRoot {
<#if model_column?exists>
    <#list model_column as model>
        <#if model.columnType = 'VARCHAR' || model.columnType = 'TEXT'>
    private String ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
    private LocalDateTime ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'DECIMAL' >
    private BigDecimal ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'INT'>
    private Integer ${model.changeColumnName?uncap_first};

        <#else>
    private String ${model.changeColumnName?uncap_first};

        </#if>
    </#list>
</#if>
}