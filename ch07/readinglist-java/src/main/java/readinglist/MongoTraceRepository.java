package readinglist;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class MongoTraceRepository implements TraceRepository {

	private MongoOperations mongoOps;

	@Autowired
	public MongoTraceRepository(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
		MyLog.log(MongoTraceRepository.class, "new");
	}

	@Override
	public void add(Map<String, Object> traceInfo) {
		MyLog.log(MongoTraceRepository.class, "add: " + traceInfo);
		mongoOps.save(new Trace(new Date(), traceInfo));
	}

	@Override
	public List<Trace> findAll() {
		List<Trace> result = mongoOps.findAll(Trace.class);
		MyLog.log(MongoTraceRepository.class, "findAll: " + result.size());
		return result;
	}

}
