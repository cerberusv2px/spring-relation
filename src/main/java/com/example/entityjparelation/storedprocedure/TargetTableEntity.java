package com.example.entityjparelation.storedprocedure;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SP_MODEL_ADJ_STG_TO_TRGT", schema = "CRDP_WEB")
@Data
@NamedStoredProcedureQuery(
        name = "StagedTableEntity.stageToTarget",
        procedureName = "CRDP_WEB.CRDP_WEB.SP_MODEL_ADJ_STG_TO_TRGT",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_in_process_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_out_message", type = String.class),
        },
        resultClasses = {TargetTableEntity.class}
)
public class TargetTableEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Integer id;

    // other columns
}
