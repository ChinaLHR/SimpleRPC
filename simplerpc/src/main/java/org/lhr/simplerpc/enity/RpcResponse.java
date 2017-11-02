package org.lhr.simplerpc.enity;

/**
 * @Author : ChinaLHR
 * @Date : Create in 9:23 2017/11/2
 * @Email : 13435500980@163.com
 *
 * 封装RPC响应
 */
public class RpcResponse {

    private String requestId;
    private Exception exception;
    private Object result;

    public boolean hasException() {
        return exception != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
