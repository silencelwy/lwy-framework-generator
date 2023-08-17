package ${basePackage}.${modelPackage}.rpcImpl;

import cn.com.flaginfo.framework.dto.BasePageDto;
import cn.com.flaginfo.framework.dto.RPCRequest;
import cn.com.flaginfo.framework.dto.RPCResponse;
import cn.com.flaginfo.framework.rsql.mongodb.dto.Rsql;
import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.validator.ValidatorUtils;
import cn.com.flaginfo.platform.rpc.annotation.FlaginfoProvider;
import cn.com.flaginfo.framework.vo.ResultVO;
import ${basePackage}.${modelPackage}.model.${model};
import ${basePackage}.${modelPackage}.rpc.api.I${model}RpcRemote;
import ${basePackage}.${modelPackage}.rpc.request.${model}RpcRequest;
import ${basePackage}.${modelPackage}.rpc.response.${model}RpcResponse;
import ${basePackage}.${modelPackage}.facade.I${model}Facade;

import java.util.Collections;
import java.util.List;
import ${basePackage}.${modelPackage}.vo.${model}Vo;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@Slf4j
@FlaginfoProvider(interfaceClass = I${model}RpcRemoteImpl.class, version = "$//{spring.flaginfo.provider.${model}.Rpc.Remote.version:1.0.0}",
        group = "$//{spring.flaginfo.provider.${model}.Rpc.Remote.group:${model}-Remote-rpc}")
public class I${model}RpcRemoteImpl implements I${model}RpcRemote {

    @Autowired
    private I${model}Facade facade;

    @Override
    public RPCResponse save(${model}RpcRequest request) {
        ${model}Vo modelVo = BeanUtil.copyProperties(request, ${model}Vo.class);
        ResultVO resultVo = facade.save(modelVo);
        return RPCResponse.error(resultVo.getErrcode(),resultVo.getErrmsg());
    }

    @Override
    public RPCResponse update(${model}RpcRequest request) {
        ${model}Vo modelVo = BeanUtil.copyProperties(request, ${model}Vo.class);
        ResultVO resultVo = facade.update(modelVo);
        return RPCResponse.error(resultVo.getErrcode(),resultVo.getErrmsg());
    }

    @Override
    public RPCResponse<${model}RpcResponse> get(String id) {
        ResultVO resultVO = facade.get(id);
        RPCResponse rpcResponse = RPCResponse.error(resultVO.getErrcode(), resultVO.getErrmsg());
        rpcResponse.setData(resultVO.getData());
        return rpcResponse;
    }

    @Override
    public RPCResponse<List<${model}RpcResponse>> query(RPCRequest<${model}RpcRequest> request) {
        ${model}RpcRequest ${model}RpcRequest = request.getT();
        ValidatorUtils.validate(${model}RpcRequest);

        String where = ${model}RpcRequest.getWhere();
        String sort = ${model}RpcRequest.getSort();
        Integer page = ${model}RpcRequest.getPage();
        Integer pageSize = ${model}RpcRequest.getPageSize();
        RsqlBase rsqlBase = new RsqlBase();
        rsqlBase.setWhere(where);
        rsqlBase.setSort(sort);
        PageRequest pageRequest = PageRequest.of(1, 100);
        if (page != null) {
            pageRequest = PageRequest.of(page, pageSize);
        }
        try {
            ResultVO<List<${model}>> resultVO = facade.queryByPage(rsqlBase, pageRequest);

            List<${model}> data = resultVO.getData();
            if (CollectionUtils.isEmpty(data)) {
                return RPCResponse.ok(Collections.EMPTY_LIST);
            }
            return RPCResponse.ok(BeanUtil.copyToList(data,${model}RpcResponse.class));
        }catch (Exception exception){
            log.error("请求异常:rsqlBase{},pageRequest:{}", JSON.toJSONString(rsqlBase),JSON.toJSONString(pageRequest),exception);
            return RPCResponse.error(2001,"请求异常");
        }
    }

    @Override
    public RPCResponse del(String id) {
        ResultVO del = facade.del(id);
        return RPCResponse.error(del.getErrcode(),del.getErrmsg());
    }
}
