package my.study.springmvc.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class JpaConfig {
    @Bean // Auditing 기능 이용 시 타임존을 알 수 없어 ZonedDateTime 타입을 사용할 수 없기 때문에 기본값을 ZonedDateTime.now()로 설정해준다.
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }
}
