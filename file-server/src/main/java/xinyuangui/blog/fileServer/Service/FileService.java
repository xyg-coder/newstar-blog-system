package xinyuangui.blog.fileServer.Service;

import org.springframework.stereotype.Service;
import xinyuangui.blog.fileServer.domain.File;

import java.util.List;
import java.util.Optional;

public interface FileService {

    File saveFile(File file);

    void removeFile(String id);

    /**
     * get file by id
     * @param id
     * @return File or null if there is no such file
     */
    Optional<File> getFileById(String id);

    /**
     * get the pages of the files, sort with the upload time
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<File> listFilesByPage(int pageIndex, int pageSize);
}
