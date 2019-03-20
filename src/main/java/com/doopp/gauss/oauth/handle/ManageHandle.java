package com.doopp.gauss.oauth.handle;

import com.doopp.gauss.oauth.entity.Client;
import com.doopp.gauss.oauth.message.response.ListPage;
import com.doopp.gauss.oauth.service.ManageService;
import com.doopp.gauss.oauth.entity.User;
import com.doopp.gauss.oauth.entity.vo.UserVO;
import com.doopp.gauss.oauth.message.response.Authentication;
import com.doopp.gauss.server.application.ApplicationProperties;
import com.doopp.gauss.server.resource.RequestAttributeParam;
import com.github.pagehelper.PageHelper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.ws.rs.*;

@Slf4j
@Path("/manage/api")
public class ManageHandle {

    @Inject
    private ApplicationProperties applicationProperties;

    @Inject
    private ManageService manageService;

    @GET
    @Path("/authentication")
    public Mono<Authentication> authentication() {
        Authentication authentication = new Authentication(
                applicationProperties.l("admin.client.id"),
                applicationProperties.s("admin.client.secret")
        );
        return Mono.just(authentication);
    }

    @GET
    @Path("/manager")
    public Mono<UserVO> currentManager(@RequestAttributeParam("current_user") UserVO user) {
        return Mono.just(user);
    }

    @GET
    @Path("/users")
    public Mono<ListPage<User>> users(@QueryParam("page") Integer page) {
        PageHelper.startPage(page, 30);
        return manageService.getUsers()
            .map(list->new ListPage<>(list, User.class));
    }

    @GET
    @Path("/apps")
    public Mono<ListPage<Client>> apps(@QueryParam("page") Integer page) {
        PageHelper.startPage(page, 30);
        return manageService.getClients()
            .map(list->new ListPage<>(list, Client.class));
    }
}