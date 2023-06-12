package client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameClient {
    private final String host;
    private final int port;
    private GameClientHandler gameClientHandler;

    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            this.gameClientHandler = new GameClientHandler();
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new GameClientInitializer(this, gameClientHandler));

            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("Connected to server: " + host + ":" + port);

            Channel channel = f.channel();

            // Wait until the GameClientHandler is initialized

             this.gameClientHandler = channel.pipeline().get(GameClientHandler.class);

            // Wait for the connection to be closed
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
