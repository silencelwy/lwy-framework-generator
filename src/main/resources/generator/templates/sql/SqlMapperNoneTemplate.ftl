
    <sql id="select${clsNamePrefix?cap_first}">
             <#list selectColumns as column>
                  ${column.jdbcName}<#if column_has_next>,</#if>
             </#list>
    </sql>

    <select id="query" parameterType="${model}" resultType="${model}">
        SELECT
        <include refid="select${clsNamePrefix?cap_first}"></include>
        FROM ${tableName}  WHERE del=0
        <#list selectColumns as column>
             <#if column.jdbcName!='del'>
        <if test="${column.name}!=null">
            and ${column.jdbcName} = ${r'#'}{${column.name},jdbcType=${column.jdbcType}}
        </if>
             </#if>
        </#list>
    </select>

    <select id="get" parameterType="${model}" resultType="${model}">
        SELECT
        <include refid="select${clsNamePrefix?cap_first}"></include>
        FROM ${tableName}  WHERE del=0 and  ${tableDto.primaryKey.jdbcName}=${r'#'}{${tableDto.primaryKey.name},jdbcType=${tableDto.primaryKey.jdbcType}}
    </select>