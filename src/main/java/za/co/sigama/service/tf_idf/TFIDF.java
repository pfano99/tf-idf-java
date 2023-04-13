package za.co.sigama.service.tf_idf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TFIDF {

	private int termOccurrenceCount = 0;

	private int wordsCount(String text) {
		Map<String, Integer> counter = new HashMap<>();

		String[] split = text.split("[\\s\\n]+");
		for ( String word : split ) {
			if ( counter.get(word) != null ) {
				int p = counter.get(word);
				counter.put(word, ++p);
			} else {
				counter.put(word, 1);
			}
		}

		return counter.values().stream().mapToInt(Integer::intValue).sum();
	}

	private float termFrequency(String term, String documentText) {

		Pattern pattern = Pattern.compile(term, Pattern.CASE_INSENSITIVE);

		Matcher termMatcher = pattern.matcher(documentText);
		long termOccurrences = termMatcher.results().count();

		if ( termOccurrences > 0 ) {
			this.termOccurrenceCount++;
		}

		long totalWordsCount = wordsCount(documentText);
		return (float) termOccurrences / totalWordsCount;
	}

	private float inverseDocumentFrequency(int numDocuments) {
		return (float) Math.log10((double) numDocuments / this.termOccurrenceCount);
	}

	public String tfidf(String term, List<String> corpus) {

//		Document, Frequency
		Map<String, Float> data = new HashMap<>();

//		Calculating tf - for each document
		for ( String doc : corpus ) {
			float frequency = termFrequency(term, doc);
			data.put(doc, frequency);
		}

//		Calculating idf
		float inverse = inverseDocumentFrequency(corpus.size());

//		Calculating tf-idf
		float highest = 0;
		String document = null;
		for ( String key : data.keySet() ) {
			if ( data.get(key) > highest ) {
				highest = data.get(key);
				document = key;
			}
		}
		this.termOccurrenceCount = 0;
		return document;
	}
}
