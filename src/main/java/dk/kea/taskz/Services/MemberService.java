package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MemberService
{
    @Autowired
    MemberRepository memberRepository;

    /**
     * - OVO returns all members
     * @return
     */
    public ArrayList<Member> getAllMembers() {
        return memberRepository.getAllMembersFromDB();
    }

    /**
     * - OVO
     * Get a single member from the database
     * @param id
     * @return
     */
    public Member getSingleMember(int id) {
        return memberRepository.getSingleMEmberFromDBWthID(id);
    }

    /**
     * - RBP
     * Receives two strings from the method parameter, a username and a password.
     * Goes through a list of all members received from the local getAllMembers() method, and returns true if the
     * iterated Member has both the name and password equal to the received method parameters.
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
	 * @param Email
	 * @param Password
	 * @return
	 */
    public int getId(String Email, String Password) {
        return memberRepository.getActiveUserIDFromDB(Email, Password);
	}

}

