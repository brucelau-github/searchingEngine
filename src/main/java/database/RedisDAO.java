package database;

import com.lambdaworks.redis.*;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

public class RedisDAO implements DAO {
	private RedisClient redisClient;
	private final String connectionString = "redis://jredis@localhost:6379/13";
	StatefulRedisConnection<String, String> connection;
	RedisCommands<String, String> syncCommands;
	public RedisDAO(){
		redisClient = RedisClient.create(connectionString);
		connection = redisClient.connect();
		syncCommands = connection.sync();
	}
	
	/* (non-Javadoc)
	 * @see database.DAO#set(java.lang.String, java.lang.String)
	 */
	@Override
	public void set(String key,String value){
		
		syncCommands.set(key, value);
	}
	/* (non-Javadoc)
	 * @see database.DAO#get(java.lang.String)
	 */
	@Override
	public String get(String key){
		return syncCommands.get(key);
	}

	/* (non-Javadoc)
	 * @see database.DAO#disconnect()
	 */
	@Override
	public void disconnect(){
		
		if(connection.isOpen())
		connection.close();
		redisClient.shutdown();
	}
	
	public long getLen(String list){
		return syncCommands.llen(list);
	}
	public String lpop(String list){
		return syncCommands.lpop(list);
	}
}
