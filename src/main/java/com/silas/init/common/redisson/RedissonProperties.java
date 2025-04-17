package com.silas.init.common.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redisson 分布式锁配置
 *
 * @author Silas Yan 2025-04-15:19:08
 */
@Data
@Component
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
	private String enable;
	private String client;
	private Integer database;
	private String password;
	private Integer timeout;
}
