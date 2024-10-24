package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.payload.OutcomeCreatDto;
import uz.saidoff.crmecosystem.payload.OutcomeUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutcomeService {

    public ResponseData<?> creat(OutcomeCreatDto outcomeCreatDto) {
        return null;
    }

    public ResponseData<?> update(UUID outcomeId, OutcomeUpdateDto outcomeDto) {
        return null;
    }

    public ResponseData<?> get(UUID outcomeId) {
        return null;
    }

    public ResponseData<?> getAllUser(int page, int size) {
        return null;
    }

    public ResponseData<?> delete(UUID outcomeId) {
        return null;
    }
}
