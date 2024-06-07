package com.koreaIT.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.koreaIT.BAM.dto.Member;

public class MemberDao {
	private int lastMemberId;
	private List<Member> members;
	
	public MemberDao() {
		this.lastMemberId = 0;
		this.members = new ArrayList<>();
	}

	public Member getMemberbyLoginId(String loginId) {
		for (Member m : members) {
			if (m.getLoginId().equals(loginId)) {
				return m;
			}
		}
		return null;
	}
}
