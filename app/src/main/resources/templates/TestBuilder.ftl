package com.${company_name}.${project_name}.adapter.inbound.rest.resources.${capital_upper_table_name?lower_case}.builder;

import com.${company_name}.${project_name}.adapter.outbound.persistence.${capital_upper_table_name?lower_case}.${capital_upper_table_name}JpaRepository;
import com.${company_name}.${project_name}.adapter.outbound.persistence.${capital_upper_table_name?lower_case}.${capital_upper_table_name}PO;
import com.${company_name}.${project_name}.common.utils.SpringApplicationContext;
import com.${company_name}.${project_name}.common.utils.UUIDUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ${capital_upper_table_name}TestBuilder {

    private final ${capital_upper_table_name}PO entity = new ${capital_upper_table_name}PO();

    public static ${capital_upper_table_name}TestBuilder withDefault(){
        return new ${capital_upper_table_name}TestBuilder()
        <#if model_column?exists>
        <#list model_column as model>
        <#if model.columnName = 'id'>
            .with${model.changeColumnName}(UUIDUtil.generateUUID())
        <#elseif model.columnType = 'VARCHAR' || model.columnType = 'TEXT' || model.columnType = 'DECIMAL'>
            .with${model.changeColumnName}("")
        <#elseif model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
            .with${model.changeColumnName}(LocalDateTime.now())
        <#elseif model.columnType = 'INT'>
            .with${model.changeColumnName}(1)
        <#else>
            .with${model.changeColumnName}("")</#if></#list>;
        </#if>
    }

        <#if model_column?exists>
        <#list model_column as model>
        <#if model.columnType = 'VARCHAR' || model.columnType = 'TEXT'>
    public ${capital_upper_table_name}TestBuilder with${model.changeColumnName}(String ${model.changeColumnName?uncap_first}){
        entity.set${model.changeColumnName}(${model.changeColumnName?uncap_first});
        return this;
    }
        <#elseif model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
    public ${capital_upper_table_name}TestBuilder with${model.changeColumnName}(LocalDateTime ${model.changeColumnName?uncap_first}){
        entity.set${model.changeColumnName}(${model.changeColumnName?uncap_first});
        return this;
    }
        <#elseif model.columnType = 'DECIMAL'>
    public ${capital_upper_table_name}TestBuilder with${model.changeColumnName}(String ${model.changeColumnName?uncap_first}){
        entity.set${model.changeColumnName}(new BigDecimal(${model.changeColumnName?uncap_first}));
        return this;
    }
        <#elseif model.columnType = 'INT'>
    public ${capital_upper_table_name}TestBuilder with${model.changeColumnName}(int ${model.changeColumnName?uncap_first}){
        entity.set${model.changeColumnName}(${model.changeColumnName?uncap_first});
        return this;
    }
        <#else>
    public ${capital_upper_table_name}TestBuilder with${model.changeColumnName}(String ${model.changeColumnName?uncap_first}){
        entity.set${model.changeColumnName}(${model.changeColumnName?uncap_first});
        return this;
    }
        </#if>
        </#list>
        </#if>

    public ${capital_upper_table_name}PO persist() {
        return SpringApplicationContext.getBean(${capital_upper_table_name}JpaRepository.class).saveAndFlush(entity);
    }

    public ${capital_upper_table_name}PO persist(${capital_upper_table_name}JpaRepository jpaRepo) {
        return jpaRepo.save(entity);
    }
}