package init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Init {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public Init() {
		
	}
	
	public Init(MongoTemplate mongo) {
		mongoTemplate = mongo;
	}
	
	public void music() {
		
	}
}