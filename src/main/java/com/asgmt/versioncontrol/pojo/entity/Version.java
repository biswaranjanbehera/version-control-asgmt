package com.asgmt.versioncontrol.pojo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "version_id")
    private String versionId;

    @Column(name = "file_id")
    private int fileId;
    private String content;
}
