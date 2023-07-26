<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackage}.${modelName}.mapper.I${clsNamePrefix?cap_first}Mapper">
<#assign model = "${ clsNamePrefix?cap_first }Model"/>
    <!--代码生成器生成代码开始-->
<#if generateMethods?contains("C") >
    <insert id="insert"  parameterType="${model}">
        INSERT INTO ${tableName}(
        <#list insertColumns as column>
            <#if (column.defaultValue)??>
        <if test="${column.name}!=null">
           ${column.jdbcName},
        </if>
            <#else>
                ${column.jdbcName},
            </#if>
        </#list>
        del
        )
        VALUES(
        <#list insertColumns as column>
            <#if (column.defaultValue)??>
        <if test="${column.name}!=null">
               ${r'#'}{${column.name},jdbcType=${column.jdbcType}},
        </if>
            <#else>
                ${r'#'}{${column.name},jdbcType=${column.jdbcType}},</#if>
        </#list>0)
    </insert>
</#if>

<#if generateMethods?contains("B") >
    <insert id="batchInsert"  parameterType="${model}">
        INSERT INTO ${tableName}(
        <#list insertColumns as column>
            <#if (column.defaultValue)??>
        <if test="list[0].${column.name}!=null">
            ${column.jdbcName},
        </if>
            <#else>
                ${column.jdbcName},
            </#if>
        </#list>
        del
        )
        VALUES
        <foreach collection="list" item="model" open=" " separator="," close=" ">
            (
        <#list insertColumns as column>
            <#if (column.defaultValue)??>
        <if test="model.${column.name}!=null">
            ${r'#'}{model.${column.name},jdbcType=${column.jdbcType}},
        </if>
            <#else>
                ${r'#'}{model.${column.name},jdbcType=${column.jdbcType}},</#if>
        </#list>0)
        </foreach>
    </insert>
</#if>

<#if generateMethods?contains("U") >
    <update id="update"  parameterType="${model}">
        UPDATE ${tableName}
        SET
        <#list updateColumns as column>
        <if test="${column.name}!=null">
            ${column.jdbcName} = ${r'#'}{${column.name},jdbcType=${column.jdbcType}},
        </if>
        </#list>
        update_time = NOW()
        WHERE del=0 and ${tableDto.primaryKey.jdbcName}=${r'#'}{${tableDto.primaryKey.name},jdbcType=${tableDto.primaryKey.jdbcType}}
    </update>
</#if>

<#if generateMethods?contains("D") >
    <!--数据只做逻辑删除-->
    <delete id="delete" parameterType="${tableDto.primaryKey.javaType}">
        UPDATE ${tableName}
        SET
        del=1
        WHERE ${tableDto.primaryKey.jdbcName} in
        <foreach collection="array" item="item" index="index" open="(" separator="," close=")">
            ${r'#'}{item,jdbcType=${tableDto.primaryKey.jdbcType}}
        </foreach>

    </delete>
</#if>

<#if generateMethods?contains("R") >
    <#if associativeTableDto??>
        <#if associativeTableDto.relationalMappingType.type == 'one_to_one'>
            <#assign associativeTables=associativeTableDto.associativeTables/>
            <#include "sql/SqlMapperOneToOneTemplate.ftl">
        <#elseif associativeTableDto.relationalMappingType.type == 'one_to_more'>
            <#assign associativeTables=associativeTableDto.associativeTables/>
            <#include "sql/SqlMapperOneToMoreTemplate.ftl">
        <#else>
            <#include "sql/SqlMapperNoneTemplate.ftl"/>
        </#if>
    </#if>
</#if>
    <!--代码生成器生成代码结束-->
</mapper>
