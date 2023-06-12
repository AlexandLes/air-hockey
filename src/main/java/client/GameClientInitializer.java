package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameClientInitializer extends ChannelInitializer<SocketChannel> {
    private GameClient gameClient;
    private GameClientHandler gameClientHandler;

    public GameClientInitializer(GameClient gameClient, GameClientHandler gameClientHandler) {
        this.gameClient = gameClient;
        this.gameClientHandler = gameClientHandler;
        this.gameClient.setGameClientHandler(gameClientHandler); // Set the gameClientHandler on the gameClient
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        pipeline.addLast(gameClientHandler);
    }
}
