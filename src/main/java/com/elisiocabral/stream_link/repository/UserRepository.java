package com.elisiocabral.stream_link.repository;

import com.elisiocabral.stream_link.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
