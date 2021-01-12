package com.sl.ms.ordermanagement;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sl.ms.ordermanagement.config.AuthenticationRequest;
import com.sl.ms.ordermanagement.config.JwtRequestFilter;
import com.sl.ms.ordermanagement.config.JwtUtil;
import com.sl.ms.ordermanagement.items.ItemsRepository;
import com.sl.ms.ordermanagement.orders.Orders;
import com.sl.ms.ordermanagement.orders.OrdersRepository;
import com.sl.ms.ordermanagement.service.MyUserDetailsService;

import lombok.val;




@WebMvcTest(controllers = HelloWorldController.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class JWTTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
	
	@MockBean
	private OrdersRepository orderRepo;
	
	 @MockBean
	 private ItemsRepository itemsRepo;
	
	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private JwtUtil jwtTokenUtil;

	@MockBean
	private MyUserDetailsService userDetailsService;
	
	
	 @Test
	    void createTokenTest() throws Exception {
		 
		 AuthenticationRequest req = new AuthenticationRequest();
		 req.setUsername("foo");
		 req.setPassword("foo");
		 Authentication authentication = mock(Authentication.class);
		 
	        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())))
	        .thenReturn(authentication); 

	        this.mockMvc.perform(post("/authenticate"))
	                .andExpect(status().isBadRequest());
	                
	    }
	 
	 

}
