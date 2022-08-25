package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.RequestJoinCompanyRepository;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.RequestJoinCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestJoinCompanyServiceImpl implements RequestJoinCompanyService {

    private final RequestJoinCompanyRepository requestJoinCompanyRepository;

    private final CompanyService companyService;

    @Override
    public RequestJoinCompany createRequest(RequestJoinCompany requestJoinCompany) {
        //Mai: cần check xem recruiter này đã tạo request nào chưa, trạng thái request như thế nào
        Optional<Company> company = companyService.findById(requestJoinCompany.getCompanyId());
        if(!company.isPresent()) {
            throw new HiveConnectException("Không tìm thấy công ty này để gửi yêu cầu.");
        }
        requestJoinCompany.setStatus("Pending");
        requestJoinCompany.setApproverId(company.get().getCreatorId());
        requestJoinCompany.create();
        return requestJoinCompanyRepository.save(requestJoinCompany);
    }

    @Override
    public Optional<RequestJoinCompany> getSentRequest(long senderId) {
        return requestJoinCompanyRepository.findBySenderId(senderId);
    }

    @Override
    public Optional<List<RequestJoinCompany>> getReceiveRequest(long approverId) {
        return requestJoinCompanyRepository.findByApproverId(approverId);
    }

    @Override
    public void approveRequest(String status, long id) {
        requestJoinCompanyRepository.approveRequest(status, id);
    }

    @Override
    public Optional<RequestJoinCompany> findById(long id) {
        return requestJoinCompanyRepository.findById(id);
    }

    @Override
    public void deleteOldSentRequest(long id) {
        requestJoinCompanyRepository.deleteById(id);
    }
}
