package com.sample.lucene.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.sample.lucene.model.Address;

public class LuceneIndexer {
	private IndexWriter writer;

	public LuceneIndexer(String indexDirPath) throws Exception {
		try {
			// this directory will contain the indexes
			Directory indexDirectory = FSDirectory.getDirectory(new File(indexDirPath));
			Analyzer analyzer = new StandardAnalyzer();
			writer = new IndexWriter(indexDirectory, analyzer, true);
		}catch(IOException e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer() :: "+e.getMessage(), e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer() :: "+e.getMessage(), e);
		}
	}

	public void close() throws Exception {
		try {
			writer.close();
		}catch(CorruptIndexException e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.close() :: "+e.getMessage(), e);
		}catch(IOException e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.close() :: "+e.getMessage(), e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.close() :: "+e.getMessage(), e);
		}
	}

	private Document getDocument(File file) throws Exception{
		Document document = new Document();
		try {
			SimpleDateFormat sdfDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			Field fileContentField = new Field(LuceneConstants.CONTENTS, new FileReader(file));
			Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES, Field.Index.UN_TOKENIZED);
			Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES, Field.Index.UN_TOKENIZED);
			Field lastModifiedField = new Field(LuceneConstants.LAST_MODIFIED, sdfDateFormat.format(new Date(file.lastModified())), Field.Store.YES, Field.Index.UN_TOKENIZED);
			System.out.println(sdfDateFormat.format(new Date(file.lastModified())));
			document.add(fileContentField);
			document.add(fileNameField);
			document.add(filePathField);
			document.add(lastModifiedField);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.getDocument() :: "+e.getMessage(), e);
		}
		return document;
	}

	private void indexFile(File file) throws Exception {
		try {
			System.out.println("Indexing file :: " + file.getCanonicalPath());
			Document document = getDocument(file);
			writer.addDocument(document);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.indexFile() :: "+e.getMessage(), e);
		}
	}

	public int createIndexForFile(String dataDirPath, FileFilter filter) throws Exception {
		try {
			// get all files in the data directory
			File[] files = new File(dataDirPath).listFiles();
	
			for (File file : files) {
				if (!file.isDirectory() && !file.isHidden() && file.exists()
						&& file.canRead() && filter.accept(file)) {
					indexFile(file);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.createIndex() :: "+e.getMessage(), e);
		}
		return writer.docCount();
	}
	
	private Document getDocument(Address address) throws Exception{
		Document document = new Document();
		try {
			Field addrIdField = new Field(LuceneConstants.ADDRESS_ID, 
										  String.valueOf(address.getAddrId()),
										  Field.Store.YES, 
										  Field.Index.NO);
			Field addrLineOneField = new Field(LuceneConstants.ADDR_LINE_ONE_FIELD, 
											   address.getAddrLineOne(), 
											   Field.Store.YES, 
											   Field.Index.TOKENIZED);
			Field addrLineTwoField = new Field(LuceneConstants.ADDR_LINE_ONE_FIELD, 
											   address.getAddrLineTwo(), 
											   Field.Store.YES, 
											   Field.Index.TOKENIZED);
			Field cityField = new Field(LuceneConstants.CITY_FIELD, 
									    address.getCity(), 
									    Field.Store.YES,
									    Field.Index.TOKENIZED);
			Field stateField = new Field(LuceneConstants.STATE_FIELD, 
										 address.getState(),
										 Field.Store.YES,
										 Field.Index.TOKENIZED);
			Field countryField = new Field(LuceneConstants.COUNTRY_FIELD, 
										   address.getCountry(),
										   Field.Store.YES,
										   Field.Index.TOKENIZED);
			Field contentField = new Field(LuceneConstants.CONTENTS, 
										   address.toString(),
										   Field.Store.YES,
										   Field.Index.TOKENIZED);
			document.add(addrIdField);
			document.add(addrLineOneField);
			document.add(addrLineTwoField);
			document.add(cityField);
			document.add(stateField);
			document.add(countryField);
			document.add(contentField);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.getDocument() :: "+e.getMessage(), e);
		}
		return document;
	}
	
	private void indexObject(Address address) throws Exception {
		try {
			System.out.println("Indexing Address Object :: " + address.toString());
			Document document = getDocument(address);
			writer.addDocument(document);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.indexFile() :: "+e.getMessage(), e);
		}
	}
	public int createIndexForObject(List<Address> addresses) throws Exception {
		try {
			for (Address address : addresses) {
				indexObject(address);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception in LuceneIndexer.createIndexForObject() :: "+e.getMessage(), e);
		}
		return writer.docCount();
	}
}
