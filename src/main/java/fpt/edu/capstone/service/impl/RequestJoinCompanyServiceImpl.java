package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.repository.RequestJoinCompanyRepository;
import fpt.edu.capstone.service.RequestJoinCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestJoinCompanyServiceImpl implements RequestJoinCompanyService {

    @Autowired
    private RequestJoinCompanyRepository requestJoinCompanyRepository;

    @Override
    public RequestJoinCompany createRequest(RequestJoinCompany requestJoinCompany) {
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
}
