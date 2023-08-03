package ${basePackage}.${modelPackage}.facade.impl;

import cn.com.flaginfo.framework.rsql.mongodb.dto.Rsql;
import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.vo.ResultVO;
import cn.com.flaginfo.framework.file.export.IExportFileToOss;
import cn.com.flaginfo.framework.file.export.IFileWriter;
import cn.com.flaginfo.framework.file.export.dto.FileOssInfo;
import cn.com.flaginfo.framework.id.IdGenerator;

import cn.com.flaginfo.vo.Account;
import cn.com.flaginfo.vo.UserInfo;
import cn.com.flaginfo.framework.webmvc.utils.ResultGeneratorUtil;
import cn.com.flaginfo.framework.rsql.mongodb.utils.ParseUtils;

import ${basePackage}.${modelPackage}.facade.I${model}Facade;
import ${basePackage}.${modelPackage}.model.${model};
import ${basePackage}.${modelPackage}.excel.${model}Excel;
import ${basePackage}.${modelPackage}.service.I${model}Service;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

import java.util.*;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.facade.impl
 * @Description: code by codeGenerator
 * @date ${.now}
 */
@Slf4j
@Component
public class ${model}FacadeImpl implements I${model}Facade {
    @Autowired
    private I${model}Service service;
    @Autowired
    private IExportFileToOss exportFileToOss;
    @Override
    public ResultVO save(${model} model) {
        String id = service.add(model);
        Map map = new HashMap(1);
        map.put("id",id);
        return ResultGeneratorUtil.success(map);
    }

    @Override
    public ResultVO update(${model} model) {
        service.update(model);
        return ResultGeneratorUtil.success("修改成功");
    }


    @Override
    public ResultVO get(String id) {
        return ResultGeneratorUtil.success(service.getById(id));
    }

    @Override
    public ResultVO<List<${model}>> queryByPage(RsqlBase base, Pageable page) {
        Rsql rsql = Rsql.builder().where(base.getWhere()).sort(base.getSort()).page(page.getPageNumber()).pageSize(page.getPageSize()).build();
        ResultVO<List<${model}>> modelResult = ParseUtils
                .MongoPageToR(service.findByPage(rsql),page);
        return modelResult;
    }

    @Override
    public ResultVO del(String id) {
        service.del(id);
        return ResultGeneratorUtil.success("移除成功");
    }
    @Async
    @Override
    public void export(RsqlBase queryRsql, UserInfo userInfo) throws Exception {
        String operatorId = userInfo.getMemberId();
        String operator = userInfo.getMemberName();
        if(StringUtils.isEmpty(operatorId)) {
            if(userInfo.getAccountInfo() != null) {
                operatorId = userInfo.getAccountInfo().getAccountCode();
                operator = userInfo.getAccountInfo().getName();
            }else {
                operatorId = "admin";
                operator = "admin";
            }
        }
        FileOssInfo.OssTaskInfo ossTaskInfo = FileOssInfo.OssTaskInfo.builder()
                .businessType(getBusinessType(userInfo))
                .operatorId(operatorId)
                .operator(operator)
                .spId(userInfo.getSpId())
                .fileTitle("${modelName}数据")
                .fileType("xlsx")
                .taskCategory("${modelName}数据")
                .build();


        FileOssInfo fileOssInfo = FileOssInfo.builder()
                .compress(false)
                .pathName("${modelName}数据"+ IdGenerator.getInstance().generateIdToString())
                .eachSheetLimit(1000000)
                .token(userInfo.getToken())
                .ossTaskInfo(ossTaskInfo)
                .targetClass(${model}Excel.class)
                .build();


        exportFileToOss.createAndUpFileAndNotice(fileOssInfo, null, iFileWriter -> this.LoadOrderData(iFileWriter, queryRsql));

    }

    private void LoadOrderData(IFileWriter fileWriter, RsqlBase queryRsql) {

        int pageSize = 1000;
        ResultVO<List<${model}>> listResultVO = queryByPage(queryRsql, PageRequest.of(1, pageSize));
        int total = listResultVO.getPage().getTotal();
        int page = total / pageSize + 1;
        LinkedList list = new LinkedList<>();

        for (int i = 1; i <= page; i++) {
            List<${model}> data;
            if (i == 1) {
                data = listResultVO.getData();
            } else {
                data = queryByPage(queryRsql, PageRequest.of(i, pageSize)).getData();
            }

            List<${model}Excel> excels = BeanUtil.copyToList(data, ${model}Excel.class);
            list.addAll(excels);
        }
        try {
            fileWriter.write(list);
            fileWriter.finish();
        } catch (Exception e) {
            log.error("导出失败", e);
        }
        list.clear();


    }
    private String getBusinessType(UserInfo userInfo) {
        String accountCode;
        Account account = userInfo.getAccountInfo();
        if (account == null) {
            accountCode = "admin";
        } else {
            accountCode = account.getAccountCode();
        }
        StringBuilder businessType = new StringBuilder();
        String[] split = accountCode.split("-");
        if (split.length == 1) {
            businessType = new StringBuilder(split[0]);
        } else {
            for (int i = split.length - 1; i >= 0; i--) {
                businessType.append(split[i]);
            }
        }

        return businessType.toString();
    }
}
