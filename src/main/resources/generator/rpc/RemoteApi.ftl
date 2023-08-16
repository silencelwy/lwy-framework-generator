package ${basePackage}.${modelPackage}.rpc.api;

import cn.com.flaginfo.framework.dto.RPCRequest;
import cn.com.flaginfo.framework.dto.RPCResponse;
import ${basePackage}.${modelPackage}.rpc.request.${model}RpcRequest;
import ${basePackage}.${modelPackage}.rpc.response.${model}RpcResponse;

import java.util.List;

public interface I${model}RpcRemote {

        RPCResponse save(${model}RpcRequest request);

        RPCResponse update(${model}RpcRequest request);

        RPCResponse<${model}RpcResponse> get(String id);

        RPCResponse<List<${model}RpcResponse>> query(RPCRequest<${model}RpcRequest> rpcRequest);

        RPCResponse del(String id);

}
