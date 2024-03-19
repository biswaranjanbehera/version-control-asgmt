package com.asgmt.versioncontrol.repositories;

import com.asgmt.versioncontrol.pojo.entity.File;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepositories extends JpaRepository<File,Integer> {
    File findByFilePath(String filePath);

    @Transactional
    @Modifying
    @Query("DELETE FROM File f WHERE f.fileId = :fileId")
    void deleteByFileId(int fileId);
}
