package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int lastArticleId = 0;
    static List<Article> articles = new ArrayList<>();
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        makeTestData();

        HashMap<String, String> members = new HashMap<>();
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
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                String regDate = Util.getNowStr();
                String updateDate = Util.getNowStr();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 작성되었습니다.");
                lastArticleId++;
            } else if (cmd.startsWith("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
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
                System.out.println("==게시글 삭제==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
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
                while(true){
                    System.out.print("ID : ");
                    ID = sc.nextLine().trim();
                    if (members.containsKey(ID)) {
                        System.out.println("사용할 수 없는 아이디 입니다.");
                    } else {
                        System.out.println("사용할 수 있는 아이디 입니다.");
                        break;
                    }
                }
                while (true){
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
                members.put(ID, PW);

            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }
        }
        System.out.println("==프로그램 끝==");
        sc.close();
    }

    static void makeTestData() {
        for (int i = 1; i <= 3; i++) {
            int id = ++lastArticleId;
            String title = "Test" + id;
            String body = "TestTest" + id;
            String regDate = Util.getNowStr();
            String updateDate = Util.getNowStr();
            Article article = new Article(id, regDate, updateDate, title, body);
            articles.add(article);
        }


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

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
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


}