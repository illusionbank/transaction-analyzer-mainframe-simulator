package io.illusionbank.transaction.analyzer;

import java.util.Arrays;
import java.util.List;

import io.illusionbank.common.book.ClientNotFoundBook;
import io.illusionbank.common.book.NotFoundBook;
import io.illusionbank.common.book.PaymentBook;
import io.illusionbank.common.book.TransferBook;
import io.illusionbank.common.exception.InvalidBookException;
import io.illusionbank.common.mapper.BookMapper;
import io.illusionbank.transaction.analyzer.io.net.MainframeServer;

public class MainframeSimulator {

	public static void main(String[] args) {
		var clientNotFoundBook = new ClientNotFoundBook();
		var notFoundBook = new NotFoundBook();
		
		var bookMapper = new BookMapper();
		var clients = getClients();
		
		var server =  new MainframeServer();
		
		server.on("AFCTATRAN", (client, book)-> {
            TransferBook transferBook = null;
			try {
				transferBook = bookMapper.loadTransferBookfromBookOrNull(book);
				if(clients.contains(transferBook.getClient())) {
					client.emmit("BOOK com status ALEATORIO");
				} else {
					client.emmit(bookMapper.serializeClientNotFoundBook(clientNotFoundBook));
				}
			} catch (InvalidBookException e) {
				client.emmit(bookMapper.serializeNotFoundBook(notFoundBook));
			}
			client.close();
        });
        
		server.on("AFPAGCON", (client, book)-> {
			
			PaymentBook paymentBook = null;
			try {
				paymentBook = bookMapper.loadPaymentBookfromBookOrNull(book);
				if(clients.contains(paymentBook.getClient())) {
					client.emmit("BOOK com status ALEATORIO");
				} else {
					client.emmit(bookMapper.serializeClientNotFoundBook(clientNotFoundBook));
				}
			} catch (InvalidBookException e) {
				client.emmit(bookMapper.serializeNotFoundBook(notFoundBook));
			}
			client.close();
        });
        
		server.listener(7070);
	}
	
	
	
	private static List<String> getClients() {
		
		return Arrays.asList(
			"95993895599",
			"99653923898",
			"63989323324",
			"69682234884",
			"35684379244",
			"57264454824"
		);
	}

}
