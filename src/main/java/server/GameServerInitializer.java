package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class GameServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
            GameServerHandler gameServerHandler = new GameServerHandler();
            ch.pipeline().addLast(gameServerHandler);
    }
}
