package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService
{
    @Autowired
    MemberRepository memberRepository;

    public MemberService()
    {
    }

    public ArrayList<Member> getAllMembers() {
    	return memberRepository.getAllMembersFromDB();
	}
    
	public Member getSingleMember(int id) {
    	return memberRepository.getSingleMEmberFromDBWthID(id);
	}
	
    public boolean verifyLogin(String username, String password)
    {
        for(Member member : getAllMembers())
            if(member.getEmail().equals(username) && member.getPassword().equals(password))
            {
                return true;
            }
        
        return false;
    }
    public ArrayList<String> getAllCompetence(int id) {return memberRepository.getAllMemberCompetences(id);}
    
    public int getId(String Email, String Password) {
    	return memberRepository.getActiveUserIDFromDB(Email, Password);
	}
}
