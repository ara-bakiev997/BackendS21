package edu.backend.configs;

import edu.backend.dto.client.v1.ClientV1DTO;
import edu.backend.entities.ClientEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public ModelMapper getModelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
              .setMatchingStrategy(MatchingStrategies.STRICT)
              .setFieldMatchingEnabled(true)
              .setSkipNullEnabled(true)
              .setFieldAccessLevel(AccessLevel.PRIVATE);
        mapper.addMappings(new PropertyMap<ClientEntity, ClientV1DTO>() {
            @Override
            protected void configure() {
                map().setAddressId(source.getAddress().getAddressId());
            }
        });
        return mapper;
    }
}
