package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.Setter;
import model.Ball;
import model.Player;

@Getter
@Setter
public class GameClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Player) {
            System.out.println("Player message recieved");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Виняток під час взаємодії з сервером
        cause.printStackTrace();
        ctx.close();
    }

    public void sendPlayerMovement(Player player) {
        if (ctx != null) {
            ctx.writeAndFlush(player);
        }
    }

    public void sendBallMovement(Ball ball) {
        if (ctx != null) {
            ctx.writeAndFlush(ball);
        }
    }
}