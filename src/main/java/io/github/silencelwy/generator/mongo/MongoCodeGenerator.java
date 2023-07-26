package io.github.silencelwy.generator.mongo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成代码
 * 自动增加字段注解
 * 自动生成rap json格式
 * @author zhengmng
 */
@Slf4j
public class MongoCodeGenerator {

    public static void generator(final String basePackage, final String... models) {
        for (String model : models){
            generator2(basePackage,null,model);
        }

    }

    public static void generator2(final String basePackage, final String fields, final String model) {
        long startTime = System.currentTimeMillis();
        log.info("代码生成开始");
            String[] ma = model.split(",");
            String m = ma[0];
            String mn = ma[1];
            List<String> words = splitByUp(m);
            String collName = contactBySymbol(words,"_");
            String mPath = contactBySymbol(words,"/");
            MongoCodeVo codeVo = MongoCodeVo.builder()
                    .basePackage(basePackage)
                    .model(m)
                    .modelName(mn)
                    .modelPath(mPath)
                    .collectionName(collName)
                    .author(System.getProperty("user.name"))
                    .build();
            codeVo.buildPath();


            if (StringUtils.isNotBlank(fields)){
                List<ColumnVo> columns = buildColumns(fields);
                codeVo.setColumns(columns);
            }
            //model
            codeVo.setMPath("/model");
            codeVo.setFileName(codeVo.getModel()+".java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ModelT.ftl",codeVo);

            //vo
            codeVo.setMPath("/vo");
            codeVo.setFileName(codeVo.getModel()+"Vo.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("VoT.ftl",codeVo);

            //repository
            codeVo.setMPath("/service");
            codeVo.setFileName("I" + codeVo.getModel()+"Repository.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("RepositoryT.ftl",codeVo);

            //IService
            codeVo.setFileName("I" + codeVo.getModel()+"Service.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("IServiceT.ftl",codeVo);

            //service
            codeVo.setMPath("/service/impl");
            codeVo.setFileName(codeVo.getModel()+"ServiceImpl.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ServiceT.ftl",codeVo);


            //IFacade
            codeVo.setMPath("/facade");
            codeVo.setFileName("I"+ codeVo.getModel()+"Facade.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("IFacadeT.ftl",codeVo);

            //facade
            codeVo.setMPath("/facade/impl");
            codeVo.setFileName(codeVo.getModel()+"FacadeImpl.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("FacadeT.ftl",codeVo);

            //controller
            codeVo.setMPath("/controller");
            codeVo.setFileName(codeVo.getModel()+"Controller.java");
            FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ControllerT.ftl",codeVo);

        log.info("代码生成耗时{}(ms)", System.currentTimeMillis() - startTime);
    }

    private static List<String> splitByUp(String model){
        char[] chars = model.toCharArray();
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : chars){
            if (CharUtils.isAsciiAlphaUpper(c)){
                if (sb.length() > 0){
                    list.add(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append((char) (c + 32));
            }else {
                sb.append(c);
            }
        }

        list.add(sb.toString());
        return list;
    }

    private static String contactBySymbol(List<String> words, String sym){
        StringBuilder sb = new StringBuilder();
        for (String w : words){
            sb.append(sym);
            sb.append(w);
        }
        return sb.substring(1);
    }

    private static List<ColumnVo> buildColumns(String fields){

        List<ColumnVo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(fields)){
            String[] field = StringUtils.split(fields,";");
            for (String f : field){
                String[] column = StringUtils.split(f,",");
                ColumnVo columnVo = ColumnVo.builder()
                        .name(column[0])
                        .javaType(column[1])
                        .build();
                if (column.length >= 3){
                    columnVo.setDesc(column[2]);
                }
                List<String> words = splitByUp(column[0]);
                if (words.size() > 1){
                    columnVo.setFieldName(contactBySymbol(words,"_"));
                }
                list.add(columnVo);
            }
        }

        return list;
    }

    public static void generatorAnnotation(final String... classes) {
        long startTime = System.currentTimeMillis();
        log.info("注解生成开始");
        log.info("注解生成耗时{}(ms)", System.currentTimeMillis() - startTime);
    }


    public static void generatorJson(String path) {

//        long startTime = System.currentTimeMillis();
//        log.info("[{}]生成开始");
//
//        int start = path.lastIndexOf("/");
//        int end = path.lastIndexOf(".");
//        String clazzName = path.substring(start+1,end);
//
//        MongoCodeVo codeVo = MongoCodeVo.builder()
//                .mPath("")
//                .filePath(path.substring(0,start))
//                .model(new JavaParser().parseJavaFiles(path))
//                .build();
//        codeVo.setFileName(clazzName +".json");
//        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("rap.ftl",codeVo);
//
//        log.info("[{}]生成耗时{}(ms)", System.currentTimeMillis() - startTime);
    }
}
