package com.project.Real_Moment.domain.repository;

import com.project.Real_Moment.domain.entity.ItemFile;
import com.project.Real_Moment.domain.entity.S3File;
import com.project.Real_Moment.domain.repository.custom.S3FileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface S3FileRepository extends JpaRepository<S3File, Long>, S3FileRepositoryCustom {

}
