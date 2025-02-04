/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.gadmilagro;

import com.openkm.gadmilagro.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

/**
 * Created by pavila on 17/06/16.SpringBootApplication
 * <p>
 * https://spring.io/guides/gs/spring-boot/
 * <p>
 * By default all packages below your main configuration class (the one annotated with @EnableAutoConfiguration
 * or @SpringBootApplication) will be searched.
 */
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JmsAutoConfiguration.class})
public class MainAppConfig extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(MainAppConfig.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainAppConfig.class).properties(getProperties());
	}

	// Inspired in https://www.surasint.com/spring-boot-override-property-example/
	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", "classpath:application.properties,file:${catalina.home}/" + Config.PROPERTIES);
		return props;
	}
}
