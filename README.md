# SimpleRPC

## 核心

### 基于Zookeeper的服务注册中心，实现服务的注册与服务的发现
- 注册：一级节点为/registry(持久),二级节点为Service名称(持久),三级节点为address节点(临时),值为服务host:port
- 发现：①获取Service节点 ②获取address节点 返回服务addressPath(host:port)

### 基于Netty的RPC服务器
- 编码/解码：基于protostuf的序列化/反序列化
- RPC请求/响应消息的封装
- RPC请求处理Handler：根据传入的Request的参数反射调用Service并返回执行结果。封装结果result到Response,传输给客户端
- 服务的初始化：扫描所有带有@RpcService注解的服务，用一个Map维护服务名称与服务实体之间的关系
- 服务的启动：根据传入的address向服务注册中心注册服务与启动服务器

### 基于Netty的RPC客户端与RPC代理
- RPC代理的作用是客户端程序可以通过RPC代理对象获取到远程服务对象的代理，通过调用代理的方法实现远程方法调用。
- RPC客户端：发送RPC请求(Request)与获取RPC响应(Response)
- RPCProxy：①创建RPC请求对象并设置请求属性 ②在注册中心根据服务名称获取服务地址 ③根据服务地址利用RPC客户端发送RPC请求与获取RPC响应 ④返回RPC响应结果

## 图示
![](https://raw.githubusercontent.com/ChinaLHR/SimpleRPC/master/images/SimpleRPC.png)