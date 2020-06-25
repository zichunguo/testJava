package com.guo.test.netty;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("****** channelRead ******");
		ByteBuf buf = (ByteBuf) msg;
		String recieved = getMessage(buf);
		System.out.println("客户端收到服务器消息：" + recieved);
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("****** channelActive ******");
		String msg = "你好，服务器";
		ctx.writeAndFlush(getSendByteBuf(msg));
		System.out.println("客户端发送消息：" + msg);
	}
	
	/**
     * 从ByteBuf中获取信息 使用UTF-8编码返回
     */
    private String getMessage(ByteBuf buf) {
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将字符串转为ByteBuf，用于发送消息
     * @param message 发送的字符串信息
     * @return
     * @throws UnsupportedEncodingException
     */
    private ByteBuf getSendByteBuf(String message) throws UnsupportedEncodingException {
        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);
        return pingMessage;
    }

}
