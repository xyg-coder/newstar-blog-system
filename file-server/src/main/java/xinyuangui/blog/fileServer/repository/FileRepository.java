package xinyuangui.blog.fileServer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import xinyuangui.blog.fileServer.domain.File;

/**
 * File Repository
 * @since 2018.2.23
 * @author Xinyuan Gui
 */
public interface FileRepository extends MongoRepository<File, String> {
}
