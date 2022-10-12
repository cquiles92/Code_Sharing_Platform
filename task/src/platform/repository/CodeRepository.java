package platform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.model.CodeFragment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends CrudRepository<CodeFragment, Long> {
    @Override
    List<CodeFragment> findAll();

    Optional<CodeFragment> findByUuid(UUID uuid);

    List<CodeFragment> findFirst10ByIsRestrictedFalseOrderByDateCreatedDesc();

}