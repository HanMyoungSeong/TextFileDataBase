package kr.ac.jejuuniv;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		UserRepository userRepository = new UserRepository();
		User user = new User();
		for (int i = 0; i <= 15; i++) {
			user.setName("test1");
			user.setPassword("1q2w3e");
			userRepository.insert(user);
		}

		userRepository.delete(1);

		User findedUser = userRepository.select(10);
		System.out.println("id : " + findedUser.getId() + " name : " + findedUser.getName() + " password : " + findedUser.getPassword());

		User modifiedUser = new User();
		modifiedUser.setId(13);
		modifiedUser.setName("ÇÑ¸í½Â1");
		modifiedUser.setPassword("111111");
		userRepository.update(modifiedUser);

		findedUser = userRepository.select(13);
		System.out.println("id : " + findedUser.getId() + " name : " + findedUser.getName() + " password : " + findedUser.getPassword());

		List<User> findAllUsers = userRepository.findAll();
		System.out.println(findAllUsers.size());
	}
}