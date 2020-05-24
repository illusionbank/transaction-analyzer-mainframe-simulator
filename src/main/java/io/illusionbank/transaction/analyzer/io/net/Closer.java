package io.illusionbank.transaction.analyzer.io.net;

@FunctionalInterface
public interface Closer {
    void onClose(ClientConnection connection, Couse couse);
}
