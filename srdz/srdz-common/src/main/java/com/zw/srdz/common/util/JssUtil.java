package com.zw.srdz.common.util;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcloud.jss.Credential;
import com.jcloud.jss.JingdongStorageService;
import com.jcloud.jss.client.ClientConfig;

public class JssUtil {

	private static final  String ACCESS_KEY = "cf9bb99cad1740d0886f46861cd8da32";
	private static final  String SECRET_KEY = "6323f9254f764c4e9604d3982c3d9b06dBpLPAs4";
	private static final  int 	CONNECTION_TIMEOUT = 1000*5;
	private static final  int   	MAX_CONNECTIONS    = 10;
	private static final  int 	SOCKET_TIMEOUT     = 1000*10;
	private static final  String ENDPOINT	       = "storage.jcloud.com";
	private static final  String BUCKET		   = "topic.com";
	
	private static JingdongStorageService jss = null;
	
	private static Logger LOG = LoggerFactory.getLogger(JssUtil.class);
	
	/**
	 * 创建JSS client
	 */
	private static synchronized void createClient(){
		try {
			if(jss != null){
				return;
			}
			
			LOG.info("--------->begin create JingdongStorageService");
			ClientConfig config = new ClientConfig();
			config.setConnectionTimeout(CONNECTION_TIMEOUT);
			config.setMaxConnections(MAX_CONNECTIONS);
			config.setSocketTimeout(SOCKET_TIMEOUT);
			config.setEndpoint(ENDPOINT);
			Credential credential = new Credential(ACCESS_KEY, SECRET_KEY);
			jss = new JingdongStorageService(credential, config);
			LOG.info("--------->end create JingdongStorageService   ok");
		} catch (Exception e) {
			LOG.error("--------->end create JingdongStorageService   fail",e);
			jss = null;
		}
	}
	
	/**
	 * 随机创建一个资源key
	 * @return
	 */
	private static String createResourceKey(){
		StringBuffer resourceKey = new StringBuffer();
		resourceKey.append(UUIDUtil.getUid());
		resourceKey.append(".jpg");
		return resourceKey.toString();
	}
	
	/**
	 * 创建文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static final String putObject(String filePath) throws Exception{
		LOG.info("begin put Object from jss file:"+filePath);
		if(StringUtils.isEmpty(filePath)){
			throw new RuntimeException("put file from jss fail, filePath is empty");
		}
		
		File file = new File(filePath);
		if(!file.exists()){
			throw new RuntimeException("put file from jss fail, file is not exists");
		}
		
		String resourceKey = createResourceKey();
		
		if(jss == null){
			createClient();
		}
		jss.putObject(BUCKET, resourceKey, file);
		return "http://"+ENDPOINT+"/"+BUCKET+"/"+resourceKey;
	}
	
	/**
	 * 创建文件
	 * @param in
	 * @throws Exception
	 */
	public final static String putObject(InputStream in,long fileSize) throws Exception{
		
		LOG.info("begin put Object from jss size:"+fileSize);
		if(in == null){
			throw new RuntimeException("put file from jss fail, InputStream is empty");
		}
		
		String resourceKey = createResourceKey();
		
		if(jss == null){
			createClient();
		}
		jss.putObject(BUCKET, resourceKey, fileSize, in);
		return "http://"+ENDPOINT+"/"+BUCKET+"/"+resourceKey;
	}
	
	/**
	 * 删除object
	 * @param resourceKey
	 * @throws Exception
	 */
	public final static void deleteObject(String resourceKey){
		try {
			LOG.info("delete Object from jss resourceKey:"+resourceKey);
			if(jss == null){
				createClient();
			}
			jss.deleteObject(BUCKET, resourceKey);
		} catch (Exception e) {
			LOG.error("delete object from jss fail,",e);
		}
	}
	
}
