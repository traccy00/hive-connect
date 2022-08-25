package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;
import java.util.Optional;

public interface RequestJoinCompanyService {

    RequestJoinCompany createRequest(RequestJoinCompany requestJoinCompany);

    Optional<RequestJoinCompany> getSentRequest(long senderId);

    ResponseDataPagination getReceiveRequest(Integer pageNo, Integer pageSize, long approverId);

    void approveRequest(String status, long id);

    Optional<RequestJoinCompany> findById(long id);

    void deleteOldSentRequest(long id);
}
