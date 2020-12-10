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
	private int jobTitle;

    public Member()
    {
    }


    public Member(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public Member(int memberId, String email, String password, String firstName, String lastName, int jobTitle)
    {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
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

	public int getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(int jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
    	return "MEMBER";
	}
    
}
