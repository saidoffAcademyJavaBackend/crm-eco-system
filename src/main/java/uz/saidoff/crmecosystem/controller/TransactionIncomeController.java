package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.TransactionIncomeAddDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.TransactionIncomeService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionIncomeController {
    private final TransactionIncomeService transactionIncomeService;

    @CheckPermission("CREATE_TRANSACTION")
    @PostMapping("add-income/")
    public ResponseData<?> addIncomeTransaction(@RequestBody TransactionIncomeAddDto transactionIncomeAddDto) {
        return this.transactionIncomeService.addTransactionIncome(transactionIncomeAddDto);
    }

    @CheckPermission("UPDATE_TRANSACTION")
    @PutMapping("update-income/{transactionId}")
    public ResponseData<?> updateIncomeTransaction(@PathVariable UUID transactionId,@RequestBody TransactionIncomeAddDto transactionIncomeAddDto) {
        return this.transactionIncomeService.updateTransactionIncome(transactionIncomeAddDto,transactionId);
    }

    @CheckPermission("GET_TRANSACTION")
    @GetMapping("get-all-income-transactions")
    public ResponseData<?> getAllIncomeTransaction(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return this.transactionIncomeService.getAllIncomeTransactions(page,size);
    }
    @CheckPermission("GET_TRANSACTION")
    @GetMapping("get-one-income-transaction")
    public ResponseData<?> getOneIncomeTransaction(@RequestParam UUID transactionId) {
        return this.transactionIncomeService.getOneIncomeTransaction(transactionId);
    }
}
