<#-- 一对多映射，使用级联关联，但会存在N+1问题，请勿用于大数据量查询即可 -->
<!--嵌套级联（使用嵌套查询，请删除内嵌查询）。请勿用于大数据量查询。-->
    <resultMap id="${clsNamePrefix?uncap_first}OneToMoreMap" type="${clsNamePrefix?cap_first}Model">
        <id property="${tableDto.primaryKey.name}" column="${tableDto.primaryKey.jdbcName}" javaType="${tableDto.primaryKey.javaType}" jdbcType="${tableDto.primaryKey.jdbcType}"/>
        <#list associativeTables as associativeTable>
        <collection property="${ associativeTable.clsNamePrefix?uncap_first }Models" javaType="ArrayList"  ofType="${ associativeTable.clsNamePrefix?cap_first }Model" column="${tableDto.primaryKey.jdbcName}" select="select${ associativeTable.clsNamePrefix?uncap_first }Byid"/>
</#list>
    </resultMap>

    <sql id="select${clsNamePrefix?cap_first}">
             <#list selectColumns as column>
                 ${column.jdbcName}<#if column_has_next>,</#if>
             </#list>
    </sql>

    <select id="query" parameterType="${clsNamePrefix?cap_first}Model" resultMap="${clsNamePrefix?uncap_first}OneToMoreMap">
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

    <select id="get" parameterType="${clsNamePrefix?cap_first}Model" resultMap="${clsNamePrefix?uncap_first}OneToMoreMap">
        SELECT
        <include refid="select${clsNamePrefix?cap_first}"></include>
        FROM ${tableName}  WHERE del=0 and  ${tableDto.primaryKey.jdbcName}=${r'#'}{${tableDto.primaryKey.name},jdbcType=${tableDto.primaryKey.jdbcType}}
    </select>

    <!--级联查询sql定义开始-->
<#list associativeTables as associativeTable>
    <select id="select${ associativeTable.clsNamePrefix?uncap_first }Byid" parameterType="${tableDto.primaryKey.javaType}" resultType="${associativeTable.clsNamePrefix?cap_first}Model">
        SELECT
        <#list associativeTable.columns as column>
            ${column.jdbcName}<#if column_has_next>,</#if>
        </#list>
        FROM ${associativeTable.name}  WHERE del=0 and  ${tableDto.primaryKey.jdbcName}=${r'#'}{${tableDto.primaryKey.name},jdbcType=${tableDto.primaryKey.jdbcType}}
    </select>
</#list>
    <!--嵌套级联查询sql定义结束-->

<!--内嵌级联（使用内嵌级联，请删除嵌套查询）,问题在于分页数不准确。-->
    <resultMap id="${clsNamePrefix?uncap_first}InlineOneToMoreMap" type="${clsNamePrefix?cap_first}Model">
        <id property="${tableDto.primaryKey.name}" column="a_${tableDto.primaryKey.jdbcName}" javaType="${tableDto.primaryKey.javaType}" jdbcType="${tableDto.primaryKey.jdbcType}"/>
        <#list selectColumns as column>
            <#if column.jdbcName!=tableDto.primaryKey.jdbcName&&(column.jdbcName!='del')>
        <result property="${column.name}" column="a_${column.jdbcName}" javaType="${column.javaType}" jdbcType="${column.jdbcType}"/>
            </#if>
        </#list>
    <#-- 生成一对多映射关联信息 -->
        <#list associativeTables as associativeTable>
        <collection property="${ associativeTable.clsNamePrefix?uncap_first }Models" javaType="ArrayList"  ofType="${ associativeTable.clsNamePrefix?cap_first }Model">
            <id property="${associativeTable.primaryKey.name}" column="b_${associativeTable.primaryKey.jdbcName}" javaType="${associativeTable.primaryKey.javaType}" jdbcType="${associativeTable.primaryKey.jdbcType}"/>
            <#list associativeTable.columns as column>
                <#if column.jdbcName!=associativeTable.primaryKey.jdbcName&&(column.jdbcName!='del')>
            <result property="${column.name}" column="b_${column.jdbcName}" javaType="${column.javaType}" jdbcType="${column.jdbcType}"/>
                </#if>
            </#list>
        </collection>
        <#-- 声明一个变量，一对多映射关系，所以设置第一张表数据信息到变量中。同时跳出循环 -->
            <#assign associativeTable=associativeTable/>
            <#break/>
        </#list>
    </resultMap>

    <sql id="selectInline${clsNamePrefix?cap_first}">
             <#list selectColumns as column>
                 a.${column.jdbcName} as a_${column.jdbcName},
             </#list>
             <#list associativeTable.columns as column>
                 b.${column.jdbcName} as b_${column.jdbcName} <#if column_has_next>,</#if>
             </#list>
    </sql>
    <!--查询-->
    <select id="queryInline" parameterType="${clsNamePrefix?cap_first}Model" resultMap="${clsNamePrefix?uncap_first}InlineOneToMoreMap">
        SELECT
        <include refid="selectInline${clsNamePrefix?cap_first}"></include>
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
        <if test="${ associativeTable.clsNamePrefix?uncap_first }Models!=null and ${ associativeTable.clsNamePrefix?uncap_first }Models[0].${column.name}!=null">
            and b.${column.jdbcName} = ${r'#'}{${ associativeTable.clsNamePrefix?uncap_first }Models[0].${column.name},jdbcType=${column.jdbcType}}
        </if>
            </#if>
        </#list>
    </select>

    <select id="getInline" parameterType="${clsNamePrefix?cap_first}Model" resultMap="${clsNamePrefix?uncap_first}InlineOneToMoreMap">
        SELECT
        <include refid="selectInline${clsNamePrefix?cap_first}"></include>
        FROM ${tableName} a,${associativeTable.name} b  WHERE a.${tableDto.primaryKey.jdbcName} = b.${tableDto.primaryKey.jdbcName} and a.del=0
        and b.del=0 and  a.${tableDto.primaryKey.jdbcName}=${r'#'}{${tableDto.primaryKey.name},jdbcType=${tableDto.primaryKey.jdbcType}}
    </select>
<!--内嵌级联结束,问题在于分页数不准确。-->