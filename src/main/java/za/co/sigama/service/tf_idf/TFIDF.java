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

	public float tfidf(String term, List<String> documents) {
		String document = null;
		float high = 0;

		for ( String doc : documents ) {

			float frequency = termFrequency(term, doc);
			System.out.println("frequency = " + frequency);
//			System.out.println("frequency = " + frequency);
//			float inverse = inverseDocumentFrequency(documents.size());
//			System.out.println("inverse = " + inverse);
//
//			float res = frequency * inverse;
//			System.out.println("res = " + res);
//			if ( res > high ) {
//				high = res;
//				document = doc;
//			}
		}
		float inverse = inverseDocumentFrequency(documents.size());
		System.out.println("inverse = " + inverse);
		System.out.println(document);
		this.termOccurrenceCount = 0;
		return high;
	}
}
