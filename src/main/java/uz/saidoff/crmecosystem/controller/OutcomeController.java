package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.OutcomeCreatDto;
import uz.saidoff.crmecosystem.payload.OutcomeUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.OutcomeService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.sql.Date;
import java.util.UUID;

@RestController
@RequestMapping("api/outcome")
@RequiredArgsConstructor
public class OutcomeController {
    private final OutcomeService outcomeService;

    @CheckPermission("CREATE_OUTCOME")
    @PostMapping("creat")
    public ResponseData<?> creatOutcome(@RequestBody OutcomeCreatDto outcomeCreatDto) {
        return this.outcomeService.creat(outcomeCreatDto);
    }

    @CheckPermission("EDIT_OUTCOME")
    @PutMapping("outcome-update/{outcomeId}")
    public ResponseData<?> updateOutcome(@PathVariable UUID outcomeId, @RequestBody OutcomeUpdateDto outcomeDto) {
        return this.outcomeService.update(outcomeId,outcomeDto);
    }

    @CheckPermission("GET_OUTCOME")
    @GetMapping("/{outcomeId}")
    public ResponseData<?> getOutcome(@PathVariable UUID outcomeId) {
        return this.outcomeService.get(outcomeId);
    }

    @CheckPermission("GET_OUTCOME")
    @GetMapping("/get-all-outcome")
    public ResponseData<?> getAllEmployee(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return this.outcomeService.getAllUser(page,size);
    }

    @CheckPermission("DELETE_OUTCOME")
    @DeleteMapping("delete-outcome/{outcomeId}")
    public ResponseData<?> deleteOutcome(@PathVariable UUID outcomeId) {
        return this.outcomeService.delete(outcomeId);
    }

    @CheckPermission("GET_OUTCOME")
    @GetMapping("get-outcome-history/{start}/{end}/{currency}")
    public ResponseData<?> outcomeHistory(@PathVariable Date start, @PathVariable Date end,
                                          @PathVariable Currency currency) {
        return this.outcomeService.outcome(start, end, currency);
    }

}
