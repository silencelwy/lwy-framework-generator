package io.github.silencelwy.generator.rpc;

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
public class FreeMakerRpcTemplate {
    private volatile  static FreeMakerRpcTemplate freeMakerTemplate;
    private FreeMakerRpcTemplate(){}

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_HH_mm_ss");
    private static Configuration configuration;

    public static FreeMakerRpcTemplate getFreeMakerTemplate(){
        if(freeMakerTemplate == null){
            synchronized (FreeMakerRpcTemplate.class){
                if(freeMakerTemplate == null){
                    freeMakerTemplate = new FreeMakerRpcTemplate();
                }
            }
        }
        return freeMakerTemplate;
    }

     static {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(FreeMakerRpcTemplate.class, "/generator/rpc");
     }

    /**
     * 生成文件
     *
     * @param templateName:模板名
     */
    public void generateFile(String templateName, RpcCodeVo codeVo) {

        Writer out = null;
        OutputStream outputStream = null;
        try {
            String srcPath = codeVo.getFilePath() +  codeVo.getMPath();
            log.info("生成的源码存放路径:{}",srcPath);

            File dir = FileUtils.getFile(srcPath);

            if (!dir.exists()) {
                log.info("目录{}不存在，创建目录。",srcPath);
                dir.mkdirs();
            }

            File srcFile = FileUtils.getFile(dir,codeVo.getFileName());

            if(srcFile.exists()){
                log.info("文件{}已存在，将备份旧文件",codeVo.getFileName());
                FileUtils.moveFile(srcFile,FileUtils.getFile(dir,codeVo.getFileName()+simpleDateFormat.format(new Date())+".bak"));
            }

            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            outputStream = new FileOutputStream(srcFile);
//            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(dir, "outFileName")), "UTF-8"));
            out = new OutputStreamWriter(outputStream);
            Template temp = configuration.getTemplate(templateName);

            temp.process(codeVo, out);
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

