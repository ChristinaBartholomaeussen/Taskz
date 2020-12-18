package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public MemberService() {
    }

    /**
     * - OVO returns all members
     *
     * @return
     */
    public ArrayList<Member> getAllMembers() {
        return memberRepository.getAllMembersFromDB();
    }

    /**
     * Get a single member from the database
     *
     * @param id
     * @return
     */
    public Member getSingleMember(int id) {
        return memberRepository.getSingleMEmberFromDBWthID(id);
    }

    /*public String getMember(String teammember){

        String competences = "";

        for(Member m : getAllMembers())

            if(m.getFirstName().equals(teammember)){
                competences = m.getCompetence();
        }
       return competences;
    }*/

    /**
     * -Rune
     *
     * @param username
     * @param password
     * @return
     */
    public boolean verifyLogin(String username, String password) {
        for (Member member : getAllMembers())
            if (member.getEmail().equals(username) && member.getPassword().equals(password)) {
                return true;
            }

        return false;
    }

    /**
	 * - OVO
	 * Get ID with email and password
	 *
	 * @param Email
	 * @param Password
	 * @return
	 */
    public int getId(String Email, String Password) {
        return memberRepository.getActiveUserIDFromDB(Email, Password);
	}

}

