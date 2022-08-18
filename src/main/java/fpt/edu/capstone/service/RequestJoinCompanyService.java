package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.RequestJoinCompany;

import java.util.List;
import java.util.Optional;

public interface RequestJoinCompanyService {

    RequestJoinCompany createRequest(RequestJoinCompany requestJoinCompany);

    Optional<RequestJoinCompany> getSentRequest(long senderId);

    Optional<List<RequestJoinCompany>> getReceiveRequest(long approverId);

    void approveRequest(String status, long id);

    Optional<RequestJoinCompany> findById(long id);
}
