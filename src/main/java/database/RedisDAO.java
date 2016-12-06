/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author Jianye Liu brucelau.email@gmail.com
 */
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
