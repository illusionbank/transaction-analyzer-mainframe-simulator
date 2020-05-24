package io.illusionbank.transaction.analyzer.io.net;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientConnection {
    public final String id;
    private SocketChannel channel;
    
    public ClientConnection(String id, SocketChannel channel) {
        this.id = id;
        this.channel = channel;
    }

    public void emmit(String book) {
    	ChannelHelper.send(channel, book);
    }

    public void close() {
        try {
            channel.shutdownInput();
            channel.shutdownOutput();
            ChannelHelper.close(channel);
            channel = null;
        } catch (IOException ex) {
            log.error("IO error {}", ex);
        }
    }
    
}
