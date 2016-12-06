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
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig extends Properties {
	static AppConfig appConfig;
	private AppConfig (){
		super();
	}
	public static AppConfig newInstance(){
		try {
			if(appConfig == null) {
			appConfig = new AppConfig();
			System.getProperty("user.dir");
			FileInputStream in = new FileInputStream("app.conf");
			appConfig.load(in);
			in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appConfig;
	}
}
