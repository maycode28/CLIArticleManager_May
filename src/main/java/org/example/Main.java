package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int lastArticleId = 3;
    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        makeTestData();

        String signedInMember ="";

        while (true) {
            System.out.print("명령어 ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어 입력하세요");
                continue;
            }

            if (cmd.equals("article write")) {
                if (signedInMember.isEmpty()){
                    System.out.println("먼저 로그인이 필요합니다. 로그인 해주세요.");
                    continue;
                }
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                String regDate = Util.getNowStr();
                String updateDate = Util.getNowStr();

                Article article = new Article(id, regDate, updateDate, title, body,signedInMember);
                articles.add(article);

                System.out.println(id + "번 글이 작성되었습니다.");
                lastArticleId++;
            } else if (cmd.startsWith("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.isEmpty()) {
                    System.out.println("아무것도 없음");
                } else {
                    System.out.println("   번호  /       날짜       /       제목     /   내용  ");
                    boolean exists = false;
                    for (int i = articles.size() - 1; i >= 0; i--) {
                        Article article = articles.get(i);
                        if (cmd.split(" ").length==2 || article.getTitle().contains(cmd.split(" ")[2])) {
                            exists = true;
                            if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                System.out.printf("   %d     /    %s          /    %s     /     %s   \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                            } else {
                                System.out.printf("   %d     /    %s          /    %s     /     %s   \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                            }
                        }
                    }
                    if (!exists) {
                        System.out.println("해당하는 글 없음");
                    }
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());

            } else if (cmd.startsWith("article delete")) {
                if (signedInMember.isEmpty()){
                    System.out.println("먼저 로그인이 필요합니다. 로그인 해주세요.");
                    continue;
                }
                System.out.println("==게시글 삭제==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                } else if (!signedInMember.equals(foundArticle.getAuthor())) {
                    System.out.println("게시글의 작성자만 삭제할 수 있습니다.");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else if (cmd.startsWith("article modify")) {
                if (signedInMember.isEmpty()){
                    System.out.println("먼저 로그인이 필요합니다. 로그인 해주세요.");
                    continue;
                }
                System.out.println("==게시글 수정==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }else if (!signedInMember.equals(foundArticle.getAuthor())) {
                    System.out.println("게시글의 작성자만 수정할 수 있습니다.");
                    continue;
                }
                System.out.println("기존 title : " + foundArticle.getTitle());
                System.out.println("기존 body : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine().trim();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine().trim();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);

                foundArticle.setUpdateDate(Util.getNowStr());

                System.out.println(id + "번 게시글이 수정되었습니다");
            }else if (cmd.equals("member join")) {
                String ID = "";
                String PW = "";
                System.out.print("ID : ");
                ID = sc.nextLine().trim();
                for (Member member : members) {
                    if (member.getID().equals(ID)) {
                        System.out.println("사용할 수 없는 아이디 입니다.");
                        break;
                    }
                }
                System.out.println("사용할 수 있는 아이디 입니다.");

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
            }else if (cmd.equals("member sign in")) {
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
                    continue;
                }

                System.out.print("PW : ");
                PW = sc.nextLine().trim();
                if (!foundMember.getPW().equals(PW)) {
                    System.out.println("비밀번호가 잘못 되었습니다.");
                    continue;
                }
                System.out.println("로그인 되었습니다.");
                signedInMember = ID;

            }else if (cmd.equals("member sign out")) {
                System.out.println("로그아웃 되었습니다.");
                signedInMember = "";
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }
        }
        System.out.println("==프로그램 끝==");
        sc.close();
    }

    static void makeTestData() {
        Article article = new Article(1, "2025-12-08 12:12:12", "2025-12-08 12:12:12", "Test 1", "TestTest 1","abc");
        articles.add(article);
        article = new Article(2,Util.getNowStr() , Util.getNowStr(), "Test 2", "TestTest 2","abc");
        articles.add(article);
        article = new Article(3,Util.getNowStr() , Util.getNowStr(), "Test 3", "TestTest 3","def");
        articles.add(article);
        members.add(new Member("abc","123"));
        members.add(new Member("def","456"));
        members.add(new Member("ghi","789"));



    }
    private static Article getArticleById(int id) {

        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

}

class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String body;
    private String author;

    public Article(int id, String regDate, String updateDate, String title, String body, String author) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
class Member {
    private String ID;
    private String PW;
    public Member (String ID, String PW) {
        this.ID = ID;
        this.PW = PW;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getPW() {
        return PW;
    }
    public void setPW(String PW) {
        this.PW = PW;
    }
}