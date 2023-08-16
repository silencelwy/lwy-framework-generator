package io.github.silencelwy.generator;

import io.github.silencelwy.generator.code.modelDefine.ModelDefine;
import io.github.silencelwy.generator.code.modelDefine.ModelTypeEnum;
import io.github.silencelwy.generator.mongo.MongoCodeGenerator;
import io.github.silencelwy.generator.mysql.MysqlCodeGenerator;
import io.github.silencelwy.generator.rpc.RpcCodeGenerator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Slf4j
public final class CodeGenerator {

    private static final CodeGenerator codeGenerator = new CodeGenerator();

    public static CodeGenerator getInstance(){
        return codeGenerator;
    }

    private CodeGenerator() {

    }

    public void generator(List<ModelDefine> list) {
        if (CollectionUtils.isEmpty(list)){
            System.out.println("集合为空");
        }
        list.forEach(this::generator);
    }

    public void generator(ModelDefine modelDefine) {
        @NonNull ModelTypeEnum modelTypeEnum = modelDefine.getModelTypeEnum();
        if (ModelTypeEnum.MONGODB.getType().equals(modelTypeEnum.getType())){
            MongoCodeGenerator.getInstance().generator(modelDefine);
        }else if (ModelTypeEnum.MYSQL.getType().equals(modelTypeEnum.getType())){
            MysqlCodeGenerator.getInstance().generator(modelDefine);
        }
        if (modelDefine.isNeedRpc()){
            RpcCodeGenerator.getInstance().generator(modelDefine);
        }
    }

}
