package fpt.edu.capstone.controller;

import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    //lấy ra thông tin recruiter đang sử dụng dịch vụ nào, gói nào
    //nếu rec có đang thuê dịch vụ , thì cần mở quyền cho job của rec đó được những đặc quyền của gói thuê đó

    @GetMapping("/wallet/{id}")
    public ResponseData getWalletInformation(@PathVariable(name = "id") long recruiterId){
        //Lấy ra thông tin ví bao gồm cả số dư của người đó
        return null;
    }

    @PostMapping("/add-wallet")
    public ResponseData addCreditForUser(){
        // khi người dùng nạp tiền vào tài khoản, cần insert số tiền đó vào db
        //cách thức nạp là chuyển tiền qua ngân hàng đến STK được quy định, khi hiveconnect nhận được tiền sẽ tự cộng tiền vào hệ thống
        return null;
    }

    @PutMapping("update-wallet")
    public ResponseData updateWallet(){
        //cập nhật lại số dư ví của user đó nếu người đó đã mua một gói dịch vụ thì trừ tiền, hoặc nếu user đó nạp thêm tiền
        return null;
    }

    @GetMapping("/wallet/history/{id}")
    public ResponseData moneyHistory(){
        //Lấy ra thông tin về lịch sử nạp, chi tiêu của tài khoản đó và tổng số tiền đã nạp
        return null;
    }
/*
- gia hạn gói package
- payment, package, wallet, banner
- login google , linkedlin
- upload file
 */
}
