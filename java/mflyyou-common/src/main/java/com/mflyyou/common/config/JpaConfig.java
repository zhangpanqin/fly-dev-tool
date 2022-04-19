package com.mflyyou.common.config;

import com.mflyyou.common.jpa.AuditAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditAwareImpl auditAware() {
        return new AuditAwareImpl();
    }
}
