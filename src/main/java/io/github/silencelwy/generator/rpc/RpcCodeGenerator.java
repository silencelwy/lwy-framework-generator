package io.github.silencelwy.generator.rpc;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.silencelwy.generator.code.modelDefine.FieldDefine;
import io.github.silencelwy.generator.code.modelDefine.ModelDefine;
import io.github.silencelwy.generator.mongo.ColumnVo;
import io.github.silencelwy.generator.mongo.MongoCodeVo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成代码
 * 自动增加字段注解
 * 自动生成rap json格式
 *
 * @author zhengmng
 */
@Slf4j
public final class RpcCodeGenerator {

    private static final RpcCodeGenerator rpcCodeGenerator = new RpcCodeGenerator();

    public static RpcCodeGenerator getInstance() {
        return rpcCodeGenerator;
    }

    private RpcCodeGenerator() {

    }

    public void generator(ModelDefine modelDefine) {
        log.info("代码生成开始");
        String collName = CollUtil.join(splitByUp(modelDefine.getModel()), "_");
        MongoCodeVo codeVo = MongoCodeVo.builder()
                .basePackage(modelDefine.getBackPackage())
                .model(StrUtil.upperFirst(modelDefine.getModel()))
                .modelName(modelDefine.getModelDesc())
                .modelPath(StrUtil.isBlank(modelDefine.getModelPath()) ? "/" + StringUtils.lowerCase(modelDefine.getModel()) : modelDefine.getModelPath())
                .modelPackage(StrUtil.isBlank(modelDefine.getModelPackage()) ? StringUtils.lowerCase(modelDefine.getModel()) : modelDefine.getModelPackage())
                .collectionName(collName)
                .needRpc(modelDefine.isNeedRpc())
                .needCache(modelDefine.isNeedCache())
                .author(System.getProperty("user.name"))
                .build();
        codeVo.buildPath();


        @NonNull List<FieldDefine> fieldDefineList = modelDefine.getFieldDefineList();
        if (CollUtil.isNotEmpty(fieldDefineList)) {
            codeVo.setColumns(CollStreamUtil.toList(fieldDefineList, fieldDefine -> ColumnVo.builder()
                    .name(fieldDefine.getField())
                    .fieldName(fieldDefine.getFieldName())
                    .desc(fieldDefine.getFieldDesc())
                    .javaType(fieldDefine.getFieldTypeEnum().getJavaType())
                    .supportExport(fieldDefine.isSupportExport())
                    .supportImport(fieldDefine.isSupportImport())
                    .build()));
        }
        codeInit(codeVo);
    }

    private void codeInit(MongoCodeVo codeVo) {

        long startTime = System.currentTimeMillis();

        RpcCodeVo rpcCodeVo = BeanUtil.copyProperties(codeVo, RpcCodeVo.class);

        rpcCodeVo.setMPath("/rpc/api");
        rpcCodeVo.setFileName("I"+rpcCodeVo.getModel() + "RpcRemote.java");
        FreeMakerRpcTemplate.getFreeMakerTemplate().generateFile("RemoteApi.ftl", rpcCodeVo);

        rpcCodeVo.setMPath("/rpc/request");
        rpcCodeVo.setFileName(rpcCodeVo.getModel() + "RpcRequest.java");
        FreeMakerRpcTemplate.getFreeMakerTemplate().generateFile("RemoteRequestT.ftl", rpcCodeVo);

        rpcCodeVo.setMPath("/rpc/response");
        rpcCodeVo.setFileName(rpcCodeVo.getModel() + "RpcResponse.java");
        FreeMakerRpcTemplate.getFreeMakerTemplate().generateFile("RemoteResponseT.ftl", rpcCodeVo);


        log.info("代码生成耗时{}(ms)", System.currentTimeMillis() - startTime);
    }

    private static List<String> splitByUp(String model) {
        char[] chars = model.toCharArray();
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append((char) (c + 32));
            } else {
                sb.append(c);
            }
        }

        list.add(sb.toString());
        return list;
    }
}
