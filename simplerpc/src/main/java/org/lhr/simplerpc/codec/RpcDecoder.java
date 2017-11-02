package org.lhr.simplerpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.lhr.simplerpc.utils.SerializationUtil;

import java.util.List;

/**
 * @Author : ChinaLHR
 * @Date : Create in 9:30 2017/11/2
 * @Email : 13435500980@163.com
 *
 * RPC 解码器
 */
public class RpcDecoder extends ByteToMessageDecoder{

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes()<4) return;

        buf.markReaderIndex();
        int dataLength = buf.readInt();
        if (buf.readableBytes() < dataLength){
            buf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        buf.readBytes(data);
        list.add(SerializationUtil.deserialize(data,genericClass));
    }
}
