package org.consumer.repository;

import org.consumer.dto.TransferStatusDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DelayedTransferRepository {
    // TODO: DB 연동 이후 실제 구현 필요
    public Optional<TransferStatusDto> findById(Long id) {
        TransferStatusDto transferStatusDto = new TransferStatusDto();
        transferStatusDto.setId(id);
        transferStatusDto.setStatus("pending");
        return Optional.of(transferStatusDto);
    }

    // TODO: DB 연동 이후 실제 구현 필요
    public TransferStatusDto save(TransferStatusDto transferStatusDto) {
        return transferStatusDto;
    }
}
