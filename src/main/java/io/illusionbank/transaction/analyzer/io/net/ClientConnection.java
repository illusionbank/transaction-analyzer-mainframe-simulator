package io.illusionbank.transaction.analyzer.io.net;

import java.nio.channels.SocketChannel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            emmit("finish");
            channel.shutdownInput();
            channel.shutdownOutput();
            ChannelHelper.close(channel);
            channel = null;
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
