package com.wechat.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTest {
	private String indexDir = "E:/Search Engines/Lucene/Index repository";
	private String fieldName = "content";
	
	public void createIndex(Analyzer analyzer) throws IOException{
		String[] array = {
				"【苏宁易购】国内领先的综合网上购物平台，商品涵盖手机、电脑、彩电、超市、母婴、等品类。全国联保、正品行货，支持门店自提，依托线下近4000家苏宁门店，为用户提供便利的O2O服务。",
				"苏宁易购和阿里巴巴成立战略合作伙伴",
				"苏宁易购的竞争对手是阿里和京东"};
		Directory directory = FSDirectory.open(new File(indexDir));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,analyzer);
		IndexWriter indexWriter = new IndexWriter(directory,config);
		for(String str : array){
			Document doc = new Document();
			doc.add(new TextField(fieldName,str,Field.Store.YES));
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		indexWriter.close();
		directory.close();
	}
	
	public void deleteFile(File file){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f : files){
				deleteFile(f);
			}
		}
		file.delete();
	}
}
