package database;

import com.lambdaworks.redis.*;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;

import util.AppConfig;

public class RedisDAO {
	final static AppConfig appConfig = AppConfig.newInstance();
	private static RedisClient redisClient;
//	private static String connectionString = "redis://jredis@localhost:6379/13";
	private static String connectionString = appConfig.getProperty("DATABASE_CONNECT_STRING");
	protected static StatefulRedisConnection<String, String> connection;
	protected static RedisCommands<String, String> syncCommands;

	public RedisDAO() {
		if(redisClient == null){
		redisClient = RedisClient.create(connectionString);
		connection = redisClient.connect();
		syncCommands = connection.sync();
		}
	}

	public void disconnect() {

		if (connection.isOpen())
			connection.close();
		redisClient.shutdown();
	}
}
