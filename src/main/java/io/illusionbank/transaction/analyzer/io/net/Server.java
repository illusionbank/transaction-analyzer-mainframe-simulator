
package io.illusionbank.transaction.analyzer.io.net;

public interface Server {
    void onForceClose(Closer closer);
    void onAcept(Acepter acepter);
    void onReceive(Receiver receiver);
    
    boolean isRunning();
    void listener(int port);
    void stop();
    
}
