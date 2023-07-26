package io.github.silencelwy.generator.file.service;

import io.github.silencelwy.generator.file.dto.ClassCodeDto;
import io.github.silencelwy.generator.project.config.ProjectConfig;
import io.github.silencelwy.generator.utils.UnderlineOrCamelUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Slf4j
public class FreeMakerTemplate {
    private volatile  static FreeMakerTemplate freeMakerTemplate;
    private FreeMakerTemplate(){}

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_HH_mm_ss");
    private static Configuration configuration;

    public static FreeMakerTemplate getFreeMakerTemplate(){
        if(freeMakerTemplate == null){
            synchronized (FreeMakerTemplate.class){
                if(freeMakerTemplate == null){
                    freeMakerTemplate = new FreeMakerTemplate();
                }
            }
        }
        return freeMakerTemplate;
    }

     static {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
         try {
             if(ProjectConfig.getProjectConfig().getProjectDto().isTemplateCustom()){
                 //加载自定义模板，自定义模板存放在自己工程中的/src/main/resources/generator/templates
                 configuration.setDirectoryForTemplateLoading(new File(ProjectConfig.getProjectConfig().getProjectDto().getTemplateFilePath()));
             }else{
                 //加载默认模板
                 configuration.setClassForTemplateLoading(FreeMakerTemplate.class, "/generator/templates");
             }
         } catch (IOException e) {
            log.error("加载模板配置文件失败",e);
            System.exit(-1);
         }

     }

    /**
     * 生成文件
     *
     * @param templateName:模板名
     * @param root：数据原型
     */
    public <T extends ClassCodeDto> void generateFile(String templateName, T root) {

        Writer out = null;
        OutputStream outputStream = null;
        try {
            String srcPath = ProjectConfig.getProjectConfig().getProjectDto().getProjectPath()
                    +ProjectConfig.getProjectConfig().getProjectDto().getJavaPath()
                    + UnderlineOrCamelUtil.packageConvertPath(root.getFullPackage());
            log.info("生成的源码存放路径:{}",srcPath);
            File dir = FileUtils.getFile(srcPath);

            if (!dir.exists()) {
                log.info("目录{}不存在，创建目录。",srcPath);
                dir.mkdirs();
            }

            File srcFile = FileUtils.getFile(dir,root.getFileName());

            if(srcFile.exists()){
                log.info("文件{}已存在，将备份旧文件",root.getFileName());
                FileUtils.moveFile(srcFile,FileUtils.getFile(dir,root.getFileName()+simpleDateFormat.format(new Date())+".bak"));
            }

            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            outputStream = new FileOutputStream(srcFile);
//            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(dir, "outFileName")), "UTF-8"));
            out = new OutputStreamWriter(outputStream);
            Template temp = configuration.getTemplate(templateName);
            ;
            temp.process(root, out);
        } catch (IOException e) {
            log.error("生产xx文件失败！。", e);
        } catch (TemplateException e) {
            log.error("生产xx文件失败！。", e);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                log.warn("该异常不影响业务，不用处理！。", e);
            }
        }
    }

}

