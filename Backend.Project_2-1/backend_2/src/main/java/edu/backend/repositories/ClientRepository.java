package edu.backend.repositories;

import edu.backend.entities.ClientEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    List<ClientEntity> findByClientNameAndClientSurname(
            @Nonnull final String clientName,
            @Nullable final String clientSurname
    );
}
