package outfox.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "outfox.springboot.mapper")
public class BeanConfig {
}
