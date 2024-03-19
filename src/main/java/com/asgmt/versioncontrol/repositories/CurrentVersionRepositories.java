package com.asgmt.versioncontrol.repositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CurrentVersionRepositories {
    private final JdbcTemplate jdbcTemplate;
    public CurrentVersionRepositories(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertCurrentVersion(int fileId, int versionId) {
        String sql = "INSERT INTO current_version (file_id, version_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, fileId, versionId);
    }

    public Integer getVersionIdByFileId(int fileId){
        String sql = "SELECT version_id FROM current_version WHERE file_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, fileId);
    }
    public void updateFileIdByVersionId(int fileId, int versionId) {
        String sql = "UPDATE current_version SET version_id = ? WHERE file_id = ?";
        jdbcTemplate.update(sql, versionId, fileId);
    }

    public String getContentByVersionIdAndFilePath(String filePath, String versionId) {
        String sql = "SELECT v.content FROM version v " +
                "WHERE v.version_id = ? " +
                "AND v.file_id IN (SELECT f.file_id FROM file f WHERE f.file_path = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{versionId, filePath}, String.class);
    }
}
