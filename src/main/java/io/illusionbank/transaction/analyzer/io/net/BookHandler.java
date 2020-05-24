package io.illusionbank.transaction.analyzer.io.net;


@FunctionalInterface
public interface BookHandler {
	void handle(ClientConnection client, String book);
}
