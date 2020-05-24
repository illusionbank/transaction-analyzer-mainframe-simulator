package io.illusionbank.transaction.analyzer.io.net;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MainframeServer {
	private Map<String, BookHandler> books = new HashMap<>();
	private BaseServer server = new BaseServer();
	
	private Receiver receiver = (client, book) -> {
        
        String bookCode = book.substring(0, 7);
        if(books.containsKey(bookCode)) {
        	books.get(bookCode).handle(client, book);
        	
        } else {
            log.error("BOOK não encontrado {}", book);
            client.emmit("00000BNF");
            
        }
        bookCode = null;
        book = null;
    };
    
    private Acepter acepter = (client) -> {
        System.out.println("nova conexao");
    };
    
    private void init() {
        server.onReceive(receiver);
        server.onAcept(acepter);
    }
    
    public void on(String book, BookHandler handler) {
    	books.put(book, handler);
    }
    
    public void listener(int port) {
        server.listener(port);
    }

    public Map<String, ClientConnection> connections() {
        return server.connections();
    }

    public void close() {
        server.stop();
        receiver = null;
        acepter = null;
        if(null != books) {
        	books.clear();
        }
        books = null;
        server = null;
    }
	
}
