package uz.saidoff.crmecosystem.service;

import uz.saidoff.crmecosystem.payload.InternGetDto;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.UUID;

public interface InternsService {
    ResponseData<?> getAllInterns(int page, int size);

    ResponseData<?> getOneById(UUID interId);

    ResponseData<?> addIntern(UUID userId, InternGetDto internGetDto);
}
