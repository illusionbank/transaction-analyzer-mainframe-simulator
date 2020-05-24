package io.illusionbank.transaction.analyzer.io.net;

@FunctionalInterface
public interface Acepter {
    void onAcept(ClientConnection connection);
}
