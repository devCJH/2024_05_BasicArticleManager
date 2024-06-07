package com.koreaIT.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.BAM.dto.Article;
import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.util.Util;

public class App {
	private int lastArticleId;
	private int lastMemberId;
	private List<Article> articles;
	private List<Member> members;
	
	public App() {
		this.lastArticleId = 0;
		this.lastMemberId = 0;
		this.articles = new ArrayList<>();
		this.members = new ArrayList<>();
	}
	
	public void run() {
		
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		makeTestData();

		Member loginedMember = null;
		
		while (true) {
			
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();
			
			if (cmd.equals("exit")) {
				break;
			}
			
			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			
			if (cmd.equals("member join")) {
				
				if (loginedMember != null) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
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
				
			} else if (cmd.equals("member login")) {
				
				if (loginedMember != null) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
				}
				
				System.out.println("== member login ==");
				System.out.printf("아이디 : ");
				String loginId = sc.nextLine();
				System.out.printf("비밀번호 : ");
				String loginPw = sc.nextLine();
				
				Member member = null;
				
				for (Member m : members) {
					if (m.getLoginId().equals(loginId)) {
						member = m;
						break;
					}
				}
				
				if (member == null) {
					System.out.printf("[ %s ]은(는) 존재하지 않는 아이디입니다\n", loginId);
					continue;
				}
				
				if (member.getLoginPw().equals(loginPw) == false) {
					System.out.println("비밀번호를 확인해주세요");
					continue;
				}
				
				loginedMember = member;
				
				System.out.printf("[ %s ] 님 환영합니다~\n", member.getName());
				
			} else if (cmd.equals("member logout")) {
				
				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}

				loginedMember = null;
				System.out.println("로그아웃 되었습니다");
				
			} else if (cmd.equals("article write")) {
				
				if (loginedMember == null) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}
				
				System.out.println("== article write ==");
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				lastArticleId++;
				
				Article article = new Article(lastArticleId, Util.getDateStr(), Util.getDateStr(), loginedMember.getId(), title, body);
				
				articles.add(article);
				
				System.out.printf("%d번 글이 생성되었습니다\n", lastArticleId);
				
			} else if (cmd.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다");
					continue;
				}
				
				String searchKeyword = cmd.substring("article list".length()).trim();
				
				List<Article> printArticles = articles;
				
				if (searchKeyword.length() > 0) {
					
					System.out.println("검색어 : " + searchKeyword);
					
					printArticles = new ArrayList<>();
					
					for (Article article : articles) {
						if (article.getTitle().contains(searchKeyword)) {
							printArticles.add(article);
						}
					}
					
					if (printArticles.size() == 0) {
						System.out.println("검색결과가 없습니다");
						continue;
					}
				}
				
				System.out.println("== article list ==");
				System.out.println("번호	|	제목	|	작성일			|	작성자	");
				for (int i = printArticles.size() - 1; i >= 0; i--) {
					Article article = printArticles.get(i);
					
					String writerName = null;
					
					for (Member member : members) {
						if (member.getId() == article.getMemberId()) {
							writerName = member.getLoginId();
							break;
						}
					}
					
					System.out.printf("%d	|	%s	|	%s	|	%s	\n", article.getId(), article.getTitle(), article.getUpdateDate(), writerName);
				}
				
			} else if (cmd.startsWith("article detail ")) {
				
				int id = getCmdNum(cmd);
				
				if (id == -1) {
					System.out.println("잘못된 명령어입니다");
					continue;
				}
				
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				
				String writerName = null;
				
				for (Member member : members) {
					if (member.getId() == foundArticle.getMemberId()) {
						writerName = member.getLoginId();
						break;
					}
				}
				
				System.out.println("== article detail ==");
				System.out.printf("번호 : %d\n", foundArticle.getId());
				System.out.printf("작성일 : %s\n", foundArticle.getRegDate());
				System.out.printf("수정일 : %s\n", foundArticle.getUpdateDate());
				System.out.printf("작성자 : %s\n", writerName);
				System.out.printf("제목 : %s\n", foundArticle.getTitle());
				System.out.printf("내용 : %s\n", foundArticle.getBody());
				
			} else if (cmd.startsWith("article modify ")) {
				
				int id = getCmdNum(cmd);
				
				if (id == -1) {
					System.out.println("잘못된 명령어입니다");
					continue;
				}
				
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				
				System.out.println("== article modify ==");
				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();
				
				foundArticle.setTitle(title);
				foundArticle.setBody(body);
				foundArticle.setUpdateDate(Util.getDateStr());
				
				System.out.printf("%d번 게시물을 수정했습니다\n", id);
				
			} else if (cmd.startsWith("article delete ")) {
				
				int id = getCmdNum(cmd);
				
				if (id == -1) {
					System.out.println("잘못된 명령어입니다");
					continue;
				}
				
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				
				articles.remove(foundArticle);
				
				System.out.println("== article delete ==");
				System.out.printf("%d번 게시물을 삭제했습니다\n", id);
				
			} else {
				System.out.println("존재하지 않는 명령어입니다");
			}
		}
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}
	private void makeTestData() {
		System.out.println("테스트용 게시물 데이터를 생성했습니다");
		
		for (int i = 1; i <= 5; i++) {
			articles.add(new Article(++lastArticleId, Util.getDateStr(), Util.getDateStr(), 1, "제목" + i, "내용" + i));
		}
	}
	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}
	private int getCmdNum(String cmd) {
		String[] cmdBits = cmd.split(" ");
		
		int id = -1;
		
		try {
			id = Integer.parseInt(cmdBits[2]);
			return id;
		} catch (NumberFormatException e) {
			return -1;
		}
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