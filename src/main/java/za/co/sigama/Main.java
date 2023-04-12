package za.co.sigama;

import za.co.sigama.service.read_documents.DocumentReader;
import za.co.sigama.service.tf_idf.TFIDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

		DocumentReader documentReader = new DocumentReader();

		List<String> documents = new ArrayList<>();

		long startTime = System.nanoTime();
		documentReader.read(".\\docs\\doc1.txt");
		documentReader.read(".\\docs\\doc2.txt");
		documentReader.read(".\\docs\\doc2.txt");
		documentReader.read(".\\docs\\doc2.txt");
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // duration in milliseconds

		System.out.println("Execution time: " + duration + " ms");

		startTime = System.nanoTime();
		CompletableFuture<List<String>> listCompletableFuture = documentReader.readAsync(new ArrayList<>(Arrays.asList(".\\docs\\doc1.txt",
						".\\docs\\doc2.txt", ".\\docs\\doc2.txt", ".\\docs\\doc2.txt")));
		endTime = System.nanoTime();
		listCompletableFuture.get();
		duration = (endTime - startTime) / 1000000; // duration in milliseconds

		System.out.println("Execution time: " + duration + " ms");

//		TFIDF tfidf = new TFIDF();
//		float tf = tfidf.tfidf("example", documents);
//		System.out.println("tf = " + tf);

	}
}