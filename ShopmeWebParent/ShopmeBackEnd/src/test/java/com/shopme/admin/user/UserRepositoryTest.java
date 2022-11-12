package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

		@Autowired
		private UserRepository repo;
		
		@Autowired
		private TestEntityManager entityManeger;
		
		@Test
		public void testCreateUser() {
			Role roleAdmin = entityManeger.find(Role.class, 1);
			User userNamHM = new User("nam@codejava.net", "nam2020", "Nam", "Ha Minh");
			
			userNamHM.addRole(roleAdmin);
			 
			User savedUser = repo.save(userNamHM);
			
			assertThat(savedUser.getId()).isGreaterThan(0);
			
		}
		
		@Test
		public void testCreateNewUserWithTwoRole(){
			
			User userRavi = new User("Ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
			Role roleEditor = new Role(3);
			Role roleAssistant = new Role(5);
			
			userRavi.addRole(roleEditor);
			userRavi.addRole(roleAssistant);
			
			User savedUser = repo.save(userRavi);
			
			assertThat(savedUser.getId()).isGreaterThan(0);
		}
		
		@Test
		public void testGetUserByEmail() {
			String email = "khaihoanpd@gmail.com";
			User user = repo.getUserByEmail(email);
			assertThat(user).isNotNull();
		}
		
		@Test
		public void testCountById() {
			Integer id=1;
			Long countById = repo.countById(id);
			assertThat(countById).isNotNull().isGreaterThan(0);
		}
		
		@Test
		public void testDisableUser() {
			Integer id = 9;
			boolean enabled= false;
			repo.updateEnabledStatus(id, enabled);
		}
		
		@Test
		public void testEnableUser() {
			Integer id = 9;
			boolean enabled= true;
			repo.updateEnabledStatus(id, enabled);
		}
		@Test
		public void testListFirstPage() {
			int pageanumber = 0;
			int pageSize = 4;
			org.springframework.data.domain.Pageable pageable = PageRequest.of(pageanumber, pageSize);
			Page<User> page = repo.findAll(pageable);
			List<User> listUsers = page.getContent();
			listUsers.forEach(user-> System.out.println(user));
			
			assertThat(listUsers.size()).isEqualTo(pageSize);
		}
}
