package za.co.sigama.service.read_documents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DocumentReader {
	public String read(String filePath) throws IOException {
		return Files.readString(Paths.get(filePath));
	}

	public CompletableFuture<List<String>> readAsync(List<String> filePaths) {
		List<CompletableFuture<String>> futures = new ArrayList<>();
		for ( String filePath : filePaths ) {
			CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
				try {
					return Files.readString(Path.of(filePath));
				} catch ( Exception e ) {
					throw new RuntimeException("Failed to read file: " + filePath, e);
				}
			});
			futures.add(future);
		}
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
									 .thenApply(v -> {
										 List<String> results = new ArrayList<>();
										 for ( CompletableFuture<String> future : futures ) {
											 results.add(future.join());
										 }
										 return results;
									 });
	}

}
