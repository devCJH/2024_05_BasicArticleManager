package com.koreaIT.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.service.MemberService;
import com.koreaIT.BAM.util.Util;

public class MemberController {

	private Member loginedMember;
	private MemberService memberService;
	private Scanner sc;
	
	public MemberController(Scanner sc) {
		this.loginedMember = null;
		this.memberService = new MemberService();
		this.sc = sc;
	}
	
	public void doJoin() {

		if (loginedMember != null) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}
		
		String loginPw = null;
		String loginId = null;
		String name = null;
		
		System.out.println("== member join ==");
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
			
			if (isLoginIdDup(loginId)) {
				System.out.printf("[ %s ]은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}
			
			System.out.printf("[ %s ]은(는) 사용가능한 아이디입니다\n", loginId);
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine().trim();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름을 입력해주세요");
				continue;
			}
			break;
		}
		lastMemberId++;
		
		Member member = new Member(lastMemberId, Util.getDateStr(), Util.getDateStr(), loginId, loginPw, name);
		
		members.add(member);
		
		System.out.printf("[ %s ] 회원님이 가입되었습니다.\n", loginId);
	}

	public void doLogin() {

		if (loginedMember != null) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}
		
		System.out.println("== member login ==");
		System.out.printf("아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("비밀번호 : ");
		String loginPw = sc.nextLine();
		
		Member member = memberService.getMemberbyLoginId(loginId);
		
		if (member == null) {
			System.out.printf("[ %s ]은(는) 존재하지 않는 아이디입니다\n", loginId);
			return;
		}
		
		if (member.getLoginPw().equals(loginPw) == false) {
			System.out.println("비밀번호를 확인해주세요");
			return;
		}
		
		loginedMember = member;
		
		System.out.printf("[ %s ] 님 환영합니다~\n", member.getName());
	}

	public void doLogout() {

		if (loginedMember == null) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}

		loginedMember = null;
		System.out.println("로그아웃 되었습니다");
	}

	private boolean isLoginIdDup(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return true;
			}
		}
		return false;
	}
	
}
