package org.example.member.controller;

import org.example.Controller;
import org.example.Util;
import org.example.member.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private List<Member> members;
    private String cmd;
    static private String signedInMember="";

    private int lastMemberId = 3;

    public MemberController(Scanner sc) {
        this.sc=sc;
        members = new ArrayList<>();
    }
    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "signin":
                doLogin();
                break;
            case "signout":
                doLogout();
                break;
            default:
                System.out.println("Invalid action method");
                break;
        }
    }
    public void doJoin(){
        String ID = "";
        String PW = "";
        System.out.print("ID : ");
        ID = sc.nextLine().trim();
        for (Member member : members) {
            if (member.getID().equals(ID)) {
                System.out.println("사용할 수 없는 아이디 입니다.");
                return;
            }
        }

        while (true) {
            System.out.print("PW : ");
            PW = sc.nextLine().trim();
            System.out.print("PW 확인 : ");
            String PWCheck = sc.nextLine().trim();
            if (!PWCheck.equals(PW)) {
                System.out.println("패스워드가 서로 다릅니다.");
                continue;
            }
            break;
        }

        System.out.println("가입되었습니다.");
        members.add(new Member(ID, PW));
    }
    public void doLogin(){
        String ID = "";
        String PW = "";
        Member foundMember = null;
        System.out.print("ID : ");
        ID = sc.nextLine().trim();
        for (Member member : members) {
            if (member.getID().equals(ID)) {
                foundMember = member;
                break;
            }
        }
        if  (foundMember == null) {
            System.out.println("존재하지 않는 아이디입니다.");
            return;
        }

        System.out.print("PW : ");
        PW = sc.nextLine().trim();
        if (!foundMember.getPW().equals(PW)) {
            System.out.println("비밀번호가 잘못 되었습니다.");
            return;
        }
        System.out.println("로그인 되었습니다.");
        signedInMember = ID;
    }
    public void doLogout(){
        System.out.println("로그아웃 되었습니다.");
        signedInMember = "";
    }

    public void makeTestData() {
        members.add(new Member("abc","123"));
        members.add(new Member("def","456"));
        members.add(new Member("ghi","789"));

    }
    static public String getSignedInMember() {
        return signedInMember;
    }
}
