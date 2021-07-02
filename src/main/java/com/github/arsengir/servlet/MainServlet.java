package com.github.arsengir.servlet;

import com.github.arsengir.controller.PostController;
import com.github.arsengir.repository.PostRepository;
import com.github.arsengir.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    public static final String API_POSTS = "/api/posts";
    public static final String API_POSTS_ID = "/api/posts/\\d+";

    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("com.github.arsengir");

        final var repository = (PostRepository) context.getBean("postRepository");
        final var service = (PostService) context.getBean("postService");
        this.controller = (PostController) context.getBean("postController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String path = req.getRequestURI();
        if (path.equals(API_POSTS)) {
            controller.all(resp);
            return;
        }
        if (path.matches(API_POSTS_ID)) {
            final long id = getId(path);
            controller.getByID(id, resp);
            return;
        }
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String path = req.getRequestURI();
        if (path.equals(API_POSTS)) {
            controller.save(req.getReader(), resp);
            return;
        }
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String path = req.getRequestURI();
        if (path.matches(API_POSTS_ID)) {
            final long id = getId(path);
            controller.removeById(id, resp);
            return;
        }
        super.doDelete(req, resp);
    }

    private long getId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }

}
