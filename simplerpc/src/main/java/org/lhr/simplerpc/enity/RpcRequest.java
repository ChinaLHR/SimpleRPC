package org.lhr.simplerpc.enity;

/**
 * @Author : ChinaLHR
 * @Date : Create in 9:20 2017/11/2
 * @Email : 13435500980@163.com
 *
 * 封装RPC请求
 */
public class RpcRequest {

    private String requestId;
    private String interfaceName;//接口名称
    private String serviceVersion;//版本号
    private String methodName;//方法名称
    private Class<?>[] parameterTypes;//方法参数
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
