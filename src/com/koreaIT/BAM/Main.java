package com.koreaIT.BAM;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		int id = 0;
		
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();
			
			if (cmd.equals("exit")) {
				break;
			}
			
			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			
			if (cmd.equals("article list")) {
				
				System.out.println("게시물이 존재하지 않습니다");
				
			} else if (cmd.equals("article write")) {
				
				System.out.printf("제목 : ");
				sc.nextLine();
				System.out.printf("내용 : ");
				sc.nextLine();
				
				id++;
				
				System.out.printf("%d번 글이 생성되었습니다\n", id);
				
			} else {
				System.out.println("존재하지 않는 명령어입니다");
			}
			
		}
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
		
	}
}