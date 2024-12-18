package uz.saidoff.crmecosystem.payload;

import lombok.*;

import java.util.UUID;

public record WarningResponseDto(int count, UUID uuid) {

}
