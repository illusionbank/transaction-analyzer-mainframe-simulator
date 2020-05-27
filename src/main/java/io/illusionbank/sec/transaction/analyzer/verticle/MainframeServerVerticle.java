package io.illusionbank.sec.transaction.analyzer.verticle;

import java.util.List;

import io.illusionbank.common.enumeration.Books;
import io.illusionbank.common.mapper.BookMapper;
import io.illusionbank.sec.transaction.analyzer.repository.ClientRepositoty;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainframeServerVerticle extends AbstractVerticle {
	private BookMapper bookMapper = new BookMapper();
	private List<String> clients = new ClientRepositoty().getClients();
	
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		int port = 7171;
		
		NetServer server = vertx.createNetServer();
		
		server.exceptionHandler(exception-> {
			log.error("{}", exception);
		});
		
		server.connectHandler(socket-> {
			socket.exceptionHandler(exception-> {
				log.error("{}", exception);
			});
			socket.handler(book->handleReceive(book, socket));
		});
		
		server.listen(port, "localhost",  listenHandler->{
			if(listenHandler.succeeded()) {
				startPromise.complete();
				log.info("Server listening on port {}", port);
			} else {
				startPromise.fail("Erro on server");
				vertx.close();
				log.info("Error {}", listenHandler.cause());
			}
		});
	}
	
	private void handleReceive(Buffer book, NetSocket socket) {
		var bookCode = book.getString(0, 8);
		Buffer data = Buffer.buffer();
		if(bookCode.equals(Books.IBPAYCON.toString())) {
			data = processPayment(bookCode, book);
			
		} else if(bookCode.equals(Books.IBCTATRA.toString())) {
			data = processTransfer(bookCode, book);
			
		} else if(bookCode.equals(Books.IBCHABAC.toString())) { 
			
		} else {
			data = Buffer.buffer(bookMapper.serializeErrorBook(bookCode));
		}
		
		System.out.print(String.format("IN : %s", book));
		System.out.println(String.format("OUT: %s", data.toString()));
		socket.end(data);
	}
	
	private Buffer processPayment(String bookCode, Buffer book) {
		var paymentBook = bookMapper.loadPaymentBookfromBookOrNull(book.toString("UTF-8"));
		if(clients.contains(paymentBook.getClient())) {
			return Buffer.buffer(bookMapper.serializeResponseBookRamdom(bookCode));
		} else {
			return Buffer.buffer(bookMapper.serializeErrorBook(bookCode));
		}
	}
	
	private Buffer processTransfer(String bookCode, Buffer book) {
		var transferBook = bookMapper.loadTransferBookfromBookOrNull(book.toString("UTF-8"));
		if(clients.contains(transferBook.getClient())) {
			return Buffer.buffer(bookMapper.serializeResponseBookRamdom(bookCode));
		} else {
			return Buffer.buffer(bookMapper.serializeErrorBook(bookCode));
		}
	}

}
