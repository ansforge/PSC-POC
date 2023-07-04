package fr.ans.psc.remote.cache.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ans.psc.remote.cache.api.model.RedisDataWrapper;

@Repository
public interface PscContextRepository extends CrudRepository<RedisDataWrapper, String> {
}
