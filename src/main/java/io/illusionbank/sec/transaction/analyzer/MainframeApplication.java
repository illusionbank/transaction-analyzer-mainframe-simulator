package io.illusionbank.sec.transaction.analyzer;

import io.illusionbank.sec.transaction.analyzer.verticle.MainframeServerVerticle;
import io.vertx.core.Launcher;

public class MainframeApplication {

	public static void main(String[] args) {
		Launcher.executeCommand("run", MainframeServerVerticle.class.getName());
	}

}
