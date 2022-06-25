package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    @Query(value = "select * from recruiter r where r.user_id = ? and is_deleted = false", nativeQuery = true)
    Recruiter getRecruiterProfile(long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.recruiter (company_id, fullname, verify_account,verify_phonenumber, gender, " +
            "position,linkedin_url,business_license, user_id, created_at,updated_at,is_deleted,phone_number, company_name, company_address) " +
            "VALUES(0,null,false,false,true,null,null,null,?1,null,null,false,null,null,null)", nativeQuery = true)
    void insertRecruiter(long userId);
}
