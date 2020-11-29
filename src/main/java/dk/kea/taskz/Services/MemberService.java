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

    private List<Member> memberList;

    private MemberService()
    {
        this.memberList = new ArrayList<>();

        // Dummy code
        memberList.add(new Member(1,"rune@taskz.com", "1234","Rune","Petersen"));
        memberList.add(new Member(2,"oscar@taskz.com", "1234","Oscar","Otterstad"));
        memberList.add(new Member(3,"frederik@taskz.com", "1234","Frederik","Meizner"));
        memberList.add(new Member(4,"christina@taskz.com", "1234","Christina","BartXXX"));
    }

    public boolean verifyLogin(String username, String password)
    {
        for(Member member : memberList)
            if(member.getEmail().equals(username) && member.getPassword().equals(password))
            {
                return true;
            }

        getAllMembers();

        return false;
    }

    public List<Member> getAllMembers()
    {
        return memberRepository.getAllMembersFromDB();
    }
}
