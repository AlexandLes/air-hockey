package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.Setter;
import model.Ball;
import model.Player;
import service.GameLogicService;

@Getter
@Setter
public class GameServerHandler extends ChannelInboundHandlerAdapter {
    private GameLogicService gameLogicService = new GameLogicService();
    private GameServerContext serverContext = GameServer.getInstance().getServerContext();
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (serverContext.getPlayerOneChannel() == null) {
            serverContext.setPlayerOneChannel(ctx.channel());
        } else if (serverContext.getPlayerTwoChannel() == null) {
            serverContext.setPlayerTwoChannel(ctx.channel());
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        if (ctx.channel() == serverContext.getPlayerOneChannel()) {
            serverContext.setPlayerOneChannel(null);
        } else if (ctx.channel() == serverContext.getPlayerTwoChannel()) {
            serverContext.setPlayerTwoChannel(null);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (ctx.channel() == serverContext.getPlayerOneChannel()) {
            if (msg instanceof Player) {
                Player playerMovementMessage = (Player) msg;
                receiveSecondPlayerPositions(playerMovementMessage);
                serverContext.getPlayerTwoChannel().writeAndFlush(playerMovementMessage);
            } else if (msg instanceof Ball) {
                Ball ballMovementMessage = (Ball) msg;
                receiveBallPosition(ballMovementMessage);
                serverContext.getPlayerTwoChannel().writeAndFlush(ballMovementMessage);
            }
        } else if (ctx.channel() == serverContext.getPlayerTwoChannel()) {
            if (msg instanceof Player) {
                Player playerMovementMessage = (Player) msg;
                receiveSecondPlayerPositions(playerMovementMessage);
                serverContext.getPlayerOneChannel().writeAndFlush(playerMovementMessage);
            } else if (msg instanceof Ball) {
                Ball ballMovementMessage = (Ball) msg;
                receiveBallPosition(ballMovementMessage);
                serverContext.getPlayerOneChannel().writeAndFlush(ballMovementMessage);
            }
        }
    }

    public void receiveSecondPlayerPositions(Player player) {
        gameLogicService.updateSecondPlayerPosition(player);
    }
    public void receiveBallPosition(Ball ball) {
        // Оновлюємо позицію м'яча
        gameLogicService.updateBallPosition(ball);
    }
}

