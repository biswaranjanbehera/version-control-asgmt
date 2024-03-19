package com.asgmt.versioncontrol.repositories;

import com.asgmt.versioncontrol.pojo.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepositories extends JpaRepository<Version,Integer> {
    Version findByFileId(int fileId);

    @Query(value = "SELECT version_id, content, timestamp FROM version WHERE file_id = ?1", nativeQuery = true)
    List<Object[]> findVersionDetailsByFileId(int fileId);


}
