package com.zw.srdz.dao.base;

import javax.annotation.Resource;
import org.springframework.data.mongodb.core.MongoOperations;

public class BaseDao {

	@Resource(name="mongoTemplete")
	protected MongoOperations template; 
	
}
