package test.southsystem.desafiobackvotos.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.southsystem.desafiobackvotos.feign.decoder.CpfFeignDecoder;

@Configuration
public class CpfFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CpfFeignDecoder();
    }
}
