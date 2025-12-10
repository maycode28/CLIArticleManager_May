package org.example;

import org.example.member.controller.MemberController;
import org.example.article.controller.ArticleController;

import java.util.Scanner;

public class App {
    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestData();

        Controller controller = null;

        while (true) {
            System.out.print("명령어 ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어 입력하세요");
                continue;
            }

            String[] cmdBits = cmd.split(" ");

            String controllerName = cmdBits[0];

            if (cmdBits.length == 1) {
                System.out.println("명령어 확인 필요");
                continue;
            }

            String actionMethodName = cmdBits[1];

            if (controllerName.equals("article")) {
                controller = articleController;
            } else if (controllerName.equals("member")) {
                controller = memberController;
            } else {
                System.out.println("지원하지 않는 기능");
                continue;
            }

            controller.doAction(cmd, actionMethodName);
        }
        System.out.println("==프로그램 끝==");
        sc.close();
    }
}
