package edu.backend.services.supplier;


import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.supplier.v1.SupplierV1DTO;
import edu.backend.dtos.supplier.v1.SuppliersV1DTO;
import edu.backend.entities.AddressEntity;
import edu.backend.entities.SupplierEntity;
import edu.backend.repositories.AddressRepository;
import edu.backend.repositories.ProductRepository;
import edu.backend.repositories.SupplierRepository;
import edu.backend.utils.error_handler.ErrorResponseException;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final AddressRepository addressRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createSupplier(@Nonnull final SupplierV1DTO supplierDTO) {
        final var supplierEntity = modelMapper.map(supplierDTO, SupplierEntity.class);

        Optional.ofNullable(supplierEntity.getSupplierId())
                .ifPresent(supplierId -> {
                    if (supplierRepository.findById(supplierId).isPresent()) {
                        throw new ErrorResponseException(
                                String.format("Адрес по указанном supplierId = %d уже существует", supplierId),
                                HttpStatus.NOT_FOUND
                        );
                    }
                });

        Optional.ofNullable(supplierDTO.getAddressId())
                .flatMap(addressRepository::findById)
                .ifPresent(supplierEntity::setAddress);

        supplierRepository.save(supplierEntity);
    }

    @Override
    @Transactional
    public void updateAddressById(final long supplierId, @Nonnull final AddressV1DTO addressDTO) {
        supplierRepository.findById(supplierId)
                          .map(supplierEntity -> {
                              final var addressDtoFromDb =
                                      Optional.ofNullable(addressDTO.getAddressId())
                                              .flatMap(addressRepository::findById)
                                              .orElseGet(() -> addressRepository.save(modelMapper.map(
                                                      addressDTO,
                                                      AddressEntity.class
                                              )));

                              supplierEntity.setAddress(addressDtoFromDb);

                              return supplierEntity;
                          })
                          .ifPresentOrElse(
                                  supplierRepository::save,
                                  () -> {
                                      throw new ErrorResponseException(
                                              String.format(
                                                      "Поставщик по указанному supplierId = %d не существует",
                                                      supplierId
                                              ),
                                              HttpStatus.NOT_FOUND
                                      );
                                  }
                          );
    }

    @Override
    @Transactional
    public void deleteById(final long supplierId) {
        supplierRepository.findById(supplierId)
                          .ifPresentOrElse(
                                  supplierEntity -> {
                                      if (!productRepository.findBySupplier(supplierEntity).isEmpty()) {
                                          throw new ErrorResponseException(
                                                  String.format(
                                                          "Нельзя удалить указанного поставщика supplierId = %d, " +
                                                          "так как существуют связанные продукты",
                                                          supplierId
                                                  ),
                                                  HttpStatus.NOT_ACCEPTABLE
                                          );
                                      }

                                      supplierRepository.delete(supplierEntity);
                                  },
                                  () -> {
                                      throw new ErrorResponseException(
                                              String.format(
                                                      "Поставщик по указанному supplierId = %d не существует",
                                                      supplierId
                                              ),
                                              HttpStatus.NOT_FOUND
                                      );
                                  }
                          );
    }

    @Nonnull
    @Override
    public SuppliersV1DTO getAllSuppliers() {
        return new SuppliersV1DTO(
                supplierRepository
                        .findAll()
                        .stream()
                        .map(supplierEntity -> modelMapper.map(supplierEntity, SupplierV1DTO.class))
                        .toList()
        );
    }

    @Nonnull
    @Override
    public SupplierV1DTO getById(final long supplierId) {
        return supplierRepository.findById(supplierId)
                                 .map(supplierEntity -> modelMapper.map(supplierEntity, SupplierV1DTO.class))
                                 .orElseThrow(() -> new ErrorResponseException(
                                         String.format(
                                                 "Поставщик по указанному supplierId = %d не существует",
                                                 supplierId
                                         ),
                                         HttpStatus.NOT_FOUND
                                 ));
    }
}

