package org.consumer.repository;

import org.consumer.dto.DelayedTransferStatusDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DelayedTransferRepository {
    // TODO: DB 연동 이후 실제 구현 필요
    public Optional<DelayedTransferStatusDto> findById(Long id) {
        DelayedTransferStatusDto delayedTransferStatusDto = new DelayedTransferStatusDto();
        delayedTransferStatusDto.setId(id);
        delayedTransferStatusDto.setStatus("pending");
        return Optional.of(delayedTransferStatusDto);
    }

    // TODO: DB 연동 이후 실제 구현 필요
    public DelayedTransferStatusDto save(DelayedTransferStatusDto delayedTransferStatusDto) {
        return delayedTransferStatusDto;
    }
}
