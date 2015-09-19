package com.tchepannou.app.login.config;

import com.tchepannou.app.login.service.AccessTokenService;
import com.tchepannou.app.login.service.AuthService;
import com.tchepannou.app.login.service.BlogService;
import com.tchepannou.app.login.service.CalendarService;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.blog.MyPostsCommand;
import com.tchepannou.app.login.service.blog.TeamPostsCommand;
import com.tchepannou.app.login.service.calendar.MyUpcomingEvents;
import com.tchepannou.app.login.service.calendar.TeamUpcomingEvents;
import com.tchepannou.app.login.service.impl.AccessTokenServiceImpl;
import com.tchepannou.app.login.service.impl.AuthServiceImpl;
import com.tchepannou.app.login.service.impl.BlogServiceImpl;
import com.tchepannou.app.login.service.impl.CalendarServiceImpl;
import com.tchepannou.app.login.service.impl.PartyServiceImpl;
import com.tchepannou.app.login.service.login.LoginCommand;
import com.tchepannou.app.login.service.login.LogoutCommand;
import com.tchepannou.app.login.service.profile.MyProfileCommand;
import com.tchepannou.app.login.service.team.MyTeamsCommand;
import com.tchepannou.app.login.service.team.TeamProfileCommand;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declare your services here!
 */
@Configuration
public class AppConfig {
    @Bean
    HttpClientConnectionManager httpClientConnectionManager (){
        PoolingHttpClientConnectionManager cnn = new PoolingHttpClientConnectionManager();
        cnn.setMaxTotal(50);
        cnn.setDefaultMaxPerRoute(10);
        return cnn;
    }

    //-- Service
    @Bean
    HttpClient httpClient () {
        return HttpClients.custom()
            .setDefaultRequestConfig(
                RequestConfig.custom()
                        .setStaleConnectionCheckEnabled(true)
                        .setSocketTimeout(5000)
                        .setConnectionRequestTimeout(5000)
                        .build()
            ).setConnectionManager(httpClientConnectionManager())
            .build();
    }

    @Bean
    AccessTokenService accessTokenService (){
        return new AccessTokenServiceImpl();
    }

    @Bean
    AuthService authService (){
        return new AuthServiceImpl();
    }

    @Bean
    BlogService blogService () {
        return new BlogServiceImpl();
    }

    @Bean
    CalendarService calendarService () {
        return new CalendarServiceImpl();
    }

    @Bean
    PartyService partyService (){
        return new PartyServiceImpl();
    }


    //-- AuthCommand
    @Bean LoginCommand loginCommand(){
        return new LoginCommand();
    }

    @Bean LogoutCommand logoutCommand(){
        return new LogoutCommand();
    }


    //-- Event Commands
    @Bean MyUpcomingEvents myUpcomingEvents (){
        return new MyUpcomingEvents();
    }

    @Bean TeamUpcomingEvents teamUpcomingEvents (){
        return new TeamUpcomingEvents();
    }


    //-- Party commands
    @Bean TeamProfileCommand getTeamProfile() {
        return new TeamProfileCommand();
    }

    @Bean MyProfileCommand getProfileCommand(){
        return new MyProfileCommand();
    }

    @Bean MyTeamsCommand myTeamsCommand(){
        return new MyTeamsCommand();
    }


    //-- Post command
    @Bean MyPostsCommand getMyPostsCommand() {
        return new MyPostsCommand();
    }

    @Bean TeamPostsCommand getTeamPostsCommand (){
        return new TeamPostsCommand();
    }
}
