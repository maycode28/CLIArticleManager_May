package org.example.article.controller;

import org.example.Controller;
import org.example.Util;
import org.example.article.entity.Article;
import org.example.member.controller.MemberController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private  List<Article> articles;
    private String cmd;
    private String signedInMember;

    private int lastArticleId = 3;

    public ArticleController(Scanner sc){
        this.sc=sc;
        articles =new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.signedInMember = MemberController.getSignedInMember();

        switch (actionMethodName) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "delete":
                doDelete();
                break;
            case "modify":
                doModify();
                break;
            default:
                System.out.println("Invalid action method");
                break;
        }
    }

    public void doWrite(){
        if (signedInMember.isEmpty()){
            System.out.println("먼저 로그인이 필요합니다. 로그인 해주세요.");
            return;
        }
        System.out.println("==게시글 작성==");
        int id = lastArticleId + 1;
        System.out.print("제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("내용 : ");
        String body = sc.nextLine().trim();
        String regDate = Util.getNowStr();
        String updateDate = Util.getNowStr();

        Article article = new Article(id, regDate, updateDate, title, body, signedInMember);
        articles.add(article);

        System.out.println(id + "번 글이 작성되었습니다.");
        lastArticleId++;

    }
    public void showList(){
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
    }
    public void showDetail(){
        System.out.println("==게시글 상세보기==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());
    }
    public void doDelete(){
        if (signedInMember.isEmpty()){
            System.out.println("먼저 로그인이 필요합니다. 로그인 해주세요.");
            return;
        }
        System.out.println("==게시글 삭제==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        } else if (!signedInMember.equals(foundArticle.getAuthor())) {
            System.out.println("게시글의 작성자만 삭제할 수 있습니다.");
            return;
        }
        articles.remove(foundArticle);
        System.out.println(id + "번 게시글이 삭제되었습니다");
    }
    public void doModify(){
        if (signedInMember.isEmpty()){
            System.out.println("먼저 로그인이 필요합니다. 로그인 해주세요.");
            return;
        }
        System.out.println("==게시글 수정==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }else if (!signedInMember.equals(foundArticle.getAuthor())) {
            System.out.println("게시글의 작성자만 수정할 수 있습니다.");
            return;
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
    }
    private Article getArticleById(int id) {

        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

   public void makeTestData() {
        Article article = new Article(1, "2025-12-08 12:12:12", "2025-12-08 12:12:12", "Test 1", "TestTest 1","abc");
        articles.add(article);
        article = new Article(2,Util.getNowStr() , Util.getNowStr(), "Test 2", "TestTest 2","abc");
        articles.add(article);
        article = new Article(3,Util.getNowStr() , Util.getNowStr(), "Test 3", "TestTest 3","def");
        articles.add(article);
    }

}