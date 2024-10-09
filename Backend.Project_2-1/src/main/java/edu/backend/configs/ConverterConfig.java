package edu.backend.configs;

import edu.backend.dtos.client.v1.ClientV1DTO;
import edu.backend.dtos.product.v1.ProductV1DTO;
import edu.backend.dtos.supplier.v1.SupplierV1DTO;
import edu.backend.entities.ClientEntity;
import edu.backend.entities.ProductEntity;
import edu.backend.entities.SupplierEntity;
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

        mapper.addMappings(new PropertyMap<SupplierEntity, SupplierV1DTO>() {
            @Override
            protected void configure() {
                map().setAddressId(source.getAddress().getAddressId());
            }
        });

        mapper.addMappings(new PropertyMap<ProductEntity, ProductV1DTO>() {
            @Override
            protected void configure() {
                map().setImageId(source.getImage().getImageId());
                map().setSupplierId(source.getSupplier().getSupplierId());
            }
        });
        return mapper;
    }
}
