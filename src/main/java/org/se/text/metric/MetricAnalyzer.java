package org.se.text.metric;

import org.se.text.analysis.NounTerm;
import org.se.text.analysis.TermCollection;
import org.se.text.analysis.TermVariations;
import org.se.text.analysis.VerbTerm;

/**
 * @author Jakob Kautz
 */
public class MetricAnalyzer {
	public static int metricsGet(String content, TermCollection terms) {
		// Find average length for sentences and hyphen in order to determine text speed
		int averageH = averageHyphen(terms);
		int averageS = averageSentence(content);

		/*
		 * Define bpm for several sentence/hyphen length combinations
		 * 1-9 Words is short sentence
		 * 10-18 words is average long sentence
		 * 18+ words is long sentence
		 * Hyphen
		 * 1 hyphen is short word
		 * 2/3 hyphen is average word
		 * 4+ hyphen is long word
		 *
		 * bpm (hyphen sentence)
		 * 60bpm long long
		 * 80bpm long average
		 * 100 bpm long short
		 * 120 bpm average long
		 * 140 bpm average average
		 * 160 bpm average short
		 * 180 bpm short short
		 */
		if (averageH <= 1 && averageS <= 9) return 180;
		else if (averageH <= 3) {
			if (averageS <= 9) return 160;
			else if (averageS <= 18) return 140;
			else return 120;
		} else {
			if (averageS <= 9) return 180;
			else if (averageS <= 18) return 80;
			else return 60;
		}
	}

	/*
	 * Calculates average syllable length
	 */
	public static int averageHyphen(TermCollection terms) {
		int totalHyphens = 0;
		int termsAmount = 0;
		for (TermVariations<NounTerm> variations : terms.getNouns()) {
			totalHyphens += variations.getRandomTerm().getSyllableAmount() * variations.getFrequency();
			termsAmount += variations.getFrequency();
		}
		for (TermVariations<VerbTerm> variations : terms.getVerbs()) {
			totalHyphens += variations.getRandomTerm().getSyllableAmount() * variations.getFrequency();
			termsAmount += variations.getFrequency();
		}

		if (termsAmount == 0) return 0;
		return totalHyphens / termsAmount;
	}

	/*
	 * Calculates average sentence length
	 */
	public static int averageSentence(String content) {
		int[] termAndSentences = WordCounter.countWords(content);
		int wordsTotal = termAndSentences[0];
		int cTcSTotal = termAndSentences[1];
		return wordsTotal / cTcSTotal;
	}
}
