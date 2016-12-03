package database;

import com.lambdaworks.redis.*;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;

public class RedisDAO {
	private RedisClient redisClient;
	private final String connectionString = "redis://jredis@localhost:6379/13";
	StatefulRedisConnection<String, String> connection;
	protected RedisCommands<String, String> syncCommands;

	public RedisDAO() {
		redisClient = RedisClient.create(connectionString);
		connection = redisClient.connect();
		syncCommands = connection.sync();
	}

	public void disconnect() {

		if (connection.isOpen())
			connection.close();
		redisClient.shutdown();
	}

}
