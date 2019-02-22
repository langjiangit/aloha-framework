package com.doopp.gauss.server.handle;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.*;

public abstract class AbstractWebSocketServerHandle implements WebSocketServerHandle {

    @Override
    public void onConnect(Channel channel) {
        channel.writeAndFlush(new TextWebSocketFrame("connected " + channel.id()));
    }

    @Override
    public void onMessage(WebSocketFrame frame, Channel channel) {
    }

    @Override
    public void onTextMessage(TextWebSocketFrame frame, Channel channel) {
        channel.writeAndFlush(new TextWebSocketFrame("message " + channel.id()));
    }

    @Override
    public void onBinaryMessage(BinaryWebSocketFrame frame, Channel channel) {
        channel.writeAndFlush(Unpooled.buffer(0));
    }

    @Override
    public void onPingMessage(PingWebSocketFrame frame, Channel channel) {
        channel.writeAndFlush(new PongWebSocketFrame());
    }

    @Override
    public void onPongMessage(PongWebSocketFrame frame, Channel channel) {
        channel.writeAndFlush(new PingWebSocketFrame());
    }

    @Override
    public void close(CloseWebSocketFrame frame, Channel channel) {
        if (channel!=null) {
            try {
                channel.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
