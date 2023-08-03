package ${basePackage}.${modelPackage}.controller;


import cn.com.flaginfo.framework.constant.ResultCode;
import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.vo.QueryVO;
import cn.com.flaginfo.framework.vo.ResultVO;
import cn.com.flaginfo.framework.webmvc.utils.ResultGeneratorUtil;
import cn.com.flaginfo.utils.AuthDataUtils;
import ${basePackage}.${modelPackage}.facade.I${model}Facade;
import ${basePackage}.${modelPackage}.model.${model};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.controller
 * @Description: code by codeGenerator
 * @date ${.now}
 */
@RestController
@RequestMapping("${modelPath}")
public class ${model}Controller {
    @Autowired
    private I${model}Facade facade;

    /**
     * 新增
     * @param model
     * @return
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody ${model} model){
        return facade.save(model);
    }

    /**
     * 更新
     * @param model
     * @return
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody ${model} model){
        return facade.update(model);
    }

    /**
     * 明细
     * @param id
     * @return
     */
    @GetMapping("/get")
    public ResultVO get(String id){
        return facade.get(id);
    }
   /**
    * 删除
    * @param id
    * @return
    */
    @DeleteMapping("/del")
    public ResultVO del(String id){
        return facade.del(id);
    }

    /**
     * 分页查询
     * @param queryVO
     * @return
     */
    @PostMapping("/query")
    public ResultVO query(@RequestBody QueryVO<RsqlBase> queryVO){
        PageRequest page = PageRequest.of(queryVO.getPage(),queryVO.getPageSize());
        RsqlBase base = queryVO.getParams();
        if (base == null){
            base = new RsqlBase();
        }
        return facade.queryByPage(base,page);
    }

        /**
         * 导出
         * @param queryVO
         * @return
         */
        @PostMapping("/export")
        public ResultVO export(@RequestBody QueryVO<RsqlBase> queryVO){
            PageRequest page = PageRequest.of(queryVO.getPage(),queryVO.getPageSize());
            RsqlBase base = queryVO.getParams();
            if (base == null){
                base = new RsqlBase();
            }
            try {
                facade.export(base, AuthDataUtils.getUserInfo());
            } catch (Exception e) {
                return ResultGeneratorUtil.error(ResultCode.FAIL.code(),"创建导出任务失败!");
            }
            return ResultGeneratorUtil.success("创建导出任务成功!");
        }
}
