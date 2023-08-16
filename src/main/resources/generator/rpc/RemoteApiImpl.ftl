package ${basePackage}.${modelPackage}.remote;

import cn.com.flaginfo.framework.dto.RPCRequest;
import cn.com.flaginfo.framework.dto.RPCResponse;
import ${basePackage}.${modelPackage}.rpc.request.${model}Request;
import ${basePackage}.${modelPackage}.rpc.response.${model}Response;
import ${basePackage}.${modelPackage}.facade.I${model}Facade;
import java.util.List;
import cn.hutool.core.bean.BeanUtil;


public class I${model}RpcRemoteImpl {

        private I${model}Facade facade;

        public RPCResponse save(${model}RpcRequest request){
           ${model}Vo modelVo = BeanUtil.copyProperties(request, ${model}Vo.class);
           ResultVO resultVo = facade.save(modeVo);
        }

        public RPCResponse update(${model}RpcRequest request){

        }

        public RPCResponse<${model}RpcResponse> get(String id){

        }

        public RPCResponse<List<${model}RpcResponse>> query(RPCRequest<${model}RpcRequest> rpcRequest){

        }

        public RPCResponse del(String id){

        }

}
