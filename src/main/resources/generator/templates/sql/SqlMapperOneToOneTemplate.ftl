<#-- 一对一映射关系，使用内嵌关联，内嵌关联时，指定两张关联表的别名，避免字段相同重名 -->
    <resultMap id="${clsNamePrefix?uncap_first}OneToOneMap" type="${clsNamePrefix?cap_first}Model">
        <id property="${tableDto.primaryKey.name}" column="a_${tableDto.primaryKey.jdbcName}" javaType="${tableDto.primaryKey.javaType}" jdbcType="${tableDto.primaryKey.jdbcType}"/>
        <#list selectColumns as column>
            <#if column.jdbcName!=tableDto.primaryKey.jdbcName&&(column.jdbcName!='del')>
        <result property="${column.name}" column="a_${column.jdbcName}" javaType="${column.javaType}" jdbcType="${column.jdbcType}"/>
            </#if>
        </#list>
    <#-- 生成一对一映射关联信息 -->
        <#list associativeTables as associativeTable>
        <association property="${ associativeTable.clsNamePrefix?uncap_first }Model" javaType="${ associativeTable.clsNamePrefix?cap_first }Model">
            <id property="${associativeTable.primaryKey.name}" column="b_${associativeTable.primaryKey.jdbcName}" javaType="${associativeTable.primaryKey.javaType}" jdbcType="${associativeTable.primaryKey.jdbcType}"/>
            <#list associativeTable.columns as column>
                <#if column.jdbcName!=associativeTable.primaryKey.jdbcName&&(column.jdbcName!='del')>
            <result property="${column.name}" column="b_${column.jdbcName}" javaType="${column.javaType}" jdbcType="${column.jdbcType}"/>
                </#if>
            </#list>
        </association>
        <#-- 声明一个变量，一对一映射关系，所以设置第一张表数据信息到变量中。同时跳出循环 -->
            <#assign associativeTable=associativeTable/>
        <#break/>
        </#list>
    </resultMap>

    <sql id="select${clsNamePrefix?cap_first}">
             <#list selectColumns as column>
                 a.${column.jdbcName} as a_${column.jdbcName},
             </#list>
             <#list associativeTable.columns as column>
                 b.${column.jdbcName} as b_${column.jdbcName} <#if column_has_next>,</#if>
             </#list>
    </sql>
    <!--查询-->
    <select id="query" parameterType="${clsNamePrefix?cap_first}Model" resultMap="${clsNamePrefix?uncap_first}OneToOneMap">
        SELECT
        <include refid="select${clsNamePrefix?cap_first}"></include>
        FROM ${tableName} a,${associativeTable.name} b  WHERE a.${tableDto.primaryKey.jdbcName} = b.${tableDto.primaryKey.jdbcName} and a.del=0 and b.del=0
        <#list selectColumns as column>
            <#if column.jdbcName!='del'>
        <if test="${column.name}!=null">
            and a.${column.jdbcName} = ${r'#'}{${column.name},jdbcType=${column.jdbcType}}
        </if>
            </#if>
        </#list>
        <#list associativeTable.columns as column>
            <#if column.jdbcName!='del'>
        <if test="${ associativeTable.clsNamePrefix?uncap_first }Model!=null and ${ associativeTable.clsNamePrefix?uncap_first }Model.${column.name}!=null">
            and b.${column.jdbcName} = ${r'#'}{${ associativeTable.clsNamePrefix?uncap_first }Model.${column.name},jdbcType=${column.jdbcType}}
        </if>
            </#if>
        </#list>
    </select>

    <select id="get" parameterType="${clsNamePrefix?cap_first}Model" resultMap="${clsNamePrefix?uncap_first}OneToOneMap">
        SELECT
        <include refid="select${clsNamePrefix?cap_first}"></include>
        FROM ${tableName} a,${associativeTable.name} b  WHERE a.${tableDto.primaryKey.jdbcName} = b.${tableDto.primaryKey.jdbcName} and a.del=0
        and b.del=0 and  a.${tableDto.primaryKey.jdbcName}=${r'#'}{${tableDto.primaryKey.name},jdbcType=${tableDto.primaryKey.jdbcType}}
    </select>