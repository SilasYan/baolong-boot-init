package com.silas.init.common.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 分布式锁配置
 *
 * @author Silas Yan 2025-04-15:18:57
 */
@Configuration
@ConditionalOnBean(RedissonProperties.class)
@ConditionalOnProperty(prefix = "redisson", name = "enable", havingValue = "true", matchIfMissing = false)
public class RedissonConfig {

	private final RedissonProperties redissonProperties;

	public RedissonConfig(RedissonProperties redissonProperties) {
		this.redissonProperties = redissonProperties;
	}

	@Bean(name = "redissonClient")
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer().setAddress(redissonProperties.getClient());
		// 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
		config.useSingleServer().setTimeout(redissonProperties.getTimeout());
		config.useSingleServer().setDatabase(redissonProperties.getDatabase());
		if (redissonProperties.getPassword() != null && !redissonProperties.getPassword().isEmpty()) {
			config.useSingleServer().setPassword(redissonProperties.getPassword());
		}
		return Redisson.create(config);
	}
}
