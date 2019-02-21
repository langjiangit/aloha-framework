package com.doopp.gauss.server.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public abstract class AbstractWebSocketServerHandle implements WebSocketServerHandle {

    @Override
    public void onConnect(Channel channel) {
        channel.writeAndFlush(new TextWebSocketFrame("connected " + channel.id()));
    }

    @Override
    public void onMessage(Channel channel) {

    }

    @Override
    public String onTextMessage(Channel channel) {
        return null;
    }

    @Override
    public ByteBuf onBinaryMessage(Channel channel) {
        return Unpooled.buffer(0);
    }

    @Override
    public ByteBuf onPingMessage(Channel channel) {
        return Unpooled.buffer(0);
    }

    @Override
    public ByteBuf onPongMessage(Channel channel) {
        return Unpooled.buffer(0);
    }

    @Override
    public void close(Channel channel) {
        if (channel!=null) {
            try {
                channel.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Channel channel) {
        this.close(channel);
    }
}
