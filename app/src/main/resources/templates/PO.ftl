package com.${company_name}.${project_name}.adapter.outbound.persistence.${capital_upper_table_name?lower_case};

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="${lower_table_name_with_underscore}")
public class ${capital_upper_table_name}PO {
<#if model_column?exists>
    <#list model_column as model>
        <#if (model.columnName = 'id')>
    @Id
    private String ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'VARCHAR' || model.columnType = 'TEXT'>
    @Column(name = "${model.columnName}",columnDefinition = "VARCHAR")
    private String ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
    @Column(name = "${model.columnName}",columnDefinition = "TIMESTAMP")
    private LocalDateTime ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'DECIMAL'>
    @Column(name = "${model.columnName}",columnDefinition = "DECIMAL")
    private BigDecimal ${model.changeColumnName?uncap_first};

        <#elseif model.columnType = 'INT'>
    @Column(name = "${model.columnName}",columnDefinition = "INT")
    private Integer ${model.changeColumnName?uncap_first};

        <#else>
    @Column(name = "${model.columnName}",columnDefinition = "VARCHAR")
    private String ${model.changeColumnName?uncap_first};

        </#if>
    </#list>
</#if>
}