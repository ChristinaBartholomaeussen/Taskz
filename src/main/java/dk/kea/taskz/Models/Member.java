package dk.kea.taskz.Models;

import java.util.ArrayList;

public class Member
{
    private int memberId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
	private String competance;

    public Member()
    {
    }

    public Member(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public Member(int memberId, String email, String password, String firstName, String lastName)
    {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

	public String getCompetance() {
		return competance;
	}

	public void setCompetance(String competance) {
		this.competance = competance;
	}

	@Override
	public String toString() {
    	return "MEMBER";
	}
    
}
