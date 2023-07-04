package fr.ans.psc.copier.coller.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ans.psc.copier.coller.api.model.RedisDataWrapper;

@Repository
public interface PscContextRepository extends CrudRepository<RedisDataWrapper, String> {
}
