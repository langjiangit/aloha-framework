package com.doopp.gauss.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame socketFrame) throws Exception {

        if (socketFrame instanceof TextWebSocketFrame) {
            handleText(ctx, (TextWebSocketFrame) socketFrame);
        }
        else if (socketFrame instanceof BinaryWebSocketFrame) {
            handleBinary(ctx, (BinaryWebSocketFrame) socketFrame);
        }
        else {
            String message = "unsupported frame type: " + socketFrame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }

//        Channel incoming = ctx.channel();
//        logger.info("\n 1) {}", incoming);
//        for (Channel channel : channels) {
//            if (channel != incoming){
//                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + ((TextWebSocketFrame) socketFrame).text()));
//            } else {
//                channel.writeAndFlush(new TextWebSocketFrame("[you]" + ((TextWebSocketFrame) socketFrame).text() ));
//            }
//        }
    }

    private void handleText(ChannelHandlerContext ctx, TextWebSocketFrame socketFrame) {
        logger.info("\n 1) ChannelHandlerContext >>> {} \n 2) TextWebSocketFrame >>> {}", ctx, socketFrame);
//        ByteBuf buf = socketFrame.content();
//        System.out.println(buf.array().length); //16M的array字节数组大小！？
//
//        // Send the uppercase string back.
        String text = socketFrame.text();
        socketFrame.retain();
        logger.info("\n 3) {} \n 4) {}", ctx.channel(), text);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("hello boy"));
//
//        //Request wsRequest = JSONUtil.fromJSON(request, JsonRequest.class);
        //Request request = JSON.parseObject(text, JsonRequest.class);
        // handle(ctx, request);
    }

    private void handleBinary(ChannelHandlerContext ctx, BinaryWebSocketFrame socketFrame) {
        System.out.print("\n" + socketFrame);
//        ByteBuf buf = frame.content();
//        System.out.println(buf.array().length); //16M的array字节数组大小！？
//
//        byte[] data = new byte[buf.readableBytes()];
//        buf.readBytes(data);
////    	System.out.println(data.length);
////    	String text = buf.toString(CharsetUtil.UTF_8);
////    	System.out.println(new String(data, CharsetUtil.UTF_8));
//
//        RequestProto proto = null;
//        try {
////			TestProto proto = TestProto.parseFrom(data);
////			System.out.println(proto.getId()+", "+proto.getName());
////
////			Player player =  getPlayer(ctx);
////			player.write(proto);
//
////    		JsonProto proto = JsonProto.parseFrom(data);
////    		System.out.println(proto.getData());
//
//            proto = RequestProto.parseFrom(data);
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//
//        Request request  = new ProtobufRequest(proto);
//        handle(ctx, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client: {} 异常", incoming.remoteAddress());
        cause.printStackTrace();
        ctx.close();
    }
}
