package com.koreaIT.BAM.service;

import com.koreaIT.BAM.dao.MemberDao;
import com.koreaIT.BAM.dto.Member;

public class MemberService {
	
	private MemberDao memberDao;
	
	public MemberService() {
		this.memberDao = new MemberDao();
	}

	public Member getMemberbyLoginId(String loginId) {
		return memberDao.getMemberbyLoginId(loginId);
	}
}
