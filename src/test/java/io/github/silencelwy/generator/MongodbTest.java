package io.github.silencelwy.generator;

import io.github.silencelwy.generator.mongo.MongoCodeGenerator;
import org.junit.Test;

public class MongodbTest {

    @Test
    public void generatorTest(){
        MongoCodeGenerator.generator2("cn.com.flaginfo.jinfen.crowd.cc",
                "id,String,id;" +
                        "type,String,推送范围。1:指定企业；2：筛选企业范围;" +
                        "epWhere,Integer,人群限制。0:随机；1：指定值;" +
                        "crowsIds,String,人群包。多个人群包逗号分隔;" +
                        "crowTotal,String,人群包累计手机号数量;" +
                        "pushRule,Integer,推送规则。1：随机；2：平均；3：指定数量分配"+
                        "cid,String,操作人ID"+
                        "cname,String,操作人姓名"+
                        "ctime,Date,创建时间",
                "CrowdPushConfig,push");

    }
}
