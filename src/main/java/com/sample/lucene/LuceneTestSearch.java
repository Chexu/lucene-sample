package com.sample.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.TopDocs;

import com.sample.lucene.model.Address;
import com.sample.lucene.utils.LuceneConstants;
import com.sample.lucene.utils.LuceneIndexer;
import com.sample.lucene.utils.LuceneSearcher;

public class LuceneTestSearch {
	
	static LuceneIndexer indexer;
	static LuceneSearcher searcher;
	static final String TEXT = "Lucene is Simple & Popular yet Powerful JAVA-based search library. xyz@example.com";
	
	static{
		try {
			searcher = new LuceneSearcher(LuceneConstants.INDEX_DIRECTORY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		try {
//			createIndex();
			searchIndexUsingQuery(LuceneConstants.COUNTRY_FIELD +":UK");
			searchIndexUsingWildCardQuery(LuceneConstants.CONTENTS, "*gram");
			searchIndexUsingPhraseQuery(LuceneConstants.CONTENTS, "edgar", "street", 2);
//			searchIndexUsingRangeQuery(LuceneConstants.CONTENTS, "10", "795", true);
			searchIndexUsingFuzzyQuery(LuceneConstants.CONTENTS, "tuxson");
			searchIndexUsingPrefixQuery(LuceneConstants.CONTENTS, "us");
			searchIndexUsingSpanNearQuery(LuceneConstants.CONTENTS, "smallsys", "arizona", 5, true);
//			displayTokenUsingAnalyzer(TEXT, new StandardAnalyzer());
//			displayTokenUsingAnalyzer(TEXT, new WhitespaceAnalyzer());
//			displayTokenUsingAnalyzer(TEXT, new StopAnalyzer());
//			displayTokenUsingAnalyzer(TEXT, new SimpleAnalyzer());
//			displayTokenUsingAnalyzer(TEXT, new KeywordAnalyzer());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			searcher.close();
		}
	}
	public static void displayTokenUsingAnalyzer(String text, Analyzer analyzer) throws IOException{
		TokenStream tokenStream = analyzer.tokenStream(LuceneConstants.CONTENTS, new StringReader(text));
		Token token = tokenStream.next();
		System.out.println("\n");
		System.out.println("----------------"+analyzer.getClass().getSimpleName()+"----------------");
		while(token != null) {
			  System.out.print("[" + token.termText() + "] ");
			  token = tokenStream.next();
		}
	}

	private static void createIndex() throws Exception {
		indexer = new LuceneIndexer(LuceneConstants.INDEX_DIRECTORY);
		int numIndexed = 0;
		long startTime = System.currentTimeMillis();
//		numIndexed = indexer.createIndexForFile(LuceneConstants.DATA_DIRECTORY, new TextFileFilter());
		numIndexed = indexer.createIndexForObject(getAddresses());
		long endTime = System.currentTimeMillis();
		indexer.close();
		System.out.println(numIndexed + " Records indexed, time taken: " + (endTime - startTime) + " ms");
		System.out.println();
	}
	
	private static void searchIndexUsingQuery(String searchQuery) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------Query----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingQuery(searchQuery);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static void searchIndexUsingWildCardQuery(String whichField, String searchQuery) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------WildCardQuery----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingWildCardQuery(whichField, searchQuery);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static void searchIndexUsingRangeQuery(String whichField, String start, String end, boolean inclusive) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------RangeQuery----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingRangeQuery(whichField, start, end, inclusive);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static void searchIndexUsingPhraseQuery(String whichField, String string1, String string2, int slop) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------PhraseQuery----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingPhraseQuery(whichField, string1, string2, slop);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static void searchIndexUsingFuzzyQuery(String whichField, String searchQuery) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------FuzzyQuery----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingFuzzyQuery(whichField, searchQuery);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static void searchIndexUsingPrefixQuery(String whichField, String searchQuery) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------PrefixQuery----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingPrefixQuery(whichField, searchQuery);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static void searchIndexUsingSpanNearQuery(String whichField, String string1, String string2, int span, boolean isOrderRequired) throws IOException, ParseException {
		System.out.println();
		System.out.println("----------SpanNearQuery----------");
		
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.searchIndexUsingSpanNearQuery(whichField, string1, string2, span, isOrderRequired);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time : " + (endTime - startTime) + " ms");
		searcher.displayResults(searcher, hits);
		
	}
	private static List<Address> getAddresses(){
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address(1, "Mainstreet LA", "M. G. Road", "TUCSON", "Washington", "USA");
		addresses.add(address);
		address = new Address(2, "SMALLSYS INC", "795 E DRAGRAM", "TUKSON", "Arizona", "USA");
		addresses.add(address);
		address = new Address(3, "100 MAIN ST", "PO BOX 1022", "SEATTLE", "Washington", "USA");
		addresses.add(address);
		address = new Address(4, "14 Tottenham", "Court Road", "London", "England", "UK");
		addresses.add(address);
		address = new Address(5, "3 Edgar Buildings", "George Street", "Bath", "England", "UK");
		addresses.add(address);
		address = new Address(6, "Her Majesty The Queen", "Buckingham Palace", "TUXSON", "England", "UK");
		addresses.add(address);
		return addresses;
	}
}