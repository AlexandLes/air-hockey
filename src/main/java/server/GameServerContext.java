package server;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameServerContext {
    private Channel playerOneChannel;
    private Channel playerTwoChannel;
}
