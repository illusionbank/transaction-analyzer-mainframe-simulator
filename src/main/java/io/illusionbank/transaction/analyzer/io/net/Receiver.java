
package io.illusionbank.transaction.analyzer.io.net;

@FunctionalInterface
public interface Receiver {
    void onReceive(ClientConnection connection, String data);
}
