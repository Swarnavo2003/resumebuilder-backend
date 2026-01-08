package in.swarnavo.resumebuilderapi.repository;

import in.swarnavo.resumebuilderapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
