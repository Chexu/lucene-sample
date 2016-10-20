package com.sample.lucene.utils;


import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneSearcher {
	private IndexSearcher indexSearcher;
	private QueryParser queryParser;
	
	public LuceneSearcher(String indexDirPath) throws IOException{
		Directory indexDirectory = FSDirectory.getDirectory(new File(indexDirPath));
		indexSearcher = new IndexSearcher(indexDirectory);
		Analyzer analyzer = new StandardAnalyzer();
		queryParser = new QueryParser(LuceneConstants.CONTENTS, analyzer);
	}
	
	public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}

	public void close() throws IOException {
		indexSearcher.close();
	}
	
	public TopDocs searchIndexUsingQuery(String searchQuery)throws IOException, ParseException{
		Query query = queryParser.parse(searchQuery);
		System.out.println("Query : "+ query.toString());
		return indexSearcher.search(query, null, LuceneConstants.MAX_SEARCH);
	}
	public TopDocs searchIndexUsingWildCardQuery(String whichField, String searchQuery)throws IOException, ParseException{
		Term term = new Term(whichField, searchQuery);
		Query query = new WildcardQuery(term);
		System.out.println("WildCardQuery : "+ query.toString());
		return indexSearcher.search(query, null, LuceneConstants.MAX_SEARCH);
	}
	public TopDocs searchIndexUsingRangeQuery(String whichField, String start, String end, boolean inclusive) 
							throws IOException, ParseException {
		Term startTerm = new Term(whichField, start);
		Term endTerm = new Term(whichField, end);
		Query query = new RangeQuery(startTerm, endTerm, inclusive);
		System.out.println("RangeQuery : "+ query.toString());
		return indexSearcher.search(query, null, LuceneConstants.MAX_SEARCH);
	}
	
	public TopDocs searchIndexUsingPhraseQuery(String whichField, String string1, String string2, int slop) 
				throws IOException, ParseException {
		Term term1 = new Term(whichField, string1);
		Term term2 = new Term(whichField, string2);
		PhraseQuery phraseQuery = new PhraseQuery();
		phraseQuery.add(term1, 0);
		phraseQuery.add(term2, 1);
		phraseQuery.setSlop(slop);
		System.out.println("PhraseQuery : "+ phraseQuery.toString());
		return indexSearcher.search(phraseQuery, null, LuceneConstants.MAX_SEARCH);
	}
	public TopDocs searchIndexUsingFuzzyQuery(String whichField, String searchQuery)throws IOException, ParseException{
		Term term = new Term(whichField, searchQuery);
		Query query = new FuzzyQuery(term);
		System.out.println("FuzzyQuery : "+ query.toString());
		return indexSearcher.search(query, null, LuceneConstants.MAX_SEARCH);
	}
	public TopDocs searchIndexUsingPrefixQuery(String whichField, String searchQuery)throws IOException, ParseException{
		Term term = new Term(whichField, searchQuery);
		Query query = new PrefixQuery(term);
		System.out.println("PrefixQuery : "+ query.toString());
		return indexSearcher.search(query, null, LuceneConstants.MAX_SEARCH);
	}
	public TopDocs searchIndexUsingSpanNearQuery(String whichField, String string1, String string2, int span, boolean isOrderRequired) 
			throws IOException, ParseException {
		SpanTermQuery term1 = new SpanTermQuery(new Term(LuceneConstants.CONTENTS, string1));
		SpanTermQuery term2 = new SpanTermQuery(new Term(LuceneConstants.CONTENTS, string2));
		SpanNearQuery spanNearQuery = new SpanNearQuery(new SpanTermQuery[] {term1, term2}, span, isOrderRequired);
		System.out.println("SpanNearQuery : "+ spanNearQuery.toString());
		return indexSearcher.search(spanNearQuery, null, LuceneConstants.MAX_SEARCH);
	}
	public void displayResults(LuceneSearcher searcher, TopDocs hits) throws CorruptIndexException, IOException {
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("Address :: " + doc.get(LuceneConstants.CONTENTS));
		}
	}
}
