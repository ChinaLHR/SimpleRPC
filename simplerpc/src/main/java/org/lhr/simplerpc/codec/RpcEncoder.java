package org.lhr.simplerpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.lhr.simplerpc.utils.SerializationUtil;

/**
 * @Author : ChinaLHR
 * @Date : Create in 9:25 2017/11/2
 * @Email : 13435500980@163.com
 * <p>
 * RPC编码器
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf buf) throws Exception {
        //确定指定的对象是否与这个类所表示的对象兼容
        if (genericClass.isInstance(obj)){
            byte[] data = SerializationUtil.serialize(obj);
            buf.writeInt(data.length);
            buf.writeBytes(data);
        }
    }
}
