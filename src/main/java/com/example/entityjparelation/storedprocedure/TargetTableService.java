package com.example.entityjparelation.storedprocedure;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TargetTableService {

    private TargetTableRepository targetTableRepository;

    public TargetTableService(TargetTableRepository targetTableRepository) {
        this.targetTableRepository = targetTableRepository;
    }


    @Transactional
    public String stageToTarget(Integer p_in_process_id) {
        return targetTableRepository.stageToTarget(p_in_process_id);
    }
}
