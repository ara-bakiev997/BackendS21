package edu.backend.repositories;

import edu.backend.entities.ClientEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    List<ClientEntity> findByClientNameAndClientSurnameIgnoreCase(
            @Nonnull final String clientName,
            @Nonnull final String clientSurname
    );

    List<ClientEntity> findByClientNameIgnoreCase(
            @Nonnull final String clientName
    );
}
