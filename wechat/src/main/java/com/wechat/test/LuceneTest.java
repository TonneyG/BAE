package com.wechat.test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTest {
	private String indexDir;
	private String fieldName = "content";
	private IndexReader indexReader;
	
	public void initParam(){
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/env.properties"));
			String osName = System.getProperty("os.name");
			if(osName.equals("Windows 8")){
				indexDir = prop.getProperty("indexDir_win8");
			}else if(osName.equals("Windows 7")){
				indexDir = prop.getProperty("indexDir_win7");
			}
			System.out.println(indexDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public IndexSearcher getIndexSearcher(){
		try {
			Directory directory = FSDirectory.open(new File(indexDir));
			if(indexReader == null){
				indexReader = DirectoryReader.open(directory); 
			}else{
				IndexReader newReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader);
				if(newReader != null){
					indexReader.close();
					indexReader = newReader;
				}
			}
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			return indexSearcher;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void searchIndex(String sentence,Analyzer analyzer){
		try {
			Directory directory = FSDirectory.open(new File(indexDir));
			//IndexReader reader = IndexReader.open(directory);
			IndexSearcher searcher = getIndexSearcher();
			QueryParser parser = new QueryParser(Version.LUCENE_47,fieldName,analyzer);
			Query query = parser.parse(sentence);
			System.out.println("查询语句："+query.toString());
			TopDocs topDocs = searcher.search(query, 10);
			ScoreDoc[] scoreDoc = topDocs.scoreDocs;
			System.out.println("共索引到 "+topDocs.totalHits+" 条匹配结果");
			for(ScoreDoc sd : scoreDoc){
				//根据id获取document
				Document d = searcher.doc(sd.doc);
				System.out.println(d.get(fieldName)+" score:"+sd.score);
				//查看文档得分解析
				System.out.println(searcher.explain(query, sd.doc));
			}
			indexReader.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public static void main(String[] args) {
		Properties props = System.getProperties();
		System.out.println(props.getProperty("os.name"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("user.name"));
		LuceneTest lt = new LuceneTest();
		lt.initParam();
	}
}
