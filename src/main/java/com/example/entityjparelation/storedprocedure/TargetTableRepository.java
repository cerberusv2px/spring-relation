package com.example.entityjparelation.storedprocedure;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TargetTableRepository extends CrudRepository<TargetTableEntity, Integer> {
    @Procedure(name = "TargetTableEntity.stageToTarget")
    public String stageToTarget(Integer p_in_process_id);
}
