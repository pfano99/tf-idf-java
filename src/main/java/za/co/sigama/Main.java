package za.co.sigama;

import za.co.sigama.service.read_documents.DocumentReader;
import za.co.sigama.service.tf_idf.TFIDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws ExecutionException, InterruptedException {

		DocumentReader documentReader = new DocumentReader();

		List<String> documents = documentReader.readAsync(new ArrayList<>(
						Arrays.asList(
										".\\docs\\doc1.txt",
										".\\docs\\doc2.txt"
						)
		)).get();

		TFIDF tfidf = new TFIDF();
		String document = tfidf.tfidf("sigama", documents);
		System.out.println("tf = " + document);

	}
}